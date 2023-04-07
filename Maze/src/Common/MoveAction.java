package Common;
import Players.*;
import Referee.*;
import Enumerations.*;
import java.util.Objects;

/**
 * Class used to store information about a move action.
 * Index is the row/column index
 * Direction is the move direction of the row/column
 * numRotations is the number of times the spare tile should be rotated counter-clockwise before
 * being inserted into the board
 */
public class MoveAction implements Action{
  private final int index;
  private final Direction d;
  private final int numRotations;
  private final Coordinate moveTo;

  public MoveAction(int index, Direction d, int numRotations) {
    this(index, d, numRotations, null);
  }

  public MoveAction(int index, Direction d, int numRotations, Coordinate moveTo) {
    this.index = index;
    this.d = d;
    this.numRotations = numRotations % 4;
    this.moveTo = moveTo;
  }

  public MoveAction getInverse() {
    return new MoveAction(this.index, this.d.oppositeDirection(), (4-this.numRotations)%4);
  }

  public int getIndex() {
    return this.index;
  }

  public Direction getDirection() {
    return this.d;
  }

  public int getNumRotations() {
    return this.numRotations;
  }

//  public Coordinate getMoveTo() {return this.moveTo;}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MoveAction that = (MoveAction) o;
    return index == that.index && d == that.d && numRotations == that.numRotations;
  }

  public boolean equalsIgnoreRotation(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MoveAction that = (MoveAction) o;
    return index == that.index && d == that.d;
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, d, numRotations);
  }

  public Coordinate getMoveTo() {
    return this.moveTo;
  }

  @Override
  public boolean isMove() {
    return true;
  }

  @Override
  public MoveAction getMove() {
    return this;
  }

}
