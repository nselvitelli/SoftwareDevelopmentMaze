package Referee;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;

import Common.*;

/**
 * Observer watches a game of Labyrinth.
 */
public class Observer extends JFrame{

  //The states up next
  public Queue<State> states;
  //The state currently up
  public State activeState;

  //The display panel of the game
  private GamePanel game;
  //The main display panel
  private JPanel mainPanel;

  //Game over handlers:
  private boolean gameOver;
  private List<Color> winners;
  private List<Color> losers;


  /**
   * Initializes an observer for the functionality of watching a game play out.
   */
  public Observer() {
    super();
    this.states = new ArrayDeque<>();
    this.gameOver = false;

    //Main Panel
    this.mainPanel = new JPanel();
    mainPanel.setBackground(Color.black);
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    //Menu Panel
    mainPanel.add(this.createMenuPanel());

    //Game:
    this.game = new GamePanel();
    mainPanel.add(this.game);

    this.add(mainPanel);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Creates a menu that has "Save" and "Next" buttons that can be clicked.
   * These buttons have a listener listening to them.
   * When it hears "Save" is clicked, it calls the method save().
   * When it hears "Next" is clicked, it calls the method next().
   * @return a JPanel of the menu
   */
  private JPanel createMenuPanel() {
    ListenObserver listener = new ListenObserver(this);

    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    JMenuBar menuBar = new JMenuBar();

    JMenuItem save = new JMenuItem("Save");
    save.setActionCommand("save");
    save.addActionListener(listener);

    JMenuItem next = new JMenuItem("Next");
    next.setActionCommand("next");
    next.addActionListener(listener);

    menuBar.add(next);
    menuBar.add(save);
    menuPanel.add(menuBar);
    return menuPanel;
  }

  /**
   * Saves a JSON representation of the current state using the file finder.
   */
  public void save() {
    try{
      JSONObject jsonState = JSONConverter.stateToJSON(this.activeState);
      Optional<File> file = selectFile();
      if (file.isPresent()) {
        try {
          saveToFile(jsonState, file.get());
        } catch (IOException e) {
          System.out.println("unable to write to file");
        }
      }
    } catch (JSONException e) {
      //this.failureToSave();
    }
  }

  /**
   * Selects the file to save the JSON state to
   * @return the file to save to, or Optional.empty() if no file is selected
   * @throws IllegalArgumentException when an invalid file is selected
   */
  private Optional<File> selectFile() throws IllegalArgumentException {
    JFrame frame = new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    int fileSelect = fileChooser.showSaveDialog(frame);
    if (fileSelect == JFileChooser.APPROVE_OPTION) {
      return Optional.of(fileChooser.getSelectedFile());
    } else if (fileSelect == JFileChooser.CANCEL_OPTION) {
      return Optional.empty();
    } else {
      throw new IllegalArgumentException("Invalid File Selected");
    }
  }

  /**
   * Saves the JSON representation of state to the file.
   * @param jo the JSON state
   * @param file the desired file
   * @throws IOException when it fails to save it.
   */
  private void saveToFile(JSONObject jo, File file) throws IOException {

    // Check to make sure the file is actually a json file
    if (!file.getName().endsWith(".json")) {
      file = new File(file.getParentFile(), file.getName() + ".json");
    }
    String path = file.getAbsolutePath();
    FileOutputStream outputStream = new FileOutputStream(path);
    byte[] strToBytes = jo.toString().getBytes();
    outputStream.write(strToBytes);
    outputStream.close();

  }

  /**
   * Moves onto the next available state of the game.
   */
  public void next() {
    if (!states.isEmpty()) {
      this.activeState = states.poll();
      this.render();
    } else if (gameOver) {
      this.renderEndGame();
    }
  }

  /**
   * "End Game" pops up on the screen denoting an end of the game.
   */
  private void renderEndGame() {
    JLabel gameOverText = new JLabel();
    gameOverText.setText("GAME OVER");
    gameOverText.setSize(300, 300);
    gameOverText.setFont(new Font(Font.SANS_SERIF, 14, 50));
    PopupFactory pf = new PopupFactory();
    pf.getPopup(this.mainPanel, gameOverText, 50, 50).show();
  }

  /**
   * Handles the rendering of the game display.
   */
  private void render() {
    if (this.activeState != null) {
      this.game.removeAll();
      this.game.revalidate();
      this.game.repaint();
      this.game.setState(this.activeState);
      this.pack();
    }
  }

  /*
  Referee Use:
   */

  /**
   * Receives a state and adds it to the queue of states
   * @param s the state
   */
  public void receiveState(State s) {
    this.states.add(s);
  }

  /**
   * Tells the Observer that the game has finished and sends the respective winners and losers.
   * @param winners the winners of the game
   * @param losers the losers f the game
   */
  public void gameOver(List<Color> winners, List<Color> losers) {
    this.gameOver = true;
    this.winners = winners;
    this.losers = losers;
  }

  /*
  Getters:
   */

  public State getCurrentState() {
    if (this.activeState == null) {
      return null;
    } else{
      return this.activeState.createCopy();
    }
  }

  public boolean getGameOver() {
    return this.gameOver;
  }


}