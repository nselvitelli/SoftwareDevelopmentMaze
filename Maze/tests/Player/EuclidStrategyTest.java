//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import Common.*;
//import Players.*;
//import Enumerations.*;
//import Referee.*;
//
//public class EuclidStrategyTest {
//
//  public Tile initialSpare;
//  public Tile[][] mockGameboard;
//  public Board mockBoard;
//  public State mockState;
//  public Player AIPlayer1;
//  public Player AIPlayer2;
//  public TestReferee mockReferee;
//
//
//  @Before
//  public void setup() {
//    Utils.setAvailableGems();
//    Tile[][] tiles = new Tile[7][7];
//
//    for(int i=0;i<7;i++) {
//      for(int j=0;j<7;j++) {
//        if (i%2 == 1) {
//          tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
//        } else {
//          tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//        }
//      }
//    }
//
//
//    /*
//
//    BOARD LOOKS LIKE:
//
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//
//     */
//
//    this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//
//    this.mockGameboard = tiles;
//    this.mockBoard = new Board(this.mockGameboard);
//    this.mockState = new State(this.mockBoard, this.initialSpare);
//
//    this.AIPlayer1 = new AIPlayer(new EuclidStrategy(), " ", " ");
//    Coordinate home1 = new Coordinate(1, 3);
//    Coordinate goal1 = new Coordinate(3, 3);
//    this.AIPlayer2 = new AIPlayer(new EuclidStrategy(), " ", " ");
//    Coordinate home2 = new Coordinate(3, 3);
//    Coordinate goal2 = new Coordinate(5, 1);
//    Coordinate current2 = new Coordinate(0, 6);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1);
//    this.mockState.addPlayer(AIPlayer2, goal2, home2, current2);
//    this.mockReferee = new TestReferee();
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//
//  @Test
//  public void testEuclid1() {
//    setup();
//    //Player 1 reaches goal
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(2, Direction.LEFT, 3));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(3, 3));
//    //Player 2 reaches tile next to goal
//    assertEquals(AIPlayer2.takeTurn(mockState.createPlayerState(AIPlayer2)), new MoveAction(6, Direction.UP, 0));
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer2), new Coordinate(6, 1));
//    //Player 1 reaches home
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(2, Direction.LEFT, 0));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 3));}
//
//  @Before
//  public void setup2() {
//    Utils.setAvailableGems();
//    Tile[][] tiles = new Tile[7][7];
//
//    for(int i=0;i<7;i++) {
//      for(int j=0;j<7;j++) {
//        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//      }
//    }
//
//    tiles[0][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//
//
//    /*
//
//    BOARD LOOKS LIKE:
//
//    - - | - - - -
//    - - - - - - -
//    - - - - - - -
//    - - - - - - -
//    - - - - - - -
//    - - - - - - -
//    - - - - - - -
//
//     */
//
//    this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//
//    this.mockGameboard = tiles;
//    this.mockBoard = new Board(this.mockGameboard);
//    this.mockState = new State(this.mockBoard, this.initialSpare);
//
//    this.AIPlayer1 = new AIPlayer(new EuclidStrategy(), " ", " ");
//    Coordinate home1 = new Coordinate(1, 1);
//    Coordinate goal1 = new Coordinate(3, 3);
//    Coordinate current1 = new Coordinate(6, 6);
//    this.AIPlayer2 = new AIPlayer(new EuclidStrategy(), " ", " ");
//    Coordinate home2 = new Coordinate(1, 3);
//    Coordinate goal2 = new Coordinate(3, 3);
//    Coordinate current2 = new Coordinate(4, 4);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1, current1);
//    this.mockState.addPlayer(AIPlayer2, goal2, home2, current2);
//    this.mockReferee = new TestReferee();
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//  @Test
//  public void testEuclid2() {
//    setup2();
//    //Player 1 gets as close as possible to goal
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(6, Direction.UP, 0));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(5, 3));
//
//    //Player 2 reaches goal with move:
//    assertEquals(AIPlayer2.takeTurn(mockState.createPlayerState(AIPlayer2)), new MoveAction(4, Direction.UP, 0));
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer2), new Coordinate(3, 3));
//  }
//
//
//  private static class TestReferee extends Referee {
//    public TestReferee() {
//
//    }
//
//    public void playerTurn(Player p) {
//      super.playerTurn(p);
//    }
//
//  }
//
//
//
//}
