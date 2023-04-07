package Common;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Enumerations.*;

/**
 * The board of a Labyrinth game. It holds all information to do with the arrangement and
 * manipulation of tiles.
 * (0,0) is the top left with the coordinates being represented as (row, column).
 */
public class Board{

  private Tile[][] gameboard;
  private Map<Coordinate, List<Coordinate>> successors;

  private int rows;
  private int cols;

  /**
   * Creates the board with all its tiles randomly created.
   * @param rows number of rows
   * @param cols number of columns
   */
  public Board(int rows, int cols) {
    this.setupGameboard(rows, cols);
    this.initializeSuccessors();
    this.rows = rows;
    this.cols = cols;
  }

  /**
   * Creates a generic board of 7X7 size.
   */
  public Board() {
    this(7, 7);
  }

  /**
   * Creates a Board object with the gameboard given.
   * @param gameboard the 2D array of Tiles
   */
  public Board(Tile[][] gameboard) {
    this.gameboard = gameboard;
    Utils.setAvailableGems();
    this.initializeSuccessors();
    this.rows = gameboard.length;
    this.cols = gameboard[0].length;
  }


  /**
   * Builds the 2D array of tiles for hte gameboard.
   * @param rows number of rows
   * @param cols number of columns
   */
  private void setupGameboard(int rows, int cols) {
    Utils.setAvailableGems();
    this.gameboard = new Tile[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Tile t = new Tile();
        this.gameboard[i][j] = new Tile();
      }
    }
  }

  /**
   * Initializes the successors for all the tiles in the board
   */
  private void initializeSuccessors() {
    this.successors = new HashMap<>();
    for(int r=0; r<this.gameboard.length; r++) {
      for(int c=0; c<this.gameboard[0].length; c++) {
        this.successors.put(new Coordinate(r, c), new ArrayList<>());
      }
    }
  }

  /**
   * Public method to perform a turns set of actions:
   * Slide column, then insert the spare piece into the game board
   * @param index Index of the row/column to be slid
   * @param d Direction of the row/column to be slid
   * @param t Tile to be inserted into the new location
   * @return
   */
  public Tile rearrangeBoard(int index, Direction d, Tile t) {
    Tile newSpare;
    switch(d) {
      case LEFT:
        newSpare = slideRow(index, false);
        insertSparePiece(t);
        return newSpare;
      case RIGHT:
        newSpare = slideRow(index, true);
        insertSparePiece(t);
        return newSpare;
      case UP:
        newSpare = slideColumn(index, false);
        insertSparePiece(t);
        return newSpare;
      case DOWN:
        newSpare = slideColumn(index, true);
        insertSparePiece(t);
        return newSpare;
      default:
        throw new IllegalArgumentException("invalid input to rearrangeBoard");

    }
  }

  /**
   * Performs the sliding of a row.
   * @param row_number natural number - represents the row to move
   * @param moveRight if true move the row right, if false move the row left
   */
  private Tile slideRow(int row_number, boolean moveRight) {
    Tile removedPiece = null;
    // Check to Make sure the row is a valid row in the board
    assertValidPosition(new Coordinate(row_number, 0));

    if (moveRight) {
      for (int index = gameboard[row_number].length - 1; index >= 1; index--) {
        // If we are in first position, take out tile at index 0 and move tile from left over
        if (index == gameboard.length - 1) {
          removedPiece = this.gameboard[row_number][index];
        }
        this.gameboard[row_number][index] = this.gameboard[row_number][index - 1];
        // If we get to second to last position, move tile from left over, and remove last tile
        if (index == 1) {
          this.gameboard[row_number][index - 1] = null;
        }
      }
    } else {
      for (int index = 0; index <= gameboard[row_number].length - 2; index++) {
        // If we are in first position, take out tile at index 0 and move tile from right over
        if (index == 0) {
          removedPiece = this.gameboard[row_number][index];
        }
        this.gameboard[row_number][index] = this.gameboard[row_number][index + 1];
        // If we get to second to last position, move tile from right over, and remove last tile
        if (index == gameboard.length - 2) {
          this.gameboard[row_number][index + 1] = null;
        }
      }
    }
    return removedPiece;

  }

  /**
   * Performs the sliding of a column.
   * @param column_number natural number - represents the column to move
   * @param moveDown if true move the column up, if false move the column down
   */
  private Tile slideColumn(int column_number, boolean moveDown) {
    Tile removedPiece = null;
    // Check to Make sure the row is a valid row in the board
    assertValidPosition(new Coordinate(0, column_number));

    if (moveDown) {
      for (int index = gameboard.length - 1; index >= 1; index--) {
        // If we are in first position, take out tile at index 0 and move tile from left over
        if (index == gameboard.length - 1) {
          removedPiece = this.gameboard[index][column_number];
        }
        this.gameboard[index][column_number] = this.gameboard[index-1][column_number];
        // If we get to second to last position, move tile from left over, and remove last tile
        if (index == 1) {
          this.gameboard[index-1][column_number] = null;
        }
      }
    } else {
      for (int index = 0; index <= gameboard.length - 2; index++) {
        // If we are in first position, take out tile at index 0 and move tile from right over
        if (index == 0) {
          removedPiece = this.gameboard[index][column_number];
        }
        this.gameboard[index][column_number] = this.gameboard[index+1][column_number];
        // If we get to second to last position, move tile from right over, and remove last tile
        if (index == gameboard.length - 2) {
          this.gameboard[index+1][column_number] = null;
        }
      }
    }
    return removedPiece;
  }

  /**
   * Inserts the given tile into the empty spot on the gameboard
   * @param t The tile to be inserted into the gameboard
   */
  private void insertSparePiece(Tile t) {
    for(int i=0;i<this.gameboard.length;i++) {
      for(int j=0;j<this.gameboard[0].length;j++) {
        if (this.gameboard[i][j] == null) {
          this.gameboard[i][j] = t;
        }
      }
    }
  }

  /**
   * Clears the list of reachable direct neighbors from all the tiles on the gameboard
   */
  private void clearSuccessors() {
    for(int r=0; r<this.gameboard.length; r++) {
      for(int c=0; c<this.gameboard[0].length; c++) {
        this.successors.get(new Coordinate(r, c)).clear();
      }
    }
  }

  /**
   * Iterates through the gameboard and for each tile, generates a list of the
   * direct neighbors that can be reached from the tile. Used for BFS implementation
   */
  private void updateSuccessors() {

    this.clearSuccessors();

    //Declarations:
    Tile t;
    Coordinate coordinate;
    List<Direction> directions;
    Coordinate otherCoordinate;
    List<Direction> otherDirections;

    //Initialization:
    List<Direction> downAndRight = new ArrayList<Direction>();
    downAndRight.add(Direction.DOWN);
    downAndRight.add(Direction.RIGHT);

    //Loop through all tiles:
    for(int r=0; r<this.gameboard.length; r++) {
      for(int c=0; c<this.gameboard[0].length; c++) {

        //Get info about this tile:
        t = this.gameboard[r][c];
        coordinate = new Coordinate(r, c);
        directions = t.getReachable();

        //Check both down and right of this tile for successors:
        for(Direction d : downAndRight) {

          otherCoordinate = coordinate.addDirection(d);
          if (directions.contains(d) && this.checkValidPosition(otherCoordinate)) {

            int row = otherCoordinate.getRow();
            int col = otherCoordinate.getCol();

            otherDirections = this.gameboard[row][col].getReachable();

            if (otherDirections.contains(d.oppositeDirection())) {

              this.successors.get(coordinate).add(otherCoordinate);
              this.successors.get(otherCoordinate).add(coordinate);
            }
          }
        }

      }
    }
  }


  /**
   * Gets all reachable coordinates from an input coordinate. Utilizes a BFS algorithm
   * @param c the coordinate
   * @return a list of coordinates that the player can reach from the input position
   */
  public List<Coordinate> getActionsAtPosition(Coordinate c) {
    if (!this.checkValidPosition(c)) {
      return new ArrayList<>();
    }

    //Update successors
    this.updateSuccessors();

    //Run Breadth First Search
    Queue<Coordinate> frontier = new ArrayDeque();
    frontier.add(c);
    List<Coordinate> explored = new ArrayList<>();

    Coordinate current;

    while(!frontier.isEmpty()) {
      current = frontier.poll();
      explored.add(current);
      for(Coordinate other : this.successors.get(current)) {
        if (!explored.contains(other) && !frontier.contains(other)) {
          frontier.add(other);
        }
      }
    }

    Utils.sortCoordinates(explored);

    return explored;
  }





  /**
   * Check if the position is valid
   * @param c the coordinate to check
   * @return true if the position is valid
   */
  private boolean checkValidPosition(Coordinate c) {
    return !(c.getRow() >= this.gameboard.length || 0 == this.gameboard.length ||
            c.getCol() >= this.gameboard[0].length || c.getRow() < 0 || c.getCol() < 0);
  }

  /**
   * Assert that the position is valid. Throw an illegal argument exception if not.
   * @param c the coordinate
   */
  private void assertValidPosition(Coordinate c) {
    if (!this.checkValidPosition(c)) {
      throw new IllegalArgumentException("Invalid board position");
    }
  }

  /**
   * Creates a representation of some of the information of the board. Notably the position of the
   * tiles and the shape/orientation of each tile.
   * @return a String representing information of the tiles in the gameboard
   */
  public String toString() {
    String result = "";
    for(int i=0;i<this.gameboard.length;i++) {
      for(int j=0;j<this.gameboard[0].length;j++) {
        if (this.gameboard[i][j] == null) {
          result += "null ";
        } else{
          result += this.gameboard[i][j].toString();
        }
      }
      result += " \n";
    }
    return result;
  }

  /**
   * Makes a deep copy of the board.
   * @return a new board identical to this
   */
  public Board makeCopy() {
    Tile[][] gameboardCopy = new Tile[rows][cols];
    for(int r=0; r<this.gameboard.length; r++) {
      for(int c=0; c<this.gameboard[0].length; c++) {
        gameboardCopy[r][c] = this.gameboard[r][c].makeCopy();
      }
    }
    return new Board(gameboardCopy);
  }

  public Tile getTileAt(Coordinate c) {
    return this.gameboard[c.getRow()][c.getCol()].makeCopy();
  }

  public int getRows() {
    return this.rows;
  }

  public int getCols() {
    return this.cols;
  }

}
