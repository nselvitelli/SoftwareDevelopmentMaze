package Enumerations;

/**
 * Data representation of a direction possible to be moved for a Tile
 */
public enum Direction {
  UP, DOWN, LEFT, RIGHT;

  public Direction oppositeDirection() {
    switch(this) {
      case DOWN:
        return UP;
      case UP:
        return DOWN;
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
    }
    return this;
  }
}
