package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import model.Model;
import model.XModel;
import view.View;
import view.XView;

/**
 * Represents a simple controller that serves as the main execution point for this project.
 */
public class Xgui implements Controller {

  private static final String ACCEPTABLE_CHARS = "┘┐└┌";
  private static Model model = XModel.getInstance();
  private static View view = XView.getInstance();


  private static final Xgui INSTANCE = new Xgui();

  /**
   * This class should only be instantiated once, this method returns that single instance.
   * @return the class singleton
   */
  public static Controller getInstance() {
    return INSTANCE;
  }

  /**
   * Declared as private to ensure that this class is singleton.
   */
  private Xgui() {

  }

  /**
   * Main method to run project
   * @param args Input from console arguments
   */
  public static void main(String[] args) {

    try {


      List<String> input = INSTANCE.receiveStrings();

      model.addValidSpecialCharStrings(input, ACCEPTABLE_CHARS);

      view.drawView(model.getRowsOfCharacters());
    }
    catch (IllegalArgumentException e) {
      INSTANCE.exit(e.getMessage(), 1);
    }
  }



   @Override
   public List<String> receiveStrings() throws IllegalArgumentException {
    // Get the STDIN as a BufferedReader
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    List<String> accumulator = new ArrayList<>();

    try {
      StringBuilder jsonString = new StringBuilder();
      boolean isBuildingJSONString = false;
      int value = 0;
      // Loop: reads every char from the BufferedReader, terminates when no more input is available
      while ((value = input.read()) != -1) {
        char character = (char)value;

        // if the JSON String is not being built currently
        if (!isBuildingJSONString) {
          // if the character is an '{', start building a JSON object
          if (character == '\"') {
            isBuildingJSONString = true;
          }
          // otherwise, ensure the character is only whitespace
          else {
            String toStr = character + "";
            if (toStr.trim().length() > 0) {
              throw new IllegalArgumentException("unacceptable input: char between objects");
            }
          }
        }
        // otherwise, the JSON String is currently being built
        else {
          // if the character is a '}', the JSON Object is complete
          if (character == '\"') {

            if(jsonString.length() == 0) {
              throw new IllegalArgumentException("unacceptable input: string does not have any content");
            }
            // output accumulator
            accumulator.add(jsonString.toString());
            // reset the StringBuilder to an empty String and stop building a JSON object
            jsonString = new StringBuilder();
            isBuildingJSONString = false;
          }
          else {
            // add the character to the StringBuilder
            jsonString.append(character);
          }
        }
      }

      // still building a string after the end of the input
      if(isBuildingJSONString) {
        throw new IllegalArgumentException("unacceptable input: incomplete string");
      }

    } catch (IOException | RuntimeException e) {
      throw new IllegalArgumentException("unacceptable input: could not interpret input");
    }
    return accumulator;
  }

  @Override
  public void exit(String message, int exitCode) {
    System.out.println(message);
    System.exit(exitCode);
  }

}
