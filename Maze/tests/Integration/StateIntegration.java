//package Integration;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//import Common.*;
//import Players.*;
//import Enumerations.*;
//import Referee.*;
//
///**
// * Integration testing for Milestone 3 - The State
// */
//public class StateIntegration {
//
//  public static void main(String args[]) {
//
//    try{
//
//      JSONTokener source = readInput();
//
//      JSONObject stateJSON = (JSONObject) source.nextValue();
//      JSONObject boardJSON = stateJSON.getJSONObject("board");
//      JSONObject spareJSON = stateJSON.getJSONObject("spare");
//      JSONArray plmtJSON = stateJSON.getJSONArray("plmt");
//
//      MoveAction lastRevert = null;
//      if (!stateJSON.isNull("last")) {
//        lastRevert = jsonCreateRevertMoveAction(stateJSON.getJSONArray("last"));
//      }
//
//
//      Board board = jsonCreateBoard(boardJSON);
//      Tile spare = jsonCreateTile(spareJSON);
//
//
//      TestState state = new TestState(board, spare);
//
//      jsonCreatePlayersInState(plmtJSON, state);
//
//      int index = (int) source.nextValue();
//      Direction direction = Direction.valueOf((String) source.nextValue());
//      int num90RotationsCounter = (int) source.nextValue() / 90;
//      int num90Rotations =  (4 - num90RotationsCounter) % 4;
//
//      MoveAction nextMove = new MoveAction(index, direction, num90Rotations);
//      state.setLastMove(lastRevert);
//      state.moveActionStay(nextMove);
//      List<Coordinate> reachable = state.getValidCoordinates(state.getPlayerLocation(state.whoseTurn()));
//      printJSONArrayCoordinates(reachable);
//
//    } catch(JSONException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  private static Tile jsonCreateTile(JSONObject spareJSON) throws JSONException {
//    String connector = spareJSON.getString("tilekey");
//    Gem g1 = null;//Gem.valueOf(spareJSON.getString("1-image"));
//    Gem g2 = null;//Gem.valueOf(spareJSON.getString("2-image"));
//    return new Tile((connector.charAt(0)), g1, g2);
//  }
//
//  public static Board jsonCreateBoard(JSONObject boardJSON) throws JSONException{
//    Tile[][] tiles = new Tile[7][7];
//    JSONArray rowsJSON = boardJSON.getJSONArray("connectors");
//    JSONArray rowsGemJSON = boardJSON.getJSONArray("treasures");
//
//    for(int r=0; r<7; r++) {
//      JSONArray rowJSON = rowsJSON.getJSONArray(r);
//      JSONArray rowGemJSON = rowsGemJSON.getJSONArray(r);
//
//      for(int c=0; c<7; c++) {
//        char character = rowJSON.getString(c).charAt(0);
//        JSONArray treasureJSON = rowGemJSON.getJSONArray(0);
//        Gem g1 = null;//Gem.valueOf(treasureJSON.getString(0));
//        Gem g2 = null;//Gem.valueOf(treasureJSON.getString(1));
//        tiles[r][c] = new Tile(character, g1, g2);
//      }
//    }
//    return new Board(tiles);
//  }
//
//  private static void jsonCreatePlayersInState(JSONArray plmtJSON, State state) throws JSONException{
//    for(int i=0; i<plmtJSON.length(); i++) {
//      JSONObject pJSON = plmtJSON.getJSONObject(i);
//      JSONObject homeJSON = pJSON.getJSONObject("home");
//      JSONObject currentJSON = pJSON.getJSONObject("current");
//      String color = pJSON.getString("color");
//      Coordinate home = new Coordinate((int) homeJSON.get("row#"), (int) homeJSON.get("column#"));
//      Coordinate current = new Coordinate((int) currentJSON.get("row#"), (int) currentJSON.get("column#"));
//
//      int goalRow = (home.getRow() + 2) % 7;
//
//      state.addPlayer(new MockPlayer(), new Coordinate(goalRow, 1), home, current);
//
//    }
//  }
//
//  private static MoveAction jsonCreateRevertMoveAction(JSONArray lastJSON) throws JSONException{
//    if (lastJSON == null) {
//      return null;
//    }
//    int i = lastJSON.getInt(0);
//    Direction d = Direction.valueOf(lastJSON.getString(1));
//    MoveAction ma = new MoveAction(i, d.oppositeDirection(), 0);
//    return ma;
//  }
//
//  private static void printJSONArrayCoordinates(List<Coordinate> reachable) {
//    JSONArray ret = new JSONArray();
//    for (Coordinate c : reachable) {
//      JSONObject temp = new JSONObject();
//      try{
//        temp.put("column#", c.getCol());
//        temp.put("row#", c.getRow());
//      } catch (Exception e) {}
//      ret.put(temp);
//    }
//    System.out.println(ret.toString());
//  }
//
//  public static JSONTokener readInput() {
//    Scanner sc = new Scanner(System.in);
//    String str = "";
//    while(sc.hasNext()) {
//      str += sc.next();
//    }
//
//    JSONTokener source = new JSONTokener(str);
//
//    return source;
//  }
//
//  private static class MockPlayer implements Player {
//
//
//    @Override
//    public Board proposeBoard(int rows, int columns) {
//      return null;
//    }
//
//    @Override
//    public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
//      return null;
//    }
//
//    @Override
//    public Action takeTurn(State ms) {
//      return null;
//    }
//
//    @Override
//    public ResponseStatus won(Boolean w) {
//      return null;
//    }
//
//    @Override
//    public Optional<Boolean> didWin() {
//      return Optional.empty();
//    }
//
//    @Override
//    public String getColor() {
//      return null;
//    }
//
//    @Override
//    public String getName() {
//      return null;
//    }
//  }
//
//  private static class TestState extends State {
//
//    public TestState(Board board, Tile spare) {
//      super(board, spare);
//    }
//
//    @Override
//    public void moveActionStay(MoveAction ma) {
//      super.moveActionStay(ma);
//    }
//
//    @Override
//    public List<Coordinate> getValidCoordinates(Coordinate c) {
//      return super.getValidCoordinates(c);
//    }
//  }
//
//
//}
//
//package Integration;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//import Common.*;
//import Players.*;
//import Enumerations.*;
//import Referee.*;
//
///**
// * Integration testing for Milestone 3 - The State
// */
//public class StateIntegration {
//
//  public static void main(String args[]) {
//
//    try{
//
//      JSONTokener source = readInput();
//
//      JSONObject stateJSON = (JSONObject) source.nextValue();
//      JSONObject boardJSON = stateJSON.getJSONObject("board");
//      JSONObject spareJSON = stateJSON.getJSONObject("spare");
//      JSONArray plmtJSON = stateJSON.getJSONArray("plmt");
//
//      MoveAction lastRevert = null;
//      if (!stateJSON.isNull("last")) {
//        lastRevert = jsonCreateRevertMoveAction(stateJSON.getJSONArray("last"));
//      }
//
//
//      Board board = jsonCreateBoard(boardJSON);
//      Tile spare = jsonCreateTile(spareJSON);
//
//
//      TestState state = new TestState(board, spare);
//
//      jsonCreatePlayersInState(plmtJSON, state);
//
//      int index = (int) source.nextValue();
//      Direction direction = Direction.valueOf((String) source.nextValue());
//      int num90RotationsCounter = (int) source.nextValue() / 90;
//      int num90Rotations =  (4 - num90RotationsCounter) % 4;
//
//      MoveAction nextMove = new MoveAction(index, direction, num90Rotations);
//      state.setLastMove(lastRevert);
//      state.moveActionStay(nextMove);
//      List<Coordinate> reachable = state.getValidCoordinates(state.getPlayerLocation(state.whoseTurn()));
//      printJSONArrayCoordinates(reachable);
//
//    } catch(JSONException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  private static Tile jsonCreateTile(JSONObject spareJSON) throws JSONException {
//    String connector = spareJSON.getString("tilekey");
//    Gem g1 = null;//Gem.valueOf(spareJSON.getString("1-image"));
//    Gem g2 = null;//Gem.valueOf(spareJSON.getString("2-image"));
//    return new Tile((connector.charAt(0)), g1, g2);
//  }
//
//  public static Board jsonCreateBoard(JSONObject boardJSON) throws JSONException{
//    Tile[][] tiles = new Tile[7][7];
//    JSONArray rowsJSON = boardJSON.getJSONArray("connectors");
//    JSONArray rowsGemJSON = boardJSON.getJSONArray("treasures");
//
//    for(int r=0; r<7; r++) {
//      JSONArray rowJSON = rowsJSON.getJSONArray(r);
//      JSONArray rowGemJSON = rowsGemJSON.getJSONArray(r);
//
//      for(int c=0; c<7; c++) {
//        char character = rowJSON.getString(c).charAt(0);
//        JSONArray treasureJSON = rowGemJSON.getJSONArray(0);
//        Gem g1 = null;//Gem.valueOf(treasureJSON.getString(0));
//        Gem g2 = null;//Gem.valueOf(treasureJSON.getString(1));
//        tiles[r][c] = new Tile(character, g1, g2);
//      }
//    }
//    return new Board(tiles);
//  }
//
//  private static void jsonCreatePlayersInState(JSONArray plmtJSON, State state) throws JSONException{
//    for(int i=0; i<plmtJSON.length(); i++) {
//      JSONObject pJSON = plmtJSON.getJSONObject(i);
//      JSONObject homeJSON = pJSON.getJSONObject("home");
//      JSONObject currentJSON = pJSON.getJSONObject("current");
//      String color = pJSON.getString("color");
//      Coordinate home = new Coordinate((int) homeJSON.get("row#"), (int) homeJSON.get("column#"));
//      Coordinate current = new Coordinate((int) currentJSON.get("row#"), (int) currentJSON.get("column#"));
//
//      int goalRow = (home.getRow() + 2) % 7;
//
//      state.addPlayer(new MockPlayer(), new Coordinate(goalRow, 1), home, current);
//
//    }
//  }
//
//  private static MoveAction jsonCreateRevertMoveAction(JSONArray lastJSON) throws JSONException{
//    if (lastJSON == null) {
//      return null;
//    }
//    int i = lastJSON.getInt(0);
//    Direction d = Direction.valueOf(lastJSON.getString(1));
//    MoveAction ma = new MoveAction(i, d.oppositeDirection(), 0);
//    return ma;
//  }
//
//  private static void printJSONArrayCoordinates(List<Coordinate> reachable) {
//    JSONArray ret = new JSONArray();
//    for (Coordinate c : reachable) {
//      JSONObject temp = new JSONObject();
//      try{
//        temp.put("column#", c.getCol());
//        temp.put("row#", c.getRow());
//      } catch (Exception e) {}
//      ret.put(temp);
//    }
//    System.out.println(ret.toString());
//  }
//
//  public static JSONTokener readInput() {
//    Scanner sc = new Scanner(System.in);
//    String str = "";
//    while(sc.hasNext()) {
//      str += sc.next();
//    }
//
//    JSONTokener source = new JSONTokener(str);
//
//    return source;
//  }
//
//  private static class MockPlayer implements Player {
//
//
//    @Override
//    public Board proposeBoard(int rows, int columns) {
//      return null;
//    }
//
//    @Override
//    public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
//      return null;
//    }
//
//    @Override
//    public Action takeTurn(State ms) {
//      return null;
//    }
//
//    @Override
//    public ResponseStatus won(Boolean w) {
//      return null;
//    }
//
//    @Override
//    public Optional<Boolean> didWin() {
//      return Optional.empty();
//    }
//
//    @Override
//    public String getColor() {
//      return null;
//    }
//
//    @Override
//    public String getName() {
//      return null;
//    }
//  }
//
//  private static class TestState extends State {
//
//    public TestState(Board board, Tile spare) {
//      super(board, spare);
//    }
//
//    @Override
//    public void moveActionStay(MoveAction ma) {
//      super.moveActionStay(ma);
//    }
//
//    @Override
//    public List<Coordinate> getValidCoordinates(Coordinate c) {
//      return super.getValidCoordinates(c);
//    }
//  }
//
//
//}
//
