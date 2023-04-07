package Integration;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import Common.*;

public class BoardIntegration {

  private static void createJSON(List<Coordinate> reachable) {
    JSONArray ret = new JSONArray();
    for (Coordinate c : reachable) {
      JSONObject temp = new JSONObject();
      try{
        temp.put("column#", c.getCol());
        temp.put("row#", c.getRow());
      } catch (Exception e) {}
      ret.put(temp);
    }
    System.out.println(ret);
  }
  private static void parseInput() {
    Scanner sc = new Scanner(System.in);
    String str = "";
    while(sc.hasNextLine()) {
      str += sc.nextLine();
    }

    try {
      JSONTokener jt = new JSONTokener(str);
      JSONObject jo = (JSONObject) jt.nextValue();
      JSONObject jc = (JSONObject) jt.nextValue();


      // Create the board using the input connectors;
      Board b = JSONConverter.boardFromJSON((jo));

      // Create coordinate using input row/col information
      Coordinate position = Utils.getCoordinateJSON(jc);
      List<Coordinate> reachable = b.getActionsAtPosition(position);
      createJSON(reachable);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }
  public static void main(String args[]) {
    parseInput();
  }
}

