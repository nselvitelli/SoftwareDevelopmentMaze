package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.json.JSONArray;

/**
 * Represents a listener for mouse events caused by the view.
 */
public class MouseEventListener implements MouseListener {

  private static final MouseEventListener INSTANCE = new MouseEventListener();

  public static MouseEventListener getInstance() {
    return INSTANCE;
  }

  private MouseEventListener() {
    super();
  }


  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    // When mouse is pressed, get the mouse location and exit with the JSON array as the exit message
    int x = e.getX();
    int y = e.getY();
    int[] coords = {x, y};
    Xgui.getInstance().exit(new JSONArray(coords).toString(), 0);
  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
