package Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Enumerations.Direction;
import Enumerations.Gem;
import Enumerations.Orientation;
import Enumerations.Shape;

/**
 * a Tile represents the tile of the game Labyrinth. It holds a shape, an orientation, and two gems.
 * It doesn't have much functionality beyond generating random tiles to be used in board creation.
 */
public class Tile {

  private Gem gem1, gem2;
  private Shape shape;
  private Orientation orientation;

  /**
   * Holds the Directions that are reachable for this Tile.
   */
  private List<Direction> reachableTiles;


  /**
   * Explicitly create a Tile.
   * @param gem1 the first gem
   * @param gem2 the second gem
   * @param shape the shape
   * @param orientation the orientation
   */
  public Tile(Gem gem1, Gem gem2, Shape shape, Orientation orientation) {
    this.gem1 = gem1;
    this.gem2 = gem2;
    this.orientation = orientation;
    this.shape = shape;
    this.setReachable();
  }

  /**
   * Randomly generates a tile.
   * @throws NullPointerException when too many tiles have been created for a single board and
   * no more unique pairs of gems exist.
   */
  public Tile() throws NullPointerException{
    this.setRandomShape();
    this.setRandomOrientation();
    this.setRandomGems();
    this.setReachable();
  }

  /**
   * Used for integration testing, converts a char into the corresponding Tile with shape and orientation
   * @param shape char of the tile shape to be used
   */
  public Tile(char shape, Gem g1, Gem g2) {
    switch(shape) {
      case '─':
        this.orientation = Orientation.DEGREE90;
        this.shape = Shape.LINE;
        break;
      case '│':
        this.shape = Shape.LINE;
        this.orientation = Orientation.DEGREE0;
        break;
      case '└':
        this.shape = Shape.CORNER;
        this.orientation = Orientation.DEGREE0;
        break;
      case '┌':
        this.shape = Shape.CORNER;
        this.orientation = Orientation.DEGREE90;
        break;
      case '┐':
        this.shape = Shape.CORNER;
        this.orientation = Orientation.DEGREE180;
        break;
      case '┘':
        this.shape = Shape.CORNER;
        this.orientation = Orientation.DEGREE270;
        break;
      case '┬':
        this.shape = Shape.T;
        this.orientation = Orientation.DEGREE0;
        break;
      case '┤':
        this.shape = Shape.T;
        this.orientation = Orientation.DEGREE90;
        break;
      case '┴':
        this.shape = Shape.T;
        this.orientation = Orientation.DEGREE180;
        break;
      case '├':
        this.shape = Shape.T;
        this.orientation = Orientation.DEGREE270;
        break;
      case '┼':
        this.shape = Shape.CROSS;
        this.orientation = Orientation.DEGREE0;
        break;
    }
    this.gem1 = g1;
    this.gem2 = g2;
    this.setReachable();
  }

  /**
   * Sets the two gems to random gems still available.
   */
  private void setRandomGems() {
    List<Gem> gemPair = Utils.getGems();
    this.gem1 = gemPair.get(0);
    this.gem2 = gemPair.get(1);
  }

  /**
   * Sets the shape to a random shape.
   */
  private void setRandomShape() {
    Shape[] shapes = Shape.values();
    Random rand = new Random();
    this.shape = shapes[rand.nextInt(shapes.length)];
  }

  /**
   * Sets the orientation to a random value.
   */
  private void setRandomOrientation() {
    Orientation[] orientations = Orientation.values();
    Random rand = new Random();
    this.orientation = orientations[rand.nextInt(orientations.length)];
  }

  private void setReachable() {
    ArrayList<Direction> reachable = new ArrayList<Direction>();
    switch(this.shape) {
      case CORNER:
        switch(this.orientation) {
          case DEGREE0:
            reachable.add(Direction.UP);
            reachable.add(Direction.RIGHT);
            break;
          case DEGREE90:
            reachable.add(Direction.DOWN);
            reachable.add(Direction.RIGHT);
            break;
          case DEGREE180:
            reachable.add(Direction.DOWN);
            reachable.add(Direction.LEFT);
            break;
          case DEGREE270:
            reachable.add(Direction.UP);
            reachable.add(Direction.LEFT);
            break;
        }
        this.reachableTiles = reachable;
        break;
      case CROSS:
        reachable.add(Direction.UP);
        reachable.add(Direction.DOWN);
        reachable.add(Direction.LEFT);
        reachable.add(Direction.RIGHT);
        this.reachableTiles = reachable;
        break;
      case T:
        switch(this.orientation) {
          case DEGREE0:
            reachable.add(Direction.LEFT);
            reachable.add(Direction.RIGHT);
            reachable.add(Direction.DOWN);
            this.reachableTiles = reachable;
            break;
          case DEGREE90:
            reachable.add(Direction.DOWN);
            reachable.add(Direction.UP);
            reachable.add(Direction.LEFT);
            this.reachableTiles = reachable;
            break;
          case DEGREE180:
            reachable.add(Direction.LEFT);
            reachable.add(Direction.RIGHT);
            reachable.add(Direction.UP);
            this.reachableTiles = reachable;
            break;
          case DEGREE270:
            reachable.add(Direction.DOWN);
            reachable.add(Direction.UP);
            reachable.add(Direction.RIGHT);
            this.reachableTiles = reachable;
            break;
        }
        break;
      case LINE:
        if (this.orientation == Orientation.DEGREE0 || this.orientation == Orientation.DEGREE180) {
          reachable.add(Direction.UP);
          reachable.add(Direction.DOWN);
        } else {
          reachable.add(Direction.LEFT);
          reachable.add(Direction.RIGHT);
        }
        this.reachableTiles = reachable;
        break;
    }
    return;
  }


  /**
   * Sets the orientation of the Tile.
   * @param o the new orientation
   */
  public void setOrientation(Orientation o) {
    this.orientation = o;
    this.setReachable();
  }

  /**
   * Rotates the tile clockwise 90 degrees
   * @param times the number of times to rotate
   */
  public void rotate90(int times) {
    this.orientation = this.orientation.rotate(times);
    this.setReachable();
  }

/*
  GETTER FUNCTIONS:
 */

  public String getGems() {return this.gem1 + " " + this.gem2;}

  public String getGem1() {return this.gem1.toString();}
  public String getGem2() {return this.gem2.toString();}



  public Shape getShape() {return this.shape;}

  public Orientation getOrientation() {return this.orientation;}

  public List<Direction> getReachable() {return this.reachableTiles;}

  /**
   * Creates a string that communicates the state of the tile.
   * @return a string with the information of the orientation and shape
   */
  public String toString() {
    switch(this.shape) {
      case CORNER:
        switch(this.orientation) {
          case DEGREE0:
            return "└";
          case DEGREE90:
            return "┌";
          case DEGREE180:
            return "┐";
          case DEGREE270:
            return "┘";
        }
        break;
      case CROSS:
        return "┼";
      case T:
        switch(this.orientation) {
          case DEGREE0:
            return "┬";
          case DEGREE90:
            return "┤";
          case DEGREE180:
            return "┴";
          case DEGREE270:
            return "├";
        }
        break;
      case LINE:
        if (this.orientation == Orientation.DEGREE0 || this.orientation == Orientation.DEGREE180) {
          return "│";
        } else {
          return "─";
        }
    }
    return "NOT VALID";
  }

  public Tile makeCopy() {
    return new Tile(this.gem1, this.gem2, this.shape, this.orientation);
  }
}

