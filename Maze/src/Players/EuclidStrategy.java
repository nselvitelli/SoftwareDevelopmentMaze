package Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Common.*;

/**
 * EuclidStrategy is a strategy that prioritizes the goal, but when it can't be reached, prioritizes
 * coordinates closest to the goal using euclidean distance (breaks ties by row-column order).
 */
public class EuclidStrategy extends Strategy{

  /**
   * Construstor creates a EuclidStrategy object.
   */
  public EuclidStrategy() {}

  @Override
  protected List<Coordinate> createRankings(Coordinate destination) {
    Map<Integer, Coordinate> rankToCoordinate = this.mapRankings(destination);
    return this.createGoalOrderFromRankings(rankToCoordinate);
  }

  /**
   * Creates the goal order list from the ranking map.
   * @param rankToCoordinate the map of ranks to coordinates
   * @return the goal order list
   */
  private List<Coordinate> createGoalOrderFromRankings(Map<Integer, Coordinate> rankToCoordinate) {
    List<Coordinate> goalOrder = new ArrayList<>();
    while(!rankToCoordinate.isEmpty()) {
      int key = Collections.min(rankToCoordinate.keySet());
      goalOrder.add(rankToCoordinate.get(key));
      rankToCoordinate.remove(key);
    }
    return goalOrder;
  }

  /**
   * Creates a map where keys are the rank of a coordinate and the values are the coordinates.
   * The key is a number of the form abc where
   * a = euclidean distance
   * b = row
   * c = column
   * This allows us to use a single number to rank the row. A smaller number means a better rank
   * @param goal the goal coordinate
   * @return the mapping
   */
  private Map<Integer, Coordinate> mapRankings(Coordinate goal) {
    Map<Integer, Coordinate> rankToCoordinate = new HashMap<>();
    for(int r = 0; r < this.playerState.getRows(); r++) {
      for(int c = 0; c < this.playerState.getCols(); c++) {
        Coordinate pos = new Coordinate(r, c);
        int distance = Utils.distanceFrom(goal, pos);
        int coordinateHash = (distance*100) + (r*10) + c;
        rankToCoordinate.put(coordinateHash, pos);
      }
    }
    return rankToCoordinate;
  }


}
