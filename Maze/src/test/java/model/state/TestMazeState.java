package model.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import model.board.BasicTile;
import model.board.Board;
import model.board.Board7x7;
import util.Direction;
import model.board.Gem;
import model.board.Tile;
import org.junit.jupiter.api.Test;
import util.Posn;

public class TestMazeState {

  @Test
  public void testWhichPlayerTurn() {
    Board board = new Board7x7();
    List<PlayerData> players = new ArrayList<>();
    PlayerData first = new PlayerData(new Posn(0, 0), new Posn(1, 1), Color.red);
    players.add(first);
    players.add(new PlayerData(new Posn(0, 0), new Posn(3, 1), Color.blue));
    players.add(new PlayerData(new Posn(0, 0), new Posn(1, 3), Color.green));
    players.add(new PlayerData(new Posn(0, 0), new Posn(1, 5), Color.yellow));

    Tile spare = new BasicTile(EnumSet.noneOf(Direction.class), Arrays.asList(Gem.EMERALD, Gem.MAGNESITE));

    State state = new MazeState(board, players, spare, Optional.empty());

    assertEquals(first, state.whichPlayerTurn());

    players = new ArrayList<>();
    State state2 = new MazeState(board, players,
        spare, Optional.empty());
    assertThrows(IllegalArgumentException.class, () -> state2.whichPlayerTurn());
  }

  @Test
  public void testCanApplyAction() {
    Posn slidePos = new Posn(0,0);
    Posn startPlayerPos = new Posn(0, 0);
    Posn targetPos = new Posn(1, 0);

    Action action = BasicTurnAction.builder()
        .rotateSpare(0)
        .slideTilePosition(slidePos)
        .slideTileDirection(Direction.DOWN)
        .targetPlayerPosition(targetPos)
        .build();

    Board board = new Board7x7();

    for (int row = 0; row < board.getBoardHeight(); row++) {
      for (int col = 0; col < board.getBoardWidth(); col++) {
        int gemIndex = Gem.values().length - (1 + (row * board.getBoardHeight()) + col);
        board.placeTileSafely(new Posn(col, row),
            new BasicTile(
                EnumSet.of(Direction.UP, Direction.LEFT),
                Arrays.asList(Gem.values()[gemIndex], Gem.values()[gemIndex - 1])));
      }
    }

    PlayerData player = new PlayerData(startPlayerPos, new Posn(1, 1), Color.blue);

    Tile spareTile = new BasicTile(
        EnumSet.of(Direction.DOWN, Direction.RIGHT),
        Arrays.asList(Gem.CHROME_DIOPSIDE, Gem.EMERALD));

    State state = new MazeState(board, Arrays.asList(player),  spareTile, Optional.empty());

    Board boardCopy = board.getCopy();

    assertTrue(state.canApplyAction(action));
    assertEquals(boardCopy, board);
  }


  @Test
  public void testApplyActionSafelyFalse() {
    Posn slidePos = new Posn(1,0);
    Posn startPlayerPos = new Posn(0, 0);
    Posn targetPos = new Posn(1, 0);

    Action action = BasicTurnAction.builder()
        .rotateSpare(0)
        .slideTilePosition(slidePos)
        .slideTileDirection(Direction.UP)
        .targetPlayerPosition(targetPos)
        .build();

    Board board = new Board7x7();

    for (int row = 0; row < board.getBoardHeight(); row++) {
      for (int col = 0; col < board.getBoardWidth(); col++) {
        int gemIndex = Gem.values().length - (1 + (row * board.getBoardHeight()) + col);
        board.placeTileSafely(new Posn(col, row),
            new BasicTile(
                EnumSet.of(Direction.UP, Direction.LEFT),
                Arrays.asList(Gem.values()[gemIndex], Gem.values()[gemIndex - 1])));
      }
    }

    PlayerData player = new PlayerData(startPlayerPos, new Posn(1, 1), Color.blue);

    Tile spareTile = new BasicTile(
        EnumSet.of(Direction.DOWN, Direction.RIGHT),
        Arrays.asList(Gem.CHROME_DIOPSIDE, Gem.EMERALD));

    State state = new MazeState(board, Arrays.asList(player), spareTile, Optional.empty());

    assertEquals(state, state.applyActionSafely(action));
  }

  @Test
  public void testApplyActionSafely() {

    Posn slidePos = new Posn(0,0);
    Posn startPlayerPos = new Posn(0, 0);
    Posn targetPos = new Posn(1, 1);

    Action action = BasicTurnAction.builder()
        .rotateSpare(0)
        .slideTilePosition(slidePos)
        .slideTileDirection(Direction.DOWN)
        .targetPlayerPosition(targetPos)
        .build();

    Board board = new Board7x7();

    for (int row = 0; row < board.getBoardHeight(); row++) {
      for (int col = 0; col < board.getBoardWidth(); col++) {
        int gemIndex = Gem.values().length - (1 + (row * board.getBoardHeight()) + col);
        board.placeTileSafely(new Posn(col, row),
            new BasicTile(
                EnumSet.of(Direction.UP, Direction.LEFT, Direction.RIGHT),
                Arrays.asList(Gem.values()[gemIndex], Gem.values()[gemIndex - 1])));
      }
    }

    PlayerData player = new PlayerData(startPlayerPos, targetPos, Color.blue);

    Tile spareTile = new BasicTile(
        EnumSet.of(Direction.DOWN, Direction.RIGHT),
        Arrays.asList(Gem.CHROME_DIOPSIDE, Gem.EMERALD));

    List<PlayerData> players = new ArrayList<>(Arrays.asList(player));

    State state = new MazeState(board, players,  spareTile, Optional.empty());
    State nextState = state.applyActionSafely(action);

    assertNotEquals(state, nextState);
  }

  @Test
  public void testIsGameOver() {

    Board board = new Board7x7();
    List<PlayerData> players = new ArrayList<>();
    PlayerData first = new PlayerData(Color.ORANGE, new Posn(0, 0), new Posn(3, 1), false);
    players.add(first);
    players.add(new PlayerData(Color.BLUE, new Posn(0, 1), new Posn(1, 1), true));
    players.add(new PlayerData(Color.RED,  new Posn(0, 2), new Posn(1, 5), false));
    players.add(new PlayerData(Color.GREEN,  new Posn(0, 3), new Posn(1, 3), false));

    for(int row = 0; row < board.getBoardHeight(); row++) {
      for(int col = 0; col < board.getBoardWidth(); col++) {
        int gemIndex = Gem.values().length - (1 + (row * board.getBoardHeight()) + col);
        board.placeTileSafely(new Posn(col, row),
            new BasicTile(EnumSet.of(Direction.LEFT, Direction.RIGHT),
                Arrays.asList(Gem.values()[gemIndex], Gem.values()[gemIndex - 1])));
      }
    }

    Tile spare = new BasicTile(EnumSet.noneOf(Direction.class), Arrays.asList(Gem.EMERALD, Gem.MAGNESITE));



    // game over on no players left in the state
    State state = new MazeState(board, new ArrayList<>(), spare, Optional.empty());
    assertTrue(state.isGameOver());

    // Game Not over
    state = new MazeState(board, players,  spare, Optional.empty());
    assertFalse(state.isGameOver());

    //update state by having the first player pass their turn
    state = state.applyActionSafely(new PassAction());
    assertFalse(state.isGameOver());

    Action action = BasicTurnAction.builder()
        .targetPlayerPosition(new Posn(1, 1))
        .slideTilePosition(new Posn(0, 2))
        .slideTileDirection(Direction.RIGHT)
        .build();
    State nextState = state.applyActionSafely(action);
    assertNotEquals(state, nextState);
    assertTrue(nextState.isGameOver());
  }

  @Test
  public void testGetWinner() {
    Board board = new Board7x7();
    List<PlayerData> players = new ArrayList<>();
    PlayerData first = new PlayerData(Color.ORANGE,  new Posn(0, 1), new Posn(3, 1), true);
    players.add(first);
    players.add(new PlayerData(Color.BLUE,  new Posn(0, 1), new Posn(1, 1), true));
    players.add(new PlayerData(Color.RED,  new Posn(0, 2), new Posn(1, 5), false));
    players.add(new PlayerData(Color.GREEN,  new Posn(0, 3), new Posn(1, 3), false));

    for(int row = 0; row < board.getBoardHeight(); row++) {
      for(int col = 0; col < board.getBoardWidth(); col++) {
        int gemIndex = Gem.values().length - (1 + (row * board.getBoardHeight()) + col);
        board.placeTileSafely(new Posn(col, row),
            new BasicTile(EnumSet.of(Direction.LEFT, Direction.RIGHT),
                Arrays.asList(Gem.values()[gemIndex], Gem.values()[gemIndex - 1])));
      }
    }

    Tile spare = new BasicTile(EnumSet.noneOf(Direction.class), Arrays.asList(Gem.EMERALD, Gem.MAGNESITE));

    State state = new MazeState(board, players, spare, Optional.empty());

    Action action = BasicTurnAction.builder()
        .targetPlayerPosition(new Posn(3, 1))
        .slideTilePosition(new Posn(0, 2))
        .slideTileDirection(Direction.RIGHT)
        .build();
    State nextState = state.applyActionSafely(action);
    assertNotEquals(state, nextState);
    assertTrue(nextState.isGameOver());

    Optional<PlayerData> winner = nextState.getWinner();
    assertTrue(winner.isPresent());
    assertEquals(first.getAvatar(), winner.get().getAvatar());
  }

  @Test
  public void testKickCurrentPlayer() {
    Board board = new Board7x7();

    List<PlayerData> players = new ArrayList<>();

    PlayerData first = new PlayerData(Color.ORANGE, new Posn(0, 0), new Posn(1, 3), false);
    players.add(first);
    PlayerData second = new PlayerData(Color.BLUE, new Posn(0, 1), new Posn(1, 1), true);
    players.add(second);
    players.add(new PlayerData(Color.RED, new Posn(0, 2), new Posn(1, 5), false));
    players.add(new PlayerData(Color.GREEN, new Posn(0, 3), new Posn(3, 3), false));

    Tile spare = new BasicTile(EnumSet.noneOf(Direction.class), Arrays.asList(Gem.EMERALD, Gem.MAGNESITE));

    State state = new MazeState(board, players, spare, Optional.empty());
    state = state.kickCurrentPlayer();
    assertEquals(second, state.whichPlayerTurn());
    state = state.kickCurrentPlayer();
    state = state.kickCurrentPlayer();
    state = state.kickCurrentPlayer();
    assertTrue(state.equals(state.kickCurrentPlayer()));
  }
}
