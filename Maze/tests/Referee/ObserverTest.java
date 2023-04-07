//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import javax.swing.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import Common.*;
//import Enumerations.Shape;
//import Players.*;
//import Enumerations.*;
//import Referee.*;
//
//@Ignore // remove tag to include GUI tests in suite
//public class ObserverTest {
//
//  public Tile initialSpare;
//  public Tile[][] mockGameboard;
//  public Board mockBoard;
//  public State mockState;
//  public Player AIPlayer1;
//  public Player AIPlayer2;
//  public Referee mockReferee;
//  private Observer mockObserver;
//
//  @Before
//  public void setup() {
//    Utils.setAvailableGems();
//    Tile[][] tiles = new Tile[7][7];
//
//    for (int i = 0; i < 7; i++) {
//      for (int j = 0; j < 7; j++) {
//        if (i % 2 == 1) {
//          tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
//        } else {
//          tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//        }
//      }
//    }
//
//
//    /*
//    BOARD LOOKS LIKE:
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//    + + + + + + +
//    - - - - - - -
//     */
//
//    this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//
//    this.mockGameboard = tiles;
//    this.mockBoard = new Board(this.mockGameboard);
//    this.mockState = new State(this.mockBoard, this.initialSpare);
//
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), "ryan", "blue");
//    Coordinate home1 = new Coordinate(1, 3);
//    Coordinate goal1 = new Coordinate(3, 3);
//    this.AIPlayer1.setup(Optional.empty(), goal1);
//    this.AIPlayer2 = new AIPlayer(new RiemannStrategy(), "greg", "green");
//    Coordinate home2 = new Coordinate(1, 5);
//    Coordinate goal2 = new Coordinate(5, 1);
//    Coordinate current2 = new Coordinate(0, 6);
//    this.AIPlayer2.setup(Optional.empty(), goal2);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1);
//    this.mockState.addPlayer(AIPlayer2, goal2, home2, current2);
//    this.mockObserver = new Observer();
//    this.mockReferee = new Referee(this.mockObserver);
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//  @Test
//  public void testNext() {
//    setup();
//    this.mockReferee.runGame();
//    assertNull(this.mockObserver.getCurrentState());
//    this.mockObserver.next();
//    assertNotNull(this.mockObserver.getCurrentState());
//    this.mockObserver.next();
//    assertEquals(this.mockObserver.getCurrentState().getPlayerLocation(this.AIPlayer1),
//            new Coordinate(3, 3));
//    this.mockObserver.next();
//    assertEquals(this.mockObserver.getCurrentState().getPlayerLocation(this.AIPlayer2),
//            new Coordinate(0, 0));
//    this.mockObserver.next();
//    assertEquals(this.mockObserver.getCurrentState().getPlayerLocation(this.AIPlayer1),
//            new Coordinate(1, 3));
//  }
//
//  @Test
//  public void testGameOver() {
//    setup();
//    this.mockReferee.runGame();
//    while(true) {}
////    assertTrue(this.mockObserver.getGameOver());
////    for (int i = 0; i < 10; i++) {
////      this.mockObserver.next();
////    }
////    State lastState = this.mockObserver.getCurrentState();
////    for (int i = 0; i < 10; i++) {
////      this.mockObserver.next();
////    }
////    assertEquals(lastState.getRound(), this.mockObserver.getCurrentState().getRound());
//  }
//
//
//
//  @Test
//  public void testObserver() {
//    Observer o = new Observer();
//    Referee ref = new Referee(o);
//    List<Player> players = new ArrayList<>();
//    AIPlayer p1 = new AIPlayer(new RiemannStrategy(), "john", "black");
//    AIPlayer p2 = new AIPlayer(new RiemannStrategy(), "jim", "red");
//    players.add(p1);
//    players.add(p2);
//    ref.startNewGame(players);
//  }
//
//  @Test
//  public void testGamePanel() {
//    JFrame frame = new JFrame();
//    State s = new State();
//    GamePanel game = new GamePanel();
//    game.setState(s);
//    frame.add(game);
//    frame.pack();
//    frame.setVisible(true);
//  }
//
//
//
//}