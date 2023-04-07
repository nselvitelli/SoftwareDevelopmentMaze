package Common;

import org.junit.Before;
import org.junit.Test;

import java.beans.Transient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import Common.*;
import Players.*;
import Enumerations.*;
import Referee.*;

public class BoardTest {

  public Board mockBoard;
  public Tile[][] mockGameboard;

  public Board mockBoard2;
  public Tile[][] mockGameboard2;


  @Before
  public void setup() {
    this.mockGameboard = new Tile[3][3];
    for(int i=0;i<3;i++) {
      for(int j=0;j<3;j++) {
        this.mockGameboard[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.T, Orientation.DEGREE0);
      }
    }
    this.mockGameboard[1][1] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE180);
    this.mockBoard = new Board(this.mockGameboard);
  }


  /**
   * Test creating random tiles:
   */
  @Test
  public void testRandomTileCreation() {
    Utils.setAvailableGems();
    ArrayList<Tile> tileList = new ArrayList<>();

    while(true) {
      try{
        tileList.add(new Tile());
      } catch(Exception e) {
        break;
      }
    }

    assertFalse(tileList.isEmpty());

  }

  /**
   * Test orientation rotation
   * Note: DEPRECATED in this version but future functionality might be useful.
   */
  @Test
  public void testRotateOrientation() {
    Orientation o = Orientation.DEGREE90;
    assertEquals(o.getDegrees(), 90);
    o = o.rotate(5);
    assertEquals(o.getDegrees(), 180);
  }



  /**
   * Tests to verify that slideRow is working correctly:
   */
  @Test
  public void testSlideRow0Right() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(0, Direction.RIGHT, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┼┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");
  }

  @Test
  public void testSlideRow1Left() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(1, Direction.LEFT, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┼┬┼ \n" +
            "┬┬┬ \n");
  }

  @Test
  public void testSlideRow1Right() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(0, Direction.RIGHT, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┼┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSlideRowOutOfBoundsPositive() throws IllegalArgumentException{
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(9, Direction.RIGHT, new Tile('┼', Gem.zircon, Gem.alexandrite));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSlideRowOutOfBoundsNegative() throws IllegalArgumentException{
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(-1, Direction.RIGHT, new Tile('┼', Gem.zircon, Gem.alexandrite));
  }


  /**
   * Tests to verify slideColumn is working correctly:
   */
  @Test
  public void testSlideColumn0Down() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(0, Direction.DOWN, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┼┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");
  }

  @Test
  public void testSlideColumn1Up() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(0, Direction.UP, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┼┬┬ \n");
  }

  @Test
  public void testSlideColumn1Down() {
    setup();
    assertEquals(this.mockBoard.toString(), "┬┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");

    this.mockBoard.rearrangeBoard(0, Direction.DOWN, new Tile('┼', Gem.zircon, Gem.alexandrite));
    assertEquals(this.mockBoard.toString(), "┼┬┬ \n" +
            "┬┼┬ \n" +
            "┬┬┬ \n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSlideColumnOutOfBoundsPositive() throws IllegalArgumentException{
    setup();

    this.mockBoard.rearrangeBoard(8, Direction.UP, new Tile('┼', Gem.zircon, Gem.alexandrite));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSlideColumnOutOfBoundsNegative() throws IllegalArgumentException{
    setup();

    this.mockBoard.rearrangeBoard(-1, Direction.DOWN, new Tile('┼', Gem.zircon, Gem.alexandrite));
  }



  /**
   * Tests for getActionsAtPositions
   */
  @Test
  public void testGetActions() {
    Tile[][] tiles = new Tile[3][3];

    tiles[0][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE90);
    tiles[0][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
    tiles[0][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
    tiles[1][0] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
    tiles[1][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
    tiles[1][2] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
    tiles[2][0] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);
    tiles[2][1] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE270);
    tiles[2][2] = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE0);

    Board board = new Board(tiles);

    List<Coordinate> actionsFromGame = board.getActionsAtPosition(new Coordinate(0,0));
    assertEquals(actionsFromGame.size(), 6);
    actionsFromGame = board.getActionsAtPosition(new Coordinate(1,1));
    assertEquals(actionsFromGame.size(), 1);
    actionsFromGame = board.getActionsAtPosition(new Coordinate(2,2));
    assertEquals(actionsFromGame.size(), 2);
  }

  /**
   * Tests for set/getReachable for a Tile
   */
  @Test
  public void testGetReachable() {
    Tile t = new Tile(Gem.zircon, Gem.zircon, Shape.T, Orientation.DEGREE0);
    List<Direction> reachable = t.getReachable();

    assertTrue(reachable.contains(Direction.DOWN));
    assertTrue(reachable.contains(Direction.LEFT));
    assertTrue(reachable.contains(Direction.RIGHT));

    // Test with rotation
    t.setOrientation(Orientation.DEGREE180);
    reachable = t.getReachable();
    assertTrue(reachable.contains(Direction.LEFT));
    assertTrue(reachable.contains(Direction.UP));
    assertTrue(reachable.contains(Direction.RIGHT));

    Tile t1 = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE0);
    reachable = t1.getReachable();
    assertTrue(reachable.contains(Direction.DOWN));
    assertTrue(reachable.contains(Direction.UP));

    t1.setOrientation(Orientation.DEGREE90);
    reachable = t1.getReachable();
    assertTrue(reachable.contains(Direction.LEFT));
    assertTrue(reachable.contains(Direction.RIGHT));

    Tile t2 = new Tile(Gem.zircon, Gem.zircon, Shape.CORNER, Orientation.DEGREE180);
    reachable = t2.getReachable();
    assertTrue(reachable.contains(Direction.DOWN));
    assertTrue(reachable.contains(Direction.LEFT));

    t2.setOrientation(Orientation.DEGREE270);
    reachable = t2.getReachable();
    assertTrue(reachable.contains(Direction.LEFT));
    assertTrue(reachable.contains(Direction.UP));

    Tile t3 = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
    reachable = t3.getReachable();
    assertTrue(reachable.contains(Direction.DOWN));
    assertTrue(reachable.contains(Direction.UP));
    assertTrue(reachable.contains(Direction.LEFT));
    assertTrue(reachable.contains(Direction.RIGHT));

  }



}
