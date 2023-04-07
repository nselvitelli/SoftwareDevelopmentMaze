import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Simple class used to accumulate acceptable characters when given well-formed and valid JSON values
 * via the standard input. A JSON value is well-formed if each element of the sequence satisfies the
 * JSON grammar. It is also valid if it has only "vertical" and "horizontal" keys and the valid
 * values. It prints the JSON array of special characters when the stream is exhausted.
 * when no more output is given (EOF).
 */
public class XJson {
  private static final HashMap<String, String> MAP = new HashMap<>();

  static {
    MAP.put("UP_LEFT", "┘");
    MAP.put("UP_RIGHT", "└");
    MAP.put("DOWN_LEFT", "┐");
    MAP.put("DOWN_RIGHT", "┌");
  }

  /**
   * Default method at runtime to start running the program. Exits with an exit code of 1 if invalid
   * input is given via standard input.
   * @param args Command Line Input
   */
  public static void main(String[] args) {
    // Get the STDIN as a BufferedReader
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    // Accumulator: Accumulates the corresponding special characters for each JSON value as a list
    List<String> accumulator = new ArrayList<>();
    try {
      // Accumulator: builds each JSON object input String
      StringBuilder objectJSONStringBuilder = new StringBuilder();
      boolean buildingJSONObject = false;
      int value = 0;
      // Loop: reads every char from the BufferedReader, terminates when no more input is available
      while ((value = input.read()) != -1) {
        char character = (char)value;

        // if the JSON object is not being built currently
        if (!buildingJSONObject) {
          // if the character is an '{', start building a JSON object
          if (character == '{') {
            buildingJSONObject = true;
            objectJSONStringBuilder.append(character);
          }
          // otherwise, ensure the character is only whitespace
          else {
            String toStr = character + "";
            if (toStr.trim().length() > 0) {
              error("unacceptable input: char between objects");
            }
          }
        }
        // otherwise, the JSON object is currently being built
        else {
          // add the character to the StringBuilder
          objectJSONStringBuilder.append(String.valueOf(character).trim());
          // if the character is a '}', the JSON Object is complete
          if (character == '}') {
            // create the JSON object using the accumulated String
            JSONObject obj = new JSONObject(objectJSONStringBuilder.toString());
            // obtain the values of the vertical and horizontal fields
            String vertical = obj.getString("vertical");
            String horizontal = obj.getString("horizontal");
            // retrieve the relavent unicode character based on the values and add to the
            // output accumulator
            accumulator.add(convertJSONtoChar(vertical, horizontal));
            // reset the StringBuilder to an empty String and stop building a JSON object
            objectJSONStringBuilder = new StringBuilder();
            buildingJSONObject = false;
          }
        }
      }

      if(objectJSONStringBuilder.length() > 0) {
        error("unacceptable input: did not complete last JSON Object before terminating file");
      }

    } catch (IOException | RuntimeException e) {
      error("unacceptable input");
    }

    System.out.println(new JSONArray(accumulator));
  }

  private static void error(String msg) {
    System.out.println(msg);
    System.exit(1);
  }

  /**
   * Converts the JSON values for "vertical" and "horizontal" keys to the corresponding special char
   * string. If the combined key is not valid, the program will error out and exit.
   * @param vert value for vertical key
   * @param hor value for horizontal key
   * @return the corresponding special char string
   */
  private static String convertJSONtoChar(String vert, String hor) {
    String key = vert + "_" + hor;
    if(MAP.containsKey(key)) {
      return MAP.get(key);
    }
    error("unacceptable input: given a bad horizontal or vertical value");
    return null;
  }
}
