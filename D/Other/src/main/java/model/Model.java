package model;

import java.util.List;

/**
 * Represents a simple model structure. Every model should be able to store the special character
 * strings, ensuring their validity and return the stored list of characters
 */
public interface Model {

  /**
   * Ensures each String within the given list have the same length and only contain the acceptable
   * characters. If the input is valid, the data is stored.
   * @param stringInput the list of Strings that contain only the acceptable characters
   * @param acceptableChars a String containing only acceptable characters
   * @throws IllegalArgumentException if any characters contained within any given String are not
   * one of the accepted types or all Strings within the list are not of equal length
   */
  void addValidSpecialCharStrings(List<String> stringInput, String acceptableChars) throws IllegalArgumentException;

  /**
   * Retrieves the stored list of Strings containing acceptable characters.
   * @return the list of Strings
   */
  List<String> getRowsOfCharacters();
}
