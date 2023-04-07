package Other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple class used to accumulate acceptable characters given as input via the standard input and
 * print the accumulated string when no more output is given (EOF).
 */
public class XCollect {
  private static final String[] CHAR_VALUES = new String[] { "\"┐\"" ,"\"└\"" ,"\"┌\"" , "\"┘\"" };
  private static final Set<String> CHAR_SET = new HashSet<>(Arrays.asList(CHAR_VALUES));

  /**
   * Default method at runtime to start running the program. Exits with an exit code of 1 if invalid
   * input is given via standard input.
   * @param args Command Line Input
   */
  public static void main(String[] args) {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    // Accumulator: Accumulates the resulting string from combining string inputs that are read from
    // standard input
    StringBuilder accumulator = new StringBuilder();
    try {
      String line;
      while ((line = input.readLine()) != null) {
        accumulator.append(XCollect.validateString(line));
      }
    } catch (IOException | RuntimeException e) {
      unacceptable();
    }

    if (accumulator.length() == 0) {
      unacceptable();
    }
    System.out.println("\"" + accumulator + "\"");
  }

  /**
   * Prints out that the input was unacceptable and exits the program with exit code 1.
   */
  private static void unacceptable() {
    System.out.println("unacceptable input");
    System.exit(1);
  }

  /**
   * Isolates the special unicode character from the given string if te string is one of the Strings
   * stored in the CHAR_VALUES String array. If the String is not contained within the list of
   * acceptable strings, throw a RuntimeException
   * @param inputStr input a single line of input
   * @throws RuntimeException if the input string is invalid based on whether it is contained in the char set
   * @return the middle special character from the input string if valid
   */
  private static char validateString(String inputStr) throws RuntimeException {
    if (CHAR_SET.contains(inputStr)) {
      return inputStr.charAt(1);
    } else {
      throw new RuntimeException("Invalid input");
    }
  }
}
