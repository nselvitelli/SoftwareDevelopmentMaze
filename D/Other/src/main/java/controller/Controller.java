package controller;

import java.util.List;

/**
 * Simple interface to control project. Every controller should be able to recieve strings from
 * std in and be able to exit gracefully.
 */
public interface Controller {

  /**
   * Retrieves the JSON Strings.
   * @return a list of the given Strings
   * @throws IllegalArgumentException if the input is malformed
   */
  List<String> receiveStrings() throws IllegalArgumentException;

  /**
   * Exits the program with the given message.
   * @param message printed on STDOUT before exiting
   * @param exitCode the code to use while exiting
   */
  void exit(String message, int exitCode);

}
