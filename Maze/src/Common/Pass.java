package Common;

/**
 * A Pass action.
 */
public class Pass implements Action{

  public Pass() {}

  @Override
  public boolean isMove() {
    return false;
  }

  @Override
  public MoveAction getMove() {
    return null;
  }

}
