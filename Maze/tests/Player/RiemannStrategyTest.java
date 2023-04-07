//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import Common.*;
//import Players.*;
//import Enumerations.*;
//import Referee.*;
//
///**
// * Testing for Riemann Strategy.
// */
//public class RiemannStrategyTest {
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
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), " ", " ");
//    Coordinate home1 = new Coordinate(1, 3);
//    Coordinate goal1 = new Coordinate(3, 3);
//    this.AIPlayer2 = new AIPlayer(new RiemannStrategy(), " ", " ");
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
//  @Test
//  public void testRiemann1() {
//    setup();
//    //Player 1 reaches goal:
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(2, Direction.LEFT, 3));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(3, 3));
//    //Player 2 goes to top left:
//    assertEquals(AIPlayer2.takeTurn(mockState.createPlayerState(AIPlayer2)), new MoveAction(0, Direction.LEFT, 0));
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer2), new Coordinate(0, 0));
//    //Player 1 reaches home:
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(0, Direction.LEFT, 0));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 3));
//    //Player 2 remains in top left with same move:
//    assertEquals(AIPlayer2.takeTurn(mockState.createPlayerState(AIPlayer2)), new MoveAction(0, Direction.LEFT, 0));
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer2), new Coordinate(0, 0));
//  }
//
//  @Before
//  public void setup2() {
//    Utils.setAvailableGems();
//    Tile[][] tiles = new Tile[7][7];
//
//    for(int i=0;i<7;i++) {
//      for(int j=0;j<7;j++) {
//          tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
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
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), " ", " ");
//    Coordinate home1 = new Coordinate(1, 1);
//    Coordinate goal1 = new Coordinate(3, 3);
//    Coordinate current1 = new Coordinate(6, 6);
//    this.AIPlayer2 = new AIPlayer(new RiemannStrategy(), " ", " ");
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
//  public void testRiemann2() {
//    setup2();
//    //Player 1 goes off board to top then as far left as possible:
//    assertEquals(AIPlayer1.takeTurn(mockState.createPlayerState(AIPlayer1)), new MoveAction(6, Direction.DOWN, 0));
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer1), new Coordinate(0, 3));
//    //Player 2 reaches goal with move:
//    assertEquals(AIPlayer2.takeTurn(mockState.createPlayerState(AIPlayer2)), new MoveAction(4, Direction.UP, 0));
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    assertEquals(this.mockState.getPlayerLocation(this.AIPlayer2), new Coordinate(3, 3));
//  }
//
//
////  @Before
////  public void setup3() {
////    Utils.setAvailableGems();
////    Tile[][] tiles = new Tile[7][7];
////
////    tiles[0][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE90);
////    tiles[0][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
////    tiles[0][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE270);
////    tiles[1][0] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
////    tiles[1][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
////    tiles[1][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
////    tiles[2][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
////    tiles[2][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE270);
////    tiles[2][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
////
////    for (int i = 3; i < 7; i++) {
////      for (int j = 3; j < 7; j++) {
////        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
////      }
////    }
////
////    for (int i = 0; i < 3; i++) {
////      for (int j = 3; j < 7; j++) {
////        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
////      }
////    }
////
////    for (int i = 3; i < 7; i++) {
////      for (int j = 0; j < 3; j++) {
////        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
////      }
////    }
////
////    /*
////
////    BOARD LOOKS LIKE:
////
////    * * * | | | |
////    * * * | | | |
////    * * * | | | |
////    - - - + + + +
////    - - - + + + +
////    - - - + + + +
////    - - - + + + +
////
////     */
////
////    //Initialize MockState
////    this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
////    this.mockGameboard = tiles;
////    this.mockBoard = new Board(this.mockGameboard);
////    MockState ms = new MockState(this.mockBoard, this.initialSpare);
////
////    //Initialize Player
////    this.AIPlayer1 = new AIPlayer(new RiemannStrategy());
////    Coordinate home = new Coordinate(5, 5);
////    Coordinate goal = new Coordinate(5, 3);
////
////    ms.addPlayer(this.AIPlayer1, goal, home, home);
////    this.AIPlayer1.setMockState(ms);
////    ms.startGame();
////  }
////
////  /**
////   * Testing the strategy outside the scope of the referee:
////   */
////  @Test
////  public void testRiemannStrategy3() {
////    setup3();
////    this.AIPlayer1.moveReady();
////    assertEquals(this.AIPlayer1.getMoveTo(), AIPlayer1.getGoal());
////    assertEquals(this.AIPlayer1.getMove().getIndex(), 0);
////    assertEquals(this.AIPlayer1.getMove().getDirection(), Direction.LEFT);
////  }
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
//}
