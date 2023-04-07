package Referee;
import javax.swing.*;
import Common.*;
import Players.*;
import Enumerations.*;
/**
 * Display of the game's state of Labyrinth
 */
public class GamePanel extends JPanel {

  public GamePanel() {}

  /**
   * Updates the GamePanel with the given state of the game.
   * @param state the new state
   */
  public void setState(State state) {
    int w = 800;
    int h = 800;

    GameDisplay gameboard = new GameDisplay(w, h, state);
    add(gameboard);
  }

}