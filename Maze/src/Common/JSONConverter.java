package Common;
import Enumerations.Direction;
import Enumerations.Gem;
import Players.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class containing methods for converting intermediate data structures to and from JSON.
 */
public class JSONConverter {


  /**
   * Convert a State to JSON
   */
  public static JSONObject stateToJSON(State state) throws JSONException {
    JSONObject stateJSON = new JSONObject();
    stateJSON.put("board", JSONConverter.boardToJSON(state));
    stateJSON.put("spare", JSONConverter.spareToJSON(state.getSpare()));
    stateJSON.put("plmt", JSONConverter.playersToJSON(state));

    MoveAction last = state.getLastMove();
    if (last == null) {
      stateJSON.put("last", JSONObject.NULL);
    } else{
      stateJSON.put("last", JSONConverter.lastToJSON(state.getLastMove()));
    }

    return stateJSON;
  }

  /**
   * Convert a Coordinate to JSON
   */
  public static JSONObject coordinateToJSON(Coordinate c) throws JSONException {
    JSONObject coordJSON = new JSONObject();
    coordJSON.put("row#", c.getRow());
    coordJSON.put("column#", c.getCol());
    return coordJSON;
  }

  /**
   * Convert a Board to JSON
   */
  private static JSONObject boardToJSON(State state) throws JSONException {
    JSONObject boardJSON = new JSONObject();
    JSONArray tilesJSON = new JSONArray();
    JSONArray treasuresJSON = new JSONArray();

    for(int r=0; r<state.getRows();r++) {
      JSONArray tileRowJSON = new JSONArray();
      JSONArray treasureRowJSON = new JSONArray();
      for(int c=0; c<state.getCols(); c++) {
        Tile t = state.getBoard().getTileAt(new Coordinate(r, c));

        tileRowJSON.put(t.toString());

        JSONArray tTreasuresJSON = new JSONArray();
        tTreasuresJSON.put(t.getGem1());
        tTreasuresJSON.put(t.getGem2());
        treasureRowJSON.put(tTreasuresJSON);
      }
      tilesJSON.put(tileRowJSON);
      treasuresJSON.put(treasureRowJSON);
    }
    boardJSON.put("connectors", tilesJSON);
    boardJSON.put("treasures", treasuresJSON);
    return boardJSON;
  }

  /**
   * Convert a State's Players to JSON
   */
  private static JSONArray playersToJSON(State state) throws JSONException {
    JSONArray playersJSON = new JSONArray();
    for(Color p : state.getPlayers()) {
      JSONObject pJSON = new JSONObject();
      pJSON.put("current", JSONConverter.coordinateToJSON(state.getPlayerLocation(p)));
      pJSON.put("home", JSONConverter.coordinateToJSON(state.getPlayerHome(p)));
      pJSON.put("color", JSONConverter.colorToString(p));
      playersJSON.put(pJSON);
    }
    return playersJSON;
  }


  /**
   * Convert a MoveAction to JSON
   */
  private static JSONArray lastToJSON(MoveAction lastMove) {
    JSONArray lastJSON = new JSONArray();
    lastJSON.put(lastMove.getIndex());
    lastJSON.put(lastMove.getDirection().toString());
    return lastJSON;
  }

  /**
   * Convert a Tile to JSON
   */
  private static JSONObject spareToJSON(Tile spare) throws JSONException {
    JSONObject tileJSON = new JSONObject();
    tileJSON.put("tilekey", spare.toString());
    tileJSON.put("1-image", spare.getGem1());
    tileJSON.put("2-image", spare.getGem2());
    return tileJSON;
  }

  public static JSONArray moveActionToJSON(MoveAction ma) throws JSONException{
    JSONArray result = new JSONArray();
    result.put(ma.getIndex());
    result.put(ma.getDirection().toString());
    int r = ma.getNumRotations();
    int counterClockwise = (4-r) % 4;
    result.put(counterClockwise * 90);
    Coordinate c = ma.getMoveTo();
    result.put(JSONConverter.coordinateToJSON(c));
    return result;
  }

  /**
   * Converts the JSON representation of a State to a State. This method can convert the State to
   * either a Player State or a Referee State.
   * @param stateJSON the JSON representation of a State
   * @return the parsed state
   * @throws JSONException if the given JSON State is malformed
   */
  public static State stateFromJSON(JSONObject stateJSON) throws JSONException {
    JSONObject boardJSON = stateJSON.getJSONObject("board");
    JSONObject spareJSON = stateJSON.getJSONObject("spare");
    JSONArray plmtJSON = stateJSON.getJSONArray("plmt");

    //Create State:
    Board board = JSONConverter.boardFromJSON(boardJSON);
    Tile spare = JSONConverter.tileFromJSON(spareJSON);
    State state = new State(board, spare);

    //Add Players:
    for(int i=0; i<plmtJSON.length(); i++) {
      JSONObject playerObj = plmtJSON.getJSONObject(i);
      JSONObject homeObj = playerObj.getJSONObject("home");
      Coordinate home = JSONConverter.coordinateFromJSON(homeObj);
      JSONObject currentObj = playerObj.getJSONObject("current");
      Coordinate current = JSONConverter.coordinateFromJSON(currentObj);
      String colorStr = playerObj.getString("color");
      Color color = JSONConverter.colorFromString(colorStr);
      if (playerObj.has("goto")) {
        JSONObject goalObj = playerObj.getJSONObject("goto");
        Coordinate goal = JSONConverter.coordinateFromJSON(goalObj);
        state.addPlayer(color, goal, home, current);
      }
      else {
        state.addPlayerNoGoal(color, home, current);
      }
    }

    //Set last move:
    if (!stateJSON.isNull("last")) {
      JSONArray lastJSON = stateJSON.getJSONArray("last");
      int i = lastJSON.getInt(0);
      Direction d = Direction.valueOf(lastJSON.getString(1));
      state.setLastMove(new MoveAction(i, d, 0));
    }

    // if PlayerState2 has goals key, set the available goals accordingly
    if(stateJSON.has("goals")) {
      JSONArray coordinates = stateJSON.getJSONArray("goals");
      Queue<Coordinate> goals = new LinkedList<>();
      for(int i = 0; i < coordinates.length(); i++) {
        Coordinate coord = JSONConverter.coordinateFromJSON(coordinates.getJSONObject(i));
        goals.add(coord);
      }
      state.setAvailableGoals(goals);
    }

    return state;
  }


  public static MoveAction moveActionFromJSON(JSONArray jsonAction) throws JSONException{
    int i = jsonAction.getInt(0);
    Direction d = Direction.valueOf(jsonAction.getString(1));
    int r = jsonAction.getInt(2);
    r = r / 90;
    r = (4 - r) % 4;
    JSONObject jsonMoveTo = jsonAction.getJSONObject(3);
    int row = jsonMoveTo.getInt("row#");
    int column = jsonMoveTo.getInt("column#");
    return new MoveAction(i, d, r, new Coordinate(row, column));
  }

  public static Coordinate coordinateFromJSON(JSONObject coordinate) throws JSONException{
    int row = coordinate.getInt("row#");
    int col = coordinate.getInt("column#");
    return new Coordinate(row, col);
  }


  public static Board boardFromJSON(JSONObject board) throws JSONException {
    JSONArray connectors = board.getJSONArray("connectors");
    JSONArray treasures = board.getJSONArray("treasures");

    Tile[][] tiles = new Tile[connectors.length()][connectors.getJSONArray(0).length()];

    for(int r = 0; r < connectors.length(); r++) {
      JSONArray rowJSON = connectors.getJSONArray(r);
      JSONArray rowGemJSON = treasures.getJSONArray(r);

      for(int c = 0; c < rowJSON.length(); c++) {
        char character = rowJSON.getString(c).charAt(0);
        JSONArray treasureJSON = rowGemJSON.getJSONArray(0);
        Gem g1 = Gem.getGemOf(treasureJSON.getString(0));
        Gem g2 = Gem.getGemOf(treasureJSON.getString(1));
        tiles[r][c] = new Tile(character, g1, g2);
      }
    }
    return new Board(tiles);
  }

  public static Color colorFromString(String color) {
    switch (color) {
      case "purple":
        return Color.MAGENTA;
      case "orange":
        return Color.ORANGE;
      case "pink":
        return Color.PINK;
      case "red":
        return Color.RED;
      case "blue":
        return Color.BLUE;
      case "green":
        return Color.GREEN;
      case "yellow":
        return Color.YELLOW;
      case "white":
        return Color.WHITE;
      case "black":
        return Color.BLACK;
      default:
        return Color.decode("#" + color);
    }
  }

  /**
   * Color is one of:

     - a String that matches the regular expression

        "^[A-F|\d][A-F|\d][A-F|\d][A-F|\d][A-F|\d][A-F|\d]$"
     - "purple",
     - "orange",
     - "pink",
     - "red",
     - "blue",
     - "green",
     - "yellow",
     - "white",
     - "black".
   * @param color
   * @return
   */
  public static String colorToString(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Cannot convert a null Color to string.");
    }
    else if (Color.RED.equals(color)) {
      return "red";
    } 
    else if (Color.BLUE.equals(color)) {
      return "blue";
    } 
    else if (Color.YELLOW.equals(color)) {
      return "yellow";
    } 
    else if (Color.GREEN.equals(color)) {
      return "green";
    } 
    else if (Color.WHITE.equals(color)) {
      return "white";
    } 
    else if (Color.BLACK.equals(color)) {
      return "black";
    } 
    else if (Color.MAGENTA.equals(color)) {
      return "purple";
    } 
    else if (Color.PINK.equals(color)) {
      return "pink";
    }
    else {
      String red = padHexValue(Integer.toHexString(color.getRed()));
      String green = padHexValue(Integer.toHexString(color.getGreen()));
      String blue = padHexValue(Integer.toHexString(color.getBlue()));
      return (red + green + blue).toUpperCase();
    }
  }

  private static String padHexValue(String hexStr) {
    if (hexStr.length() == 1) {
      return "0" + hexStr;
    }
    return hexStr;
  }

  public static JSONArray winnersAndKickedToJSON(java.util.List<Player> winners, List<Player> kicked) {
    JSONArray winnersJSON = new JSONArray();
    for(Player p : winners) {
      winnersJSON.put(p.getName());
    }

    JSONArray kickedJSON = new JSONArray();
    for(Player p : kicked) {
      kickedJSON.put(p.getName());
    }

    JSONArray result = new JSONArray();
    result.put(winnersJSON);
    result.put(kickedJSON);

    return result;
  }


  public static Tile tileFromJSON(JSONObject spareJSON) throws JSONException {
    String connector = spareJSON.getString("tilekey");
    Gem g1 = Gem.getGemOf(spareJSON.getString("1-image"));
    Gem g2 = Gem.getGemOf(spareJSON.getString("2-image"));
    return new Tile((connector.charAt(0)), g1, g2);
  }

}
