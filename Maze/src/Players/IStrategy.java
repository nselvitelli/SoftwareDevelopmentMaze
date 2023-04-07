package Players;
import java.util.Optional;
import Common.*;
import Referee.*;
import Enumerations.*;

/**
 * Represents a strategy for an AIPlayer to use.
 */
public interface IStrategy {

  /**
   * Thinks of the next move a player should perform given the current state of the game and
   * the destination the player wishes to arrive at
   * @return an empty Optional if the strategy suggests a pass or an Optional with the MoveAction if
   * the strategy recommends a move.
   */
  public Action thinkNextMove(State playerState, Coordinate destination);

}
