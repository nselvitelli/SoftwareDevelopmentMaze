//package Integration;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import Common.*;
//import Enumerations.*;
//import Referee.*;
//
//public class RefereeIntegration {
//
//
//  public static void main(String args[]) {
//
//    try{
//
//      JSONTokener source = Utils.readInput();
//
//      JSONArray playerSpec = (JSONArray) source.nextValue();
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
//      //Add Players:
//      Utils.addPlayersToStateJSON(playerSpec, plmtJSON, state);
//
//      //Set last move:
//      if (!stateJSON.isNull("last")) {
//        JSONArray lastJSON = stateJSON.getJSONArray("last");
//        int i = lastJSON.getInt(0);
//        Direction d = Direction.valueOf(lastJSON.getString(1));
//        state.setLastMove(new MoveAction(i, d, 0));
//      }
//
//      Referee ref = new Referee();
//      ref.setupGame(state);
//      ref.runGame();
//      Utils.printJSONArrayWinners(ref.getWinners());
//
//    } catch(JSONException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//
//}
