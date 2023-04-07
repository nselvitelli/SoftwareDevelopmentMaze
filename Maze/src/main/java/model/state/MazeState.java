package model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.board.Board;
import util.Direction;
import model.board.Tile;
import util.Tuple;
import util.Util;

/**
 * Represents a state of the game Labyrinth. This state is immutable.
 */
public class MazeState implements State {

  private final Board board;

  // The first PlayerData in the list refers to the player who's turn it is
  private final List<PlayerData> players;
  private final Tile spareTile;

  // represents the previous sliding action done by the action to produce this state
  // the Integer is the row/col index and the Direction is the direction of the slide
  private final Optional<Tuple<Integer, Direction>> previousMove;


  public MazeState(Board board, List<PlayerData> players,
      Tile spareTile, Optional<Tuple<Integer, Direction>> previousMove) {
    this.board = board;
    this.players = players;

    //gets the number of distinct home location coordinates and compares it with the number of players
    if(this.players.stream().map(PlayerData::getHomeLocation).distinct().count() != players.size()){
      throw new IllegalStateException("Home tiles must be unique");
    }
    for(PlayerData playerData : players){
      if(this.board.getMovableColIndices().contains(playerData.getHomeLocation().getX()) ||
          this.board.getMovableRowIndices().contains(playerData.getHomeLocation().getY()) ||
          this.board.getMovableColIndices().contains(playerData.getGoalLocation().getX()) ||
          this.board.getMovableRowIndices().contains(playerData.getGoalLocation().getY())){
        throw new IllegalStateException("Home and goal tiles must be on immovable tiles");
      }
    }

    this.spareTile = spareTile;
    this.previousMove = previousMove;
  }

  @Override
  public PlayerData whichPlayerTurn() {
    if(players.isEmpty()) {
      throw new IllegalArgumentException("No players in the game!");
    }
    return this.players.get(0);
  }

  @Override
  public boolean canApplyAction(Action action) {
    return action.isValidActionOn(this.board, this.spareTile, whichPlayerTurn(), this.previousMove);
  }

  @Override
  public State applyActionSafely(Action action) {
    if (!this.canApplyAction(action)) {
      return this;
    }

    Board newBoard = this.board.getCopy();
    List<PlayerData> newPlayerList = Util.shallowCopyOf(this.players);
    Optional<Tuple<Integer, Direction>> newPrevMove;

    Optional<Tuple<Integer, Direction>> plannedMove = action.getPlannedBoardMove();
    if(plannedMove.isPresent()) {
      newPrevMove = plannedMove;
    }
    else {
      newPrevMove = previousMove;
    }

    Tile currentSpare = this.spareTile.getCopy();

    // action.accept(...) mutates the board, spare tile, and list of players
    Tile newSpare = action.accept(newBoard, currentSpare, newPlayerList);

    //push current player to end of list
    newPlayerList.add(newPlayerList.remove(0));

    return new MazeState(newBoard, newPlayerList, newSpare, newPrevMove);
  }

  @Override
  public boolean isGameOver() {
    return this.getWinner().isPresent() || this.players.isEmpty();
  }

  @Override
  public Optional<PlayerData> getWinner() {
    for(PlayerData player : this.players) {
      if(player.getVisitedHomeAfterVisitingGoal()) {
        return Optional.of(player);
      }
    }
    return Optional.empty();
  }

  @Override
  public State kickCurrentPlayer() {
    if (players.isEmpty()) {
      return this;
    }
    Board newBoard = this.board.getCopy();
    List<PlayerData> newPlayerList = Util.shallowCopyOf(this.players);
    Tile spare = this.spareTile.getCopy();

    newPlayerList.remove(0);

    return new MazeState(newBoard, newPlayerList, spare, previousMove);
  }

  @Override
  public int getBoardWidth() {
    return board.getBoardWidth();
  }

  @Override
  public int getBoardHeight() {
    return board.getBoardHeight();
  }

  @Override
  public Board getBoard() { return board.getCopy(); }

  @Override
  public Tile getSpareTile() { return spareTile.getCopy(); }

  @Override
  public Optional<Tuple<Integer, Direction>> getPrevMove() {
    return this.previousMove;
  }

  @Override
  public List<PlayerData> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof MazeState) {
      MazeState other = (MazeState)o;
      return board.equals(other.board)
          && players.equals(other.players)
          && spareTile.equals(other.spareTile)
          && previousMove.equals(other.previousMove);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return board.hashCode()
        + players.hashCode()
        + spareTile.hashCode()
        + previousMove.hashCode();
  }
}
