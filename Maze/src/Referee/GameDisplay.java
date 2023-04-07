package Referee;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import Common.*;
import Players.*;

/**
 * Display of the game of Labyrinth
 * Some inspiration from Ian Darwin, http://www.darwinsys.com/
 */
class GameDisplay extends Canvas {
  int width, height;
  State state;

  private static final int GEM_SIZE = 20;
  private static final int AVATAR_SIZE = 30;
  private static final int HOME_SIZE = 30;

  /**
   * Creates a GameDisplay.
   */
  GameDisplay(int w, int h, State s) {
    setSize(width = w, height = h);
    state = s;
  }

  /**
   * Gets the image of a gem from the name
   * @param gemName the gem's name
   * @return the image
   */
  private Image getGem(String gemName) {
    String path = System.getProperty("user.dir");
    String gemPath = path + "/Maze/src/Common/gems/" + gemName + ".png";
    File gemFile = new File(gemPath);
    Image i = new BufferedImage(120, 120, TYPE_INT_RGB);
    try{
      i = ImageIO.read(gemFile);
    } catch (IOException e) {
      System.out.println("Unable to open gem file " + gemName);
    }
    // Scale image
    Image retImage = i.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    return retImage;
  }

  /**
   * Creates the gameboard and spare piece to be displayed
   * @param g the graphics
   */
  public void paint(Graphics g) {
    width = getSize().width;
    height = getSize().height;

    Board board = this.state.getBoard();
    Tile spare = this.state.getSpare();


    int rowHt = height / (this.state.getRows());
    int rowWid = width / (this.state.getCols() + 3);

    Font bigFont = new Font(Font.SANS_SERIF, Font.PLAIN, 80);
    g.setFont(bigFont);

    for(int r=0;r<this.state.getRows();r++) {
      for(int c=0;c<this.state.getCols();c++) {
        Tile t = board.getTileAt(new Coordinate(r, c));
        g.drawRect(c*rowWid, r*rowHt, rowWid, rowHt);
        this.drawPath(g, t.toString(), c*rowWid, (r+1)*rowHt);

        g.setColor(new Color(0, 0, 0, 0));
        String gem1 = t.getGem1().toString();
        Image gemImage1 = getGem(gem1);
        String gem2 = t.getGem2().toString();
        Image gemImage2 = getGem(gem2);
        g.drawImage(gemImage1,
                this.getCornerX(c, rowWid, 1) - (int)(GEM_SIZE*1.5),
                this.getCornerY(r, rowHt, 1),
                this);
        g.drawImage(gemImage2,
                this.getCornerX(c, rowWid, 2),
                this.getCornerY(r, rowHt, 2) - (int)(GEM_SIZE*1.5),
                this);
        g.setColor(Color.BLACK);
      }
    }

    this.drawPlayers(g, rowWid, rowHt);

    g.drawRect(8*rowWid, 3*rowHt, rowWid, rowHt);
    this.drawPath(g, spare.toString(), 8*rowWid, 4*rowHt);
  }

  private void drawPlayers(Graphics g, int rowWid, int rowHt) {
    Map<Coordinate, Integer> numPlayersOnTile = new HashMap<>();

    for (Color player : this.state.getPlayers()) {
      Coordinate location = state.getPlayerLocation(player);

      int offset = 0;
      offset = 10*numPlayersOnTile.getOrDefault(location, 0);

      Color playerColor = player;
      g.setColor(playerColor);
      g.fillOval(location.getCol() * rowWid + offset, location.getRow() * rowHt + offset, AVATAR_SIZE, AVATAR_SIZE);

      Coordinate home = state.getPlayerHome(player);
      g.setColor(playerColor);
      g.drawRect(getCornerX(home.getCol(), rowWid, 3) - HOME_SIZE + offset,
              getCornerY(home.getRow(), rowWid, 3) - HOME_SIZE + offset,
              HOME_SIZE, HOME_SIZE);
      g.setColor(Color.BLACK);

      numPlayersOnTile.put(location, numPlayersOnTile.getOrDefault(location, 0) + 1);
    }
  }

  private void drawPath(Graphics g, String symbol, int x, int y) {
    int xoffset = 0;
    int yoffset = -20;
    switch(symbol) {
      case "┼":
        xoffset += 20;
        break;
      case "─":
        yoffset += 10;
    }
    g.drawString(symbol, x+xoffset, y+yoffset);
  }

  /**
   * Takes in a corner, with 0=top left, 1=top right, 2=bot left, 3=bot right
   */
  private int getCornerX(int c, int rowWid, int corner) {
    if (corner == 0 || corner == 2) {
      return c*rowWid;
    }
    else {
      return (c+1)*rowWid;
    }
  }

  /**
   * Takes in a corner, with 0=top left, 1=top right, 2=bot left, 3=bot right
   */
  private int getCornerY(int r, int rowHt, int corner) {
    if (corner == 0 || corner == 1) {
      return r*rowHt;
    }
    else {
      return (r+1)*rowHt;
    }
  }

}