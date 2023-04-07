package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a special implementation of the model interface that ensures all acceptable strings
 * only contain given acceptable characters.
 */
public class XModel implements Model {

  private List<String> characterMap;

  /**
   * Private constructor to ensure this class is a singleton, instantiates data as empty arraylist
   */
  private XModel() {
    this.characterMap = new ArrayList<>();
  }

  private static final Model INSTANCE = new XModel();

  /**
   * This class should only be instantiated once, this method returns that single instance.
   * @return the class singleton
   */
  public static Model getInstance() {
    return INSTANCE;
  }

  @Override
  public void addValidSpecialCharStrings(List<String> stringInput, String acceptableChars)
      throws IllegalArgumentException {
    if (stringInput.size() == 0) {
      return;
    }
    List<String> accumulator = new ArrayList<>();
    String currentStr = stringInput.get(0);
    int size = currentStr.length();

    for (int i = 0; i < stringInput.size(); i++) {
      currentStr = stringInput.get(i);
      if(isValidString(stringInput.get(i), acceptableChars) && size == currentStr.length()) {
        accumulator.add(currentStr);
      }
      else {
        throw new IllegalArgumentException("unacceptable input: malformed strings");
      }
    }
    this.characterMap = accumulator;
  }

  /**
   * Checks that the given string is comprised only of acceptable characters.
   * @param str a given row of characters
   * @param acceptableChars a String containing only the acceptable characters
   * @return if the string is valid
   */
  private boolean isValidString(String str, String acceptableChars) {
    for(int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (!acceptableChars.contains(String.valueOf(c))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<String> getRowsOfCharacters() {
    return this.characterMap;
  }
}
