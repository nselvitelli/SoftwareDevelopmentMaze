package Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import Common.*;
import Players.*;
import Referee.*;
import Enumerations.*;

/**
 * Utility functions to be shared by classes across the project.
 */
public class Utils {
  /**
   * availableGems is a static variable associated with the class. It is used for creating a board
   * and ensures that no two tiles on a board have the same pair of gems. Once this runs out a board
   * will no longer be able to maintain the criteria when creating new gems so an error will be
   * thrown.
   */
  private static Queue<List<Gem>> availableGems;

  /**
   * Set the available gems. This will be called when creating a new board to ensure no tile
   * has the same pair of gems. It creates all combinations of gems and picks one when assigning a
   * new tile.
   */
  public static void setAvailableGems() {
    ArrayList<List<Gem>> gemPairs = new ArrayList<List<Gem>>();

    List<Gem> gems = Arrays.asList(Gem.values());
    for(int i=0; i<gems.size(); i++) {
      for(int j=i+1; j<gems.size(); j++) {
        Gem g1 = gems.get(i);
        Gem g2 = gems.get(j);
        gemPairs.add(List.of(g1, g2));
      }
    }
    Collections.shuffle(gemPairs);

    availableGems = new ArrayDeque<>(gemPairs);
  }

  public static List<Gem> getGems() {
    return availableGems.poll();
  }

  public static void sortCoordinates(List<Coordinate> coordinates) {
    Collections.sort(coordinates);
  }

  /**
   * JSON:
   */


  public static void printJSONArrayMoveAction(Action moveMaybe) throws JSONException {
    if (!moveMaybe.isMove()) {
      System.out.println("\"PASS\"");
      return;
    }
    MoveAction move = (MoveAction) moveMaybe;

    JSONArray ret = new JSONArray();
    ret.put(move.getIndex());
    ret.put(move.getDirection());
    ret.put(((4 - move.getNumRotations())%4) * 90);
    JSONObject coordinate = JSONConverter.coordinateToJSON(move.getMoveTo());
    ret.put(coordinate);
    System.out.println(ret.toString());
  }

  public static Player jsonAddOnePlayer(JSONArray plmtJSON, State state, String strategy) throws JSONException {
    JSONObject pJSON = plmtJSON.getJSONObject(0);

    JSONObject homeJSON = pJSON.getJSONObject("home");
    JSONObject currentJSON = pJSON.getJSONObject("current");
    String color = pJSON.getString("color");

    Coordinate home = new Coordinate((int) homeJSON.get("row#"), (int) homeJSON.get("column#"));
    Coordinate current = new Coordinate((int) currentJSON.get("row#"), (int) currentJSON.get("column#"));

    //Create AIPlayer with strategy
    AIPlayer p = null;
    if (strategy.equals("Euclid")) {
      p = new AIPlayer(new EuclidStrategy(), "name", Color.yellow);
    } else if (strategy.equals("Riemann")) {
      p = new AIPlayer(new RiemannStrategy(), "name", Color.yellow);
    }

    state.addPlayer(Color.yellow, home, home, current);
    return p;
  }

  /**
   * Caluclates the euclidean square distance between two coordinates.
   * @param c1 coordinate 1
   * @param c2 coordinate 2
   * @return the distance
   */
  public static int distanceFrom(Coordinate c1, Coordinate c2) {
    int rowDistance = Math.abs(c1.getRow() - c2.getRow());
    int colDistance = Math.abs(c1.getCol() - c2.getCol());
    return rowDistance * rowDistance + colDistance * colDistance;
  }

  public static JSONTokener readInput() {
    Scanner sc = new Scanner(System.in);
    String str = "";
    while(sc.hasNext()) {
      str += sc.next();
    }

    return new JSONTokener(str);
  }

  public static Coordinate getCoordinateJSON(JSONObject coordinateJSON) throws JSONException {
    return new Coordinate(coordinateJSON.getInt("row#"),
            coordinateJSON.getInt("column#"));

  }

  public static Color stringToColor(String colorString) {
    Color c = Color.getColor(colorString);
    if (c != null) {
      return c;
    }
    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    return new Color(r, g, b);

  }

  /**
   * Executes a given task but with a given time limit in milliseconds. If this time limit is exceeded, returns empty.
   *
   * References:
   *    * <a href="https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java">...</a>
   *    * <a href="https://stackoverflow.com/questions/1164301/how-do-i-call-some-blocking-method-with-a-timeout-in-java">...</a>
   *
   * @param task the Callable Task of type T to run with the given timeout
   * @param timeout the time the callable can take to execute before the method is forced to stop
   * @return if the task returned a value before the timeout, an optional containing the resulting value is returned.
   *         if not, an empty optional is returned
   * @param <T> the return type of the callable to run
   */
  public static <T> Optional<T> callWithTimeout(Callable<T> task, long timeout) {
    ExecutorService executor = Executors.newFixedThreadPool(1, r -> {
      Thread t = Executors.defaultThreadFactory().newThread(r);
      t.setDaemon(true);
      return t;
    });
    Future<T> future = executor.submit(task);
    try {
      return Optional.of(future.get(timeout, TimeUnit.MILLISECONDS));
    } catch (TimeoutException | InterruptedException | ExecutionException e) {
      return Optional.empty();
    } finally {
      executor.shutdown();
      future.cancel(true);
    }
  }

  /**
   * Retrieves the given port number from the given string representation.
   * @param portString the string representation of a port
   * @return the port number for clients to connect to.
   */
  public static int getPort(String portString) {
    try {
      return Integer.parseInt(portString);
    }
    catch(NumberFormatException e) {
      System.out.println("Given invalid port");
      System.exit(1);
    }
    return -1; //unreachable
  }
}



