package Common;

import Enumerations.Shape;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.awt.*;
import java.util.List;

import static Enumerations.Direction.*;
import static org.junit.Assert.*;

import Common.*;
import Enumerations.*;

public class StateTest {

    public Tile initialSpare;
    public Tile[][] mockGameboard;
    public Board mockBoard;
    public State mockState;
    public Color player1Color;
    public Color player2Color;
    public Color player3Color;


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

        this.mockGameboard = tiles;
        this.mockBoard = new Board(this.mockGameboard);
        this.mockState = new State(this.mockBoard, this.initialSpare);

        this.player1Color = Color.RED;
        Coordinate home1 = new Coordinate(1, 1);
        Coordinate goal1 = new Coordinate(1, 3);
        this.player2Color = Color.GREEN;
        Coordinate home2 = new Coordinate(3, 3);
        Coordinate goal2 = new Coordinate(3, 1);
        this.player3Color = Color.BLUE;
        Coordinate home3 = new Coordinate(3, 1);
        Coordinate goal3 = new Coordinate(1, 3);

        this.mockState.addPlayer(this.player1Color, goal1, home1);
        this.mockState.addPlayer(this.player2Color, goal2, home2);
        this.mockState.addPlayer(this.player3Color, goal3, home3);
    }


    @Test
    public void testNextTurn() {
        assertEquals(this.player1Color, this.mockState.whoseTurn());
        //Used to denote an invalid turn that moves onto next player
        this.mockState.nextTurn();
        assertEquals(this.player2Color, this.mockState.whoseTurn());
        this.mockState.nextTurn();
        assertEquals(this.player3Color, this.mockState.whoseTurn());
        this.mockState.nextTurn();
        assertEquals(this.player1Color, this.mockState.whoseTurn());
    }


    @Test
    public void testKickPlayer() {
        assertEquals(this.player1Color, this.mockState.whoseTurn());
        this.mockState.nextTurn();
        this.mockState.nextTurn();
        this.mockState.nextTurn();
        assertEquals(this.player1Color, this.mockState.whoseTurn());
        this.mockState.kickPlayer(this.player1Color);
        assertEquals(this.player2Color, this.mockState.whoseTurn());
        this.mockState.nextTurn();
        this.mockState.nextTurn();
        assertEquals(this.player2Color, this.mockState.whoseTurn());
        this.mockState.kickPlayer(this.player2Color);
        assertEquals(this.player3Color, this.mockState.whoseTurn());
        this.mockState.nextTurn();
        assertEquals(this.player3Color, this.mockState.whoseTurn());
    }

    @Test
    public void testKickPlayerNoMorePlayers() {
        assertEquals(this.player1Color, this.mockState.whoseTurn());
        this.mockState.kickPlayer(this.player1Color);
        this.mockState.kickPlayer(this.player2Color);
        this.mockState.kickPlayer(this.player3Color);
        assertThrows(IllegalArgumentException.class, () -> this.mockState.kickPlayer(this.player1Color));
        assertThrows(IllegalStateException.class, () -> this.mockState.whoseTurn());
    }

    @Test
    public void testAvailableGoalsHomeNotCountedAsGoal() {
        // player 1 reached 1 goal, goes home
        // player 2 reached 2 goals, hasn't gone home yet
        // player 2 should win

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
                Color.RED, new Coordinate(3, 3),
                Color.BLUE, new Coordinate(3, 3)
        ));
        Map<Color, List<Coordinate>> playerGoalsReached = new HashMap<>(Map.of(
                Color.RED, new ArrayList<>(List.of(new Coordinate(1, 3))),
                Color.BLUE, new ArrayList<>(List.of(new Coordinate(3, 1), new Coordinate(5, 1), new Coordinate(3, 5)))
        ));

        MoveAction lastMove = new MoveAction(5, Direction.UP, 0, new Coordinate(6, 6));
        MoveAction currentMove = new MoveAction(0, Direction.UP, 0, new Coordinate(3, 3));

        Queue<Coordinate> availableGoals = new LinkedList<>();

        State state = new State(this.mockBoard, this.initialSpare, playerColors, 0,
                playerLocations, homeLocations, goalLocations, playerGoalsReached,
                lastMove, currentMove, 4, availableGoals);

        state.performMove(currentMove);

        Coordinate goal = state.updatePlayerGoal(Color.RED);
        assertEquals(homeLocations.get(Color.RED), goal);

        List<Color> winners = state.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Color.BLUE, winners.get(0));
    }

//    @Test
//    public void testPerformMove() {
//        Coordinate c;
//        Tile t;
//
//        //Player 1 tries to move the first movable column down but can't move!
//        c = new Coordinate(0, 1);
//        t = this.mockGameboard[0][1];
//        assertThrows(IllegalArgumentException.class, () -> this.mockState.performMove(new MoveAction(1, DOWN, 0, c)));
//        assertEquals(this.mockState.getPlayerLocation(this.player1Color), new Coordinate(0, 0));
//        assertEquals(this.mockGameboard[0][1], t);
//
//        //Player 2 tries to move the first movable column down and can move!
//        c = new Coordinate(1, 2);
//        t = this.mockGameboard[0][1];
//        this.mockState.performMove(new MoveAction(1, DOWN, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(this.player1Color), c);
//        assertEquals(this.mockGameboard[1][1], t);
//        assertEquals(this.mockGameboard[0][1], this.initialSpare);
//
//        //Player 3, being the silly goofball he is, tries to move to an invalid location:
//        c = new Coordinate(-1, -6);
//        this.mockState.performMove(new MoveAction(1, DOWN, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(this.player3Color), new Coordinate(0, 0));
//        assertEquals(this.mockGameboard[1][1], t);
//        assertEquals(this.mockGameboard[0][1], this.initialSpare);
//
//        //Player 1 moves down one spot to (1, 0) and slides the last movable column UP
//        c = new Coordinate(1, 0);
//        t = this.mockGameboard[1][5];
//        Tile tSpare = this.mockGameboard[0][5];
//        this.mockState.performMove(new MoveAction(5, Direction.UP, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(this.player1Color), c);
//        assertEquals(this.mockGameboard[0][5], t);
//
//        //Player 2 then moves Player 1 off the board! Oh no! But luckily Player 1 is on other side!
//        c = new Coordinate(2, 1);
//        t = this.mockGameboard[1][1];
//        this.mockState.performMove(new MoveAction(1, LEFT, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(this.player2Color), c);
//        assertEquals(this.mockState.getPlayerLocation(this.player1Color), new Coordinate(1, 6));
//        assertEquals(this.mockGameboard[1][0], t);
//        assertEquals(this.mockGameboard[1][6], tSpare);
//    }
//
//    @Test
//    public void testPlayerGoesToGoalThenHome() {
//        //Add a new player to the board with easily accessible home and goal
//        Color pColor = Color.ORANGE;
//        Coordinate home = new Coordinate(6, 6);
//        Coordinate goal = new Coordinate(0, 6);
//        this.mockState.addPlayer(pColor, goal, home);
//
//        //Kick players 1-3 and leave only our last guy left so he can do every turn
//        this.mockState.kickPlayer(this.player1Color);
//        this.mockState.kickPlayer(this.player2Color);
//        this.mockState.kickPlayer(this.player3Color);
//
//        //Player tries to move the first movable column down but can't move so still on home.
//        Coordinate c = new Coordinate(0, 0);
//        this.mockState.performMove(new MoveAction(1, DOWN, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(pColor), this.mockState.getPlayerHome(pColor));
//        assertFalse(this.mockState.playerReachedGoal(pColor));
//
//        //Player moves to goal:
//        this.mockState.performMove(new MoveAction(3, DOWN, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(pColor), this.mockState.getPlayerGoal(pColor));
//        assertTrue(this.mockState.playerReachedGoal(pColor));
//
//        //Player moves to home:
//        this.mockState.performMove(new MoveAction(3, RIGHT, 0, c));
//        assertEquals(this.mockState.getPlayerLocation(pColor), this.mockState.getPlayerHome(pColor));
//        assertTrue(this.mockState.playerReachedGoal(pColor));
//
//        assertTrue(this.mockState.hasWinner());
//    }
//
//    @Test
//    public void testSeriesOfInvalidMoves() {
//        //Add a new player to the board with easily accessible home and goal
//        Color pColor = Color.ORANGE;
//        Coordinate home = new Coordinate(6, 6);
//        Coordinate goal = new Coordinate(0, 6);
//        this.mockState.addPlayer(pColor, goal, home);
//
//        //Kick players 1-3 and leave only our last guy left so he can do every turn
//        this.mockState.kickPlayer(this.player1Color);
//        this.mockState.kickPlayer(this.player2Color);
//        this.mockState.kickPlayer(this.player3Color);
//
//        //Make a copy of the board to store initial tile configuration:
//        Tile[][] copyGameboard = new Tile[7][7];
//        for (int row = 0; row < this.mockGameboard.length; row++) {
//            System.arraycopy(this.mockGameboard[row], 0, copyGameboard[row], 0, this.mockGameboard.length);
//        }
//
//        //Perform a series of invalid moves:
//        //Player should be able to move to coordinate c:
//        final Coordinate c = new Coordinate(0, 6);
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(-1, DOWN, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(1, DOWN, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(5, DOWN, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(-1, RIGHT, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(3, LEFT, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(8, UP, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(1, DOWN, -20, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(-2, RIGHT, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(0, null, 0, c)));
//        assertThrows(IllegalArgumentException.class,
//                () -> this.mockState.performMove(new MoveAction(0, RIGHT, 0, null)));
//        //Player is still at home:
//        assertEquals(this.mockState.getPlayerLocation(pColor), this.mockState.getPlayerHome(pColor));
//
//        //The board should have remained the same the entire time:
//        for (int row = 0; row < this.mockGameboard.length; row++) {
//            for (int col = 0; col < this.mockGameboard.length; col++) {
//                assertEquals(this.mockGameboard[row][col], copyGameboard[row][col]);
//            }
//        }
//    }
}
