package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

/**
 * Represents a special JPanel that draws the stored acceptable
 * character with a border at the edge of the JPanel.
 */
public class Tile extends JPanel {

  private final char character;

  /**
   * Public constructor for Tile that takes in a special character that will be used to draw.
   * @param character character used to draw tile
   */
  public Tile(char character) {
    super();
    this.character = character;
  }

  @Override
  public void paint(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    int strokeWeight = getWidth()/10;
    Graphics2D g2 = (Graphics2D)g;

    setBackground(Color.WHITE);


    // draw the shapes ┘┐└┌
    g2.setColor(Color.RED);


    // UP
    if(this.character == '┘' || this.character == '└') {
      g2.fillRect(width/2 - strokeWeight/2, 0, strokeWeight, height/2 + strokeWeight/2);
    }

    //DOWN
    if(this.character == '┐' || this.character == '┌') {
      g2.fillRect(width/2 - strokeWeight/2, height/2 - strokeWeight/2, strokeWeight, height/2 + strokeWeight/2);
    }

    //LEFT
    if(this.character == '┐' || this.character == '┘') {
      g2.fillRect(0, height/2 - strokeWeight/2, width/2 + strokeWeight/2, strokeWeight);
    }

    //RIGHT
    if(this.character == '└' || this.character == '┌') {
      g2.fillRect(width/2 - strokeWeight/2, height/2 - strokeWeight/2, width, strokeWeight);
    }

    // tile outline
    float thickness = 4;
    Stroke oldStroke = g2.getStroke();
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(thickness));
    g2.drawRect(0, 0, width, height);
    g2.setStroke(oldStroke);
  }

}
