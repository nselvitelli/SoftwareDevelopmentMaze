//package Integration;
//import java.util.Optional;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//import Common.*;
//import Players.Player;
//import Enumerations.*;
//
//
///**
// * Integration testing for Milestone 4 - The Strategy
// */
//public class StrategyIntegration {
//
//  public static void main(String args[]) {
//
//    try{
//
//      JSONTokener source = Utils.readInput();
//
//      String strategy = (String) source.nextValue();
//
//      JSONObject stateJSON = (JSONObject) source.nextValue();
//      JSONObject boardJSON = stateJSON.getJSONObject("board");
//      JSONObject spareJSON = stateJSON.getJSONObject("spare");
//      JSONArray plmtJSON = stateJSON.getJSONArray("plmt");
//
//
//      //Create State:
//      Board board = Utils.jsonCreateBoard7(boardJSON);
//      Tile spare = Utils.jsonCreateTile(spareJSON);
//      State state = new State(board, spare);
//
//
//      //Create Player:
//      JSONObject goalJSON = (JSONObject) source.nextValue();
//      Coordinate goal = new Coordinate(goalJSON.getInt("row#"), goalJSON.getInt("column#"));
//
//      Player p = Utils.jsonAddOnePlayer(plmtJSON, state, strategy);
//
//      //Set last move:
//      if (!stateJSON.isNull("last")) {
//        JSONArray lastJSON = stateJSON.getJSONArray("last");
//        int i = lastJSON.getInt(0);
//        Direction d = Direction.valueOf(lastJSON.getString(1));
//        state.setLastMove(new MoveAction(i, d, 0));
//      }
//
//      p.setup(Optional.empty(), goal);
//      Action moveMaybe = p.takeTurn(state.createPlayerState(p));
//
//      Utils.printJSONArrayMoveAction(moveMaybe);
//
//    } catch(JSONException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//
//}
//
