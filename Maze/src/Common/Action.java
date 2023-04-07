package Common;

/**
 * Represents an action a player can take in the game (Pass or MoveAction)
 * NOTE: isMove() must be called and must return true for getMove to return a MoveAction
 */
public interface Action {

  /**
   * Tells if this Action is a MoveAction
   * @return true if this is a MoveAction
   */
  public boolean isMove();

  /**
   * Gets the MoveAction if it is a MoveAction (if Pass returns null)
   * @return the MoveAction if this is a MoveAction
   */
  public MoveAction getMove();


}
