package Players;
import java.util.ArrayList;
import java.util.List;
import Common.*;
import Referee.*;
import Enumerations.*;

/**
 * RiemannStrategy is a strategy that prioritizes the goal, but when it can't be reached, prioritizes
 * coordinates in row-column lexicographical order.
 */
public class RiemannStrategy extends Strategy{

  /**
   * Constructor creates a RiemannStrategy object.
   */
  public RiemannStrategy() {}

  @Override
  protected List<Coordinate> createRankings(Coordinate destination) {

    List<Coordinate> goalOrder = new ArrayList<>();
    goalOrder.add(destination);
    for(int r=0; r< playerState.getRows(); r++) {
      for(int c=0; c< playerState.getCols(); c++) {
        Coordinate pos = new Coordinate(r, c);
        if (pos != destination) {
          goalOrder.add(pos);
        }
      }
    }
    return goalOrder;
  }


}
