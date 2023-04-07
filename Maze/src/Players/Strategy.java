package Players;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import Common.*;
import Referee.*;
import Enumerations.*;

/**
 * Strategy represents a strategy that can be used by an AIPlayer to play the game Labyrinth.
 * The AIPlayer is given an Strategy in its constructor to use and should be the only use of
 * Strategy.
 * The only method that need be called is thinkNextMove. This method uses
 * the player's personal PlayerState to calculate the desired move and sets the player's move by
 * using setMove() and setMoveTo() on the Player active in the PlayerState.
 */
public abstract class Strategy implements IStrategy{

  //The PlayerState to be used as the game information
  protected State playerState;

  //The order of directions to be attempted when sliding rows/columns
  protected static List<List<Direction>> directions =
          Arrays.asList(
                  Arrays.asList(Direction.LEFT, Direction.RIGHT),
                  Arrays.asList(Direction.UP, Direction.DOWN));

  /**
   * Creates an ordered list of coordinates that are candidates for potential moves.
   * The first coordinate is the highest priority target. It is the one we want to move to the most.
   * @return the ordered coordinate list
   */
  protected abstract List<Coordinate> createRankings(Coordinate destination);


  /**
   * Comes up with a move for the player using the strategy.
   * Sends the move to the player by setting its move and moveTo fields.
   * @param playerState the playerState has the information of the game that the player knows.
   * @return true if a move has been set; false if the player will be passing this turn
   */
  public Action thinkNextMove(State playerState, Coordinate destination) {
    this.playerState = playerState;
    List<Coordinate> goalOrder = this.createRankings(destination);
    //Map<MoveAction, List<Coordinate>> moveMap = this.createMoveMapping();

    //Look through candidates:
    for(Coordinate candidate : goalOrder) {
      //Loop through horizontal or vertical directions:
      for (List<Direction> dList : Strategy.directions) {
        //Loop through the index to shift:
        for (int i = 0; i < this.playerState.getRows() || i < this.playerState.getCols(); i += 2) {
          //Loop through directions:
          for (Direction d : dList) {
            //Loop through rotations: (Adjust for counter-clockwise as our rotation is clockwise)
            for (int r = 4; r > 0; r--) {
              MoveAction ma = new MoveAction(i, d, r, candidate);
              State copy = playerState.createCopy();
              try{
                playerState.performMove(ma);
                return ma;
              } catch (IllegalArgumentException e) {
                playerState = copy;
              }
            }
          }
        }
      }
    }

    //Player passes turn as nothing is reachable:
    return new Pass();
  }

}