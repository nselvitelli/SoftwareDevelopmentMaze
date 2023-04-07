package Referee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Common.*;
import Players.*;
import Enumerations.*;
/**
 * An action listener to listen to the observer.
 */
public class ListenObserver implements ActionListener {

  private Observer observer;

  /**
   * Listens to an observer
   * @param o the observer
   */
  public ListenObserver(Observer o) {
    this.observer = o;
  }

  /**
   * Assesses if the event was a "Save" or "Next" and calls the appropriate method on the Observer.
   * @param e the event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "save":
        this.observer.save();
        return;
      case "next":
        this.observer.next();
        return;
      default:
        throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
    }
  }
}
