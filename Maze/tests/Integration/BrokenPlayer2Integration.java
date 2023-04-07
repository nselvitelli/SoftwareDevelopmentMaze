package Integration;

import Common.*;
import Enumerations.Direction;
import Enumerations.ResponseStatus;
import Common.Utils;
import Players.*;
import Referee.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class BrokenPlayer2Integration {
  static private final Object waiter = new Object();


  public static void main(String args[]) {
    System.out.println(executeTest());
//    System.exit(0);
  }

  private static String executeTest() {
    try{

      JSONTokener source = Utils.readInput();

      JSONArray playerSpec = (JSONArray) source.nextValue();

      JSONObject stateJSON = (JSONObject) source.nextValue();
      JSONObject boardJSON = stateJSON.getJSONObject("board");
      JSONObject spareJSON = stateJSON.getJSONObject("spare");
      JSONArray plmtJSON = stateJSON.getJSONArray("plmt");


      //Create State:
      Board board = JSONConverter.boardFromJSON(boardJSON);
      Tile spare = JSONConverter.tileFromJSON(spareJSON);
      State state = new State(board, spare);


      //Set last move:
      if (!stateJSON.isNull("last")) {
        JSONArray lastJSON = stateJSON.getJSONArray("last");
        int i = lastJSON.getInt(0);
        Direction d = Direction.valueOf(lastJSON.getString(1));
        state.setLastMove(new MoveAction(i, d, 0));
      }

      Referee ref = new Referee();
//      Referee ref = new Referee(new Observer());
      ref.setupGame(state);
      //Add Players:
      addBadPlayersToStateJSON(playerSpec, plmtJSON, ref);
      ref.runGame();

      synchronized (waiter) {
        try {
          waiter.wait(2000);
        } catch (InterruptedException ignored) {
        }
      }

      return printJSONArrayWinnersAndKicked(ref.getWinners(), ref.getKickedPlayers());
    } catch(JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private static String printJSONArrayWinnersAndKicked(List<Player> winners, List<Player> kicked) {
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

    return result.toString();
  }

  public static void addBadPlayersToStateJSON(JSONArray playerSpec, JSONArray plmtJSON, Referee ref) throws JSONException {
    for(int i=0; i<playerSpec.length(); i++) {

      JSONObject pJSON = plmtJSON.getJSONObject(i);
      String colorString = pJSON.getString("color");
      Color color = Utils.stringToColor(colorString);
      JSONObject currentJSON = pJSON.getJSONObject("current");
      Coordinate current = Utils.getCoordinateJSON(currentJSON);

      JSONObject homeJSON = pJSON.getJSONObject("home");
      Coordinate home = Utils.getCoordinateJSON(homeJSON);

      JSONObject goToJSON = pJSON.getJSONObject("goto");
      Coordinate goTo = Utils.getCoordinateJSON(goToJSON);

      JSONArray player = playerSpec.getJSONArray(i);
      String name = player.getString(0);
      String strategy = player.getString(1);
      String bad = "none";
      int count = 1;
      boolean loop = false;
      if (player.length() > 2) {
        bad = player.getString(2);
      }
      if (player.length() > 3) {
        count = player.getInt(3);
        loop = true;
      }

      BrokenPlayer p;
      if (strategy.equals("Euclid")) {
        p = new BrokenPlayer(new EuclidStrategy(), name, bad, count, loop);
      } else if (strategy.equals("Riemann")) {
        p = new BrokenPlayer(new RiemannStrategy(), name, bad, count, loop);
      } else{
        throw new JSONException("Invalid strategy" + strategy);
      }
      ref.addPlayerToGame(p, color, goTo, home, current);
    }
  }
}
