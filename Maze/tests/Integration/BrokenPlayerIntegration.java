package Integration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import Common.*;
import Players.*;
import Enumerations.*;
import Referee.*;

public class BrokenPlayerIntegration {


  public static void main(String args[]) {

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
      ref.setupGame(state);
      //Add Players:
      addBadPlayersToStateJSON(playerSpec, plmtJSON, ref);
      ref.runGame();

      String result = JSONConverter.winnersAndKickedToJSON(ref.getWinners(), ref.getKickedPlayers()).toString();
      System.out.println(result);

    } catch(JSONException e) {
      System.out.println(e.getMessage());
    }
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
      if (player.length() == 3) {
        bad = player.getString(2);
      }

      BrokenPlayer p;
      if (strategy.equals("Euclid")) {
        p = new BrokenPlayer(new EuclidStrategy(), name, color, bad);
      } else if (strategy.equals("Riemann")) {
        p = new BrokenPlayer(new RiemannStrategy(), name, color, bad);
      } else{
        return;
      }

      ref.addPlayerToGame(p, color, goTo, home, current);
    }
  }

  static class BrokenPlayer extends AIPlayer{

    private final BrokenPlayer.BadBehavior bad;

    public BrokenPlayer(IStrategy strategy, String name, Color color, String bad) {
      super(strategy, name, color);
      this.bad = BrokenPlayer.BadBehavior.valueOf(bad);
    }

    @Override
    public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
      if (this.bad == BrokenPlayer.BadBehavior.setUp) {
        this.errorOut();
      }
      return super.setup(ps, destination);
    }

    @Override
    public Action takeTurn(State state) {
      if (this.bad == BrokenPlayer.BadBehavior.takeTurn) {
        this.errorOut();
      }
      return super.takeTurn(state);
    }

    @Override
    public ResponseStatus won(Boolean w) {
      if (this.bad == BrokenPlayer.BadBehavior.win) {
        this.errorOut();
      }
      return super.won(w);
    }

    private void errorOut() {
      int x = 1/0;
    }


    public enum BadBehavior{
      setUp, takeTurn, win, none
    }

  }


}
