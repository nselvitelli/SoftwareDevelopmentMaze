package Referee;

import Enumerations.Shape;
import Integration.BrokenPlayer;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import Common.*;
import Players.*;
import Enumerations.*;
import Referee.*;

/**
 * Testing for Riemann Strategy.
 */
public class RefereeTest {

    public Tile initialSpare;
    public Board mockBoard;


    @Before
    public void setup() {
        Utils.setAvailableGems();
        Tile[][] tiles = new Tile[7][7];

        tiles[0][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE90);
        tiles[0][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
        tiles[0][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE270);
        tiles[1][0] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
        tiles[1][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
        tiles[1][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
        tiles[2][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
        tiles[2][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE270);
        tiles[2][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);

        for (int i = 3; i < 7; i++) {
            for (int j = 3; j < 7; j++) {
                tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
            }
        }

        for (int i = 3; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
            }
        }

        /*
        BOARD LOOKS LIKE:
        + - ┘ | | | |
        | | | | | | |
        └ - └ | | | |
        - - - + + + +
        - - - + + + +
        - - - + + + +
        - - - + + + +
         */
        this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
        this.mockBoard = new Board(tiles);
    }

    @Test
    public void testRunRefereeWithAdditionalAvailableGoals() {
        List<Color> playerColors = List.of(Color.RED, Color.BLUE);

        Map<Color, Coordinate> playerLocations = new HashMap<>(Map.of(
                Color.RED, new Coordinate(3, 5),
                Color.BLUE, new Coordinate(6, 6)
        ));

        Map<Color, Coordinate> homeLocations = new HashMap<>(Map.of(
                Color.RED, new Coordinate(3, 3),
                Color.BLUE, new Coordinate(3, 3)
        ));

        Map<Color, Coordinate> goalLocations = new HashMap<>(Map.of(
                Color.RED, new Coordinate(5, 5),
                Color.BLUE, new Coordinate(1, 5)
        ));
        Map<Color, List<Coordinate>> playerGoalsReached = new HashMap<>(Map.of(
                Color.RED, new ArrayList<>(),
                Color.BLUE, new ArrayList<>()
        ));

        MoveAction lastMove = new MoveAction(5, Direction.UP, 0, new Coordinate(6, 6));
        MoveAction currentMove = new MoveAction(0, Direction.UP, 0, new Coordinate(3, 3));

        Queue<Coordinate> availableGoals = new LinkedList<>(Arrays.asList(new Coordinate(3,5)));

        State state = new State(this.mockBoard, this.initialSpare, playerColors, 0,
                playerLocations, homeLocations, goalLocations, playerGoalsReached,
                lastMove, currentMove, 4, availableGoals);

        List<Player> players = new ArrayList<>(Arrays.asList(
                new AIPlayer(new EuclidStrategy(), "Steve", Color.RED),
                new AIPlayer(new EuclidStrategy(), "Tom", Color.BLUE)));

        Referee referee = new Referee();
        referee.runNewGame(state, players);

        List<Player> winners = referee.getWinners();
        assertEquals(1, winners.size());
        assertEquals("Steve", winners.get(0).getName());
    }

    /*

    + + +
    + + +
    + + +

    p1:
    c: 1, 1
    h: 1, 1
    g: 1, 1
    4 moves

    p1 is a bad player that loops infinitely after third taketurn call
    */

    @Test
    public void testPlayerGoalHomeCurrentAllSameTile() {
        Tile[][] boardTiles = new Tile[3][3];
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                boardTiles[row][col] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
            }
        }
        Board board = new Board(boardTiles);

        Tile spare = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);

        State state = new State(board, spare);

        state.addPlayer(Color.RED, new Coordinate(1, 1), new Coordinate(1, 1), new Coordinate(1, 1));

        Player player = new BrokenPlayer(new EuclidStrategy(), "bad",
                BrokenPlayer.BadBehavior.takeTurn.toString(), 3, true);

        Referee referee = new Referee();
        referee.runNewGame(state, Arrays.asList(player));

        List<Player> kicked = referee.getKickedPlayers();
        assertEquals(1, kicked.size());
    }


//
//  public Tile initialSpare;
//  public Tile[][] mockGameboard;
//  public Board mockBoard;
//  public State mockState;
//  public Player AIPlayer1;
//  public Player AIPlayer2;
//  public TestReferee mockReferee;
//
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
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), "ryan", "red");
//    Coordinate home1 = new Coordinate(1, 3);
//    Coordinate goal1 = new Coordinate(3, 3);
//    this.AIPlayer1.setup(Optional.empty(), goal1);
//    this.AIPlayer2 = new AIPlayer(new RiemannStrategy(), "greg", "blue");
//    Coordinate home2 = new Coordinate(1, 5);
//    Coordinate goal2 = new Coordinate(5, 1);
//    Coordinate current2 = new Coordinate(0, 6);
//    this.AIPlayer2.setup(Optional.empty(), goal2);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1);
//    this.mockState.addPlayer(AIPlayer2, goal2, home2, current2);
//    this.mockReferee = new TestReferee();
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//  @Test
//  public void testPlayerTurn() {
//    setup();
//    State ps1, ps2;
//
//    //Player 1 reaches goal:
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    ps1 = this.mockState.createPlayerState(this.AIPlayer1);
//    assertEquals(ps1.getPlayerLocation(this.AIPlayer1), new Coordinate(3, 3));
//    //Player 2 goes to top left:
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    ps2 = this.mockState.createPlayerState(this.AIPlayer2);
//    assertEquals(ps2.getPlayerLocation(this.AIPlayer2), new Coordinate(0, 0));
//    //Player 1 reaches home:
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    ps1 = this.mockState.createPlayerState(this.AIPlayer1);
//    assertEquals(ps1.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 3));
//    //Player 2 remains in top left with same move:
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    ps2 = this.mockState.createPlayerState(this.AIPlayer2);
//    assertEquals(ps2.getPlayerLocation(this.AIPlayer2), new Coordinate(0, 0));
//  }
//
//  @Test
//  public void testRunGameWinner() {
//    setup();
//    State ps1, ps2;
//
//    //Runs a game where Player 1 reaches the goal and Player 2 loses:
//    this.mockReferee.runGame();
//
//    ps1 = this.mockState.createPlayerState(this.AIPlayer1);
//    ps2 = this.mockState.createPlayerState(this.AIPlayer2);
//
//    assertEquals(ps1.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 3));
//    assertEquals(ps2.getPlayerLocation(this.AIPlayer2), new Coordinate(0, 0));
//    assertTrue(this.AIPlayer1.didWin().isPresent());
//    assertTrue(this.AIPlayer1.didWin().get());
//    assertTrue(this.AIPlayer2.didWin().isPresent());
//    assertFalse(this.AIPlayer2.didWin().get());
//  }
//
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
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), "Jim", "red");
//    Coordinate home1 = new Coordinate(1, 1);
//    Coordinate goal1 = new Coordinate(3, 3);
//    this.AIPlayer2 = new AIPlayer(new RiemannStrategy(), "Jane", "blue");
//    Coordinate home2 = new Coordinate(1, 3);
//    Coordinate goal2 = new Coordinate(3, 3);
//    Coordinate current2 = new Coordinate(4, 4);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1);
//    this.mockState.addPlayer(AIPlayer2, goal2, home2, current2);
//    this.mockReferee = new TestReferee();
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//  @Test
//  public void testPlayerTurn2() {
//    setup2();
//    State ps1, ps2;
//    //Player 1 goes off board to top then as far left as possible:
//    this.mockReferee.playerTurn(this.AIPlayer1);
//    ps1 = this.mockState.createPlayerState(this.AIPlayer1);
//    assertEquals(ps1.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 0));
//    //Player 2 reaches goal with move:
//    this.mockReferee.playerTurn(this.AIPlayer2);
//    ps2 = this.mockState.createPlayerState(this.AIPlayer2);
//    assertEquals(ps2.getPlayerLocation(this.AIPlayer2), new Coordinate(3, 3));
//  }
//
//  public void setup3() {
//    Utils.setAvailableGems();
//    Tile[][] tiles = new Tile[7][7];
//
//    tiles[0][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
//    tiles[0][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//    tiles[0][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//    tiles[1][0] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//    tiles[1][1] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE90);
//    tiles[1][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//    tiles[2][0] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//    tiles[2][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//    tiles[2][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE90);
//
//    for(int i=3;i<7;i++) {
//      for(int j=3;j<7;j++) {
//        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//      }
//    }
//
//    for(int i=0;i<3;i++) {
//      for(int j=3;j<7;j++) {
//        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//      }
//    }
//
//    for(int i=3;i<7;i++) {
//      for(int j=0;j<3;j++) {
//        tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
//      }
//    }
//
//    /*
//    BOARD LOOKS LIKE:
//        ["┼","│","│","│","│","┼","┼"],
//        ["─","┌","│","│","│","┼","┼"],
//        ["─","─","┌","│","│","┼","┼"],
//        ["─","─","─","│","│","┼","┼"],
//        ["─","─","─","│","│","┼","┼"],
//        ["─","─","─","│","│","┼","┼"],
//        ["─","─","─","│","│","┼","┼"]],
//     */
//
//    //Initialize PlayerState
//    this.initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
//    this.mockGameboard = tiles;
//    this.mockBoard = new Board(this.mockGameboard);
//    this.mockState = new State(this.mockBoard, this.initialSpare);
//
//    this.AIPlayer1 = new AIPlayer(new RiemannStrategy(), "Jim", "red");
//    Coordinate home1 = new Coordinate(1, 1);
//    Coordinate goal1 = new Coordinate(5, 5);
//    this.AIPlayer1.setup(Optional.empty(), goal1);
//
//    this.mockState.addPlayer(AIPlayer1, goal1, home1);
//    this.mockReferee = new TestReferee();
//    this.mockReferee.setupGame(this.mockState);
//  }
//
//
//  @Test
//  public void testRunGamePass() {
//    setup3();
//    State ps1;
//    //Player 1 passes and because he is the only player the game ends and he wins!
//    this.mockReferee.runGame();
//    ps1 = this.mockState.createPlayerState(this.AIPlayer1);
//    assertEquals(ps1.getPlayerLocation(this.AIPlayer1), new Coordinate(1, 1));
//    assertTrue(this.AIPlayer1.didWin().isPresent());
//    assertTrue(this.AIPlayer1.didWin().get());
//  }
//
//  public class BadStrategy implements IStrategy{
//    @Override
//    public Action thinkNextMove(State playerState, Coordinate destination) {
//      return new MoveAction(4, Direction.UP, 2, new Coordinate(9, 6));
//    }
//  }
//
//  public void setup4() {
//    Utils.setAvailableGems();
//
//    List<Player> players = new ArrayList<>();
//
//    for(int i=0; i<5; i++) {
//      players.add(new AIPlayer(new BadStrategy(), "bot", "red"));
//    }
//
//    this.AIPlayer1 = new AIPlayer(new EuclidStrategy(), "ryan", "blue");
//    players.add(this.AIPlayer1);
//
//    this.mockReferee = new TestReferee();
//    this.mockReferee.startNewGame(players);
//  }
//
//  /**
//   * Testing the strategy outside the scope of the referee:
//   */
//  @Test
//  public void testRunGameKicking() {
//    setup4();
//    assertTrue(this.AIPlayer1.didWin().isPresent());
//    assertTrue(this.AIPlayer1.didWin().get());
//    assertEquals(this.mockReferee.getKickedPlayers().size(), 5);
//  }
//
//
//  private static class TestReferee extends Referee {
//    public TestReferee() {
//
//    }
//
//    public TestReferee(Observer o ) {
//      super(o);
//    }
//
//    public void playerTurn(Player p) {
//      super.playerTurn(p);
//    }
//  }
//
}
