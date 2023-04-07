package Players;
import java.awt.*;
import java.util.Optional;
import Common.*;
import Enumerations.*;

/**
 * A player of the game of Labyrinth
 */
public interface Player {

  /**
   * The player makes a board for the referee
   * @param rows the number of rows
   * @param columns the number of columns
   * @return a board
   */
  Board proposeBoard(int rows, int columns);

  /**
   * Sets the player's destination and gives them a PlayerState to view information about the game
   * as the game starts.
   * @param ps the starting state of the game
   * @param destination either the player's goal tile or home tile
   * NOTE: destination depends on if the player has already reached the goal tile or not
   */
  ResponseStatus setup(Optional<State> ps, Coordinate destination);

  /**
   * Prompts the player for their move and provides information on the form of a PlayerState
   * @return an option of a MoveAction the player wants to perform
   * Note: the empty option represents a pass
   */
  Action takeTurn(State ms);

  /**
   * Tells the player if they won or lost
   * @param w true of the player won
   */
  ResponseStatus won(Boolean w);

  /*
  Getters:
   */

  String getName();
}
