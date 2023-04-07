package Common;
import Players.*;
import Referee.*;
import Enumerations.*;
/**
 * Represents a Coordinate on the board in (row, column) order.
 */
public class Coordinate implements Comparable<Coordinate>{
  private final int r, c;

  /**
   * Create a coordinate with (r, c).
   * @param r the row coordinate
   * @param c the column coordinate
   */
  public Coordinate(int r, int c) {
    this.r = r;
    this.c = c;
  }

  /**
   * Gets the row value.
   * @return r
   */
  public int getRow() {
    return this.r;
  }

  /**
   * Gets the column value
   * @return c
   */
  public int getCol() {
    return this.c;
  }

  /**
   * Adds the direction to the coordinate
   * @param d direction
   * @return a new coordinate with the added component
   */
  public Coordinate addDirection(Direction d) {
    switch(d) {
      case DOWN:
        return new Coordinate(this.getRow()+1, this.getCol());
      case UP:
        return new Coordinate(this.getRow()-1, this.getCol());
      case LEFT:
        return new Coordinate(this.getRow(), this.getCol()-1);
      case RIGHT:
        return new Coordinate(this.getRow(), this.getCol()+1);
    }
    return null;
  }


  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Coordinate)) {
      return false;
    }

    Coordinate c = (Coordinate) o;

    return this.getRow() == c.getRow() && this.getCol() == c.getCol();
  }

  @Override
  public int hashCode()
  {
    return 100 * this.getRow() + this.getCol();
  }

  @Override
  public int compareTo(Coordinate c) {
    if (Integer.compare(this.r, c.getRow()) == 0) {
      return Integer.compare(this.c, c.getCol());
    } else {
      return Integer.compare(this.r, c.getRow());
    }
  }

  @Override
  public String toString() {
    return this.r + "," + this.c;
  }
}
