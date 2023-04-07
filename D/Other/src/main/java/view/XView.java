package view;

import controller.MouseEventListener;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a special XView that allows for the capability to draw the special acceptable character
 * strings on a grid of tiles.
 */
public class XView implements View {

  private static final View INSTANCE = new XView();
  /**
   * This class should only be instantiated once, this method returns that single instance.
   * @return the class singleton
   */
  public static View getInstance() {
    return INSTANCE;
  }

  /**
   * Private constructor to ensure this class is a singleton.
   */
  private XView() {

  }

  @Override
  public void drawView(List<String> rowsOfCharacters) {

    int rowCount = rowsOfCharacters.size();
    int tilesPerRow = rowCount > 0 ? rowsOfCharacters.get(0).length() : 0;


    JFrame frame = new JFrame("XGui");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set intial window size bounds to have each tile be 100x100 pixels. If this is too large
    // or too small, clamp maximum initial dimension to 800x800 and minimum to 100x100.
    int initialWidth = Math.min(800, Math.max(100, tilesPerRow * 100));
    int initialHeight = Math.min(800, Math.max(100, rowCount * 100));
    frame.setSize(initialWidth, initialHeight);

    frame.addMouseListener(MouseEventListener.getInstance());

    JPanel panel = rowCount > 0 ? new JPanel(new GridLayout(rowCount, tilesPerRow)) : new JPanel();
    panel.setBackground(Color.WHITE);

    for(int row = 0; row < rowsOfCharacters.size(); row++) {
      String rowStr = rowsOfCharacters.get(row);
      for(int col = 0; col < rowStr.length(); col++) {
        char colChar = rowStr.charAt(col);
        JPanel tile = new Tile(colChar);
        tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(tile);
      }
    }

    frame.add(panel);

    frame.setVisible(true);
  }
}
