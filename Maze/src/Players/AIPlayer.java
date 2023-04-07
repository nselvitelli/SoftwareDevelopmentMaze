package Players;
import java.awt.*;
import java.util.Optional;
import Common.*;
import Enumerations.*;

/**
 * Represents a computer player of the game that uses a particular strategy to play the game of
 * Labyrinth.
 */
public class AIPlayer implements Player {

  //The strategy the player will use.
  private final IStrategy strategy;
  //Name of the player
  private final String name;
  //Color of a player
  private final Color color; //todo delete

  //The goal when the game starts then the home when the player reaches the goal
  protected Coordinate destination;
  //The state for the player
  protected State playerState;
  //Holds if the player won
  protected Optional<Boolean> won;

  /**
   Creates an instance of an AIPlayer that will use the given strategy.
   * @param strategy the strategy the player will use to determine moves.
   * @param name the name of the player
   * @param color the color of the player
   * NOTE: Preferable the strategy is unique to this player, although there are no consequences if
   * this is broken.
   */
  public AIPlayer(IStrategy strategy, String name, Color color) {
    this.name = name;
    this.strategy = strategy;
    this.won = Optional.empty();
    this.color = color;
  }

  @Override
  public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
    this.destination = destination;
    ps.ifPresent(state -> this.playerState = state);
    return ResponseStatus.OK;
  }

  @Override
  public Action takeTurn(State state) {
    this.playerState = state;
    return this.strategy.thinkNextMove(this.playerState, this.destination);
  }

  @Override
  public ResponseStatus won(Boolean w) {
    this.won = Optional.of(w);
    return ResponseStatus.OK;
  }

  @Override
  public Board proposeBoard(int rows, int columns) {
    return new Board(rows, columns);
  }

    @Override
  public String getName() {
    return this.name;
  }
}
