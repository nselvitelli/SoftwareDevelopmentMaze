package Common;

import java.awt.*;
import java.util.*;
import java.util.List;

import Enumerations.*;


/**
 * A state that holds the functionality of the game Labyrinth.
 */
public class State{

  //Board information:
  protected Board board;
  protected Tile spare;

  //Player information:
  // Players are represented in the game by their unique avatar color
  protected List<Color> players;
  // playerUp is the index of whose turn it is
  protected int playerUp;
  // Map of players to the coordinates that they are currently on
  protected Map<Color, Coordinate> playerLocations;
  // Home locations of players
  private final Map<Color, Coordinate> homeLocations;
  // Goal Locations of players
  private final Map<Color, Coordinate> goalLocations;
  // Used to keep track of any players that have reached their goal location
  private final Map<Color, List<Coordinate>> playerGoalsReached;
  //Potentially holds the winning player
  private List<Color> winners;
  //Round number
  private int round;
  // goals available to retrieve
  private Queue<Coordinate> availableGoals;

  //Move information:
  protected MoveAction lastMove;
  protected MoveAction currentMove;

  /**
   * Creates an instance of a State, creating a new board to be played on.
   */
  public State() {
    this(new Board(), new Tile());
  }

  /**
   * Creates an instance of a State, taking in a declared board and spare tile
   * @param board Gameboard that has already been declared
   * @param spareTile Spare tile to be used
   */
  public State(Board board, Tile spareTile) {
    this(board, spareTile, new ArrayList<>(), 0, new HashMap<>(), new HashMap<>(), new HashMap<>(),
            new HashMap<>(), null, null, 0, new LinkedList<>());
  }

  public State(Board board, Tile spare, List<Color> players, int playerUp,
               Map<Color, Coordinate> playerLocations, Map<Color, Coordinate> homeLocations,
               Map<Color, Coordinate> goalLocations, Map<Color, List<Coordinate>> playerGoalsReached,
               MoveAction lastMove, MoveAction currentMove, int round, Queue<Coordinate> availableGoals) {
    this.board = board;
    this.spare = spare;
    this.players = players;
    this.playerUp = playerUp;
    this.playerLocations = playerLocations;
    this.homeLocations = homeLocations;
    this.goalLocations = goalLocations;
    this.playerGoalsReached = playerGoalsReached;
    this.lastMove = lastMove;
    this.currentMove = currentMove;
    this.winners = new ArrayList<>();
    this.round = round;
    this.availableGoals = availableGoals;
  }

  /*
  Player Oriented Functions:
   */

  /**
   * Adds Players to the game.
   * Throws exception if trying to add invalid player.
   * NOTE: Used for testing and JSON conversion.
   */
  public void addPlayer(Color p, Coordinate goal, Coordinate home, Coordinate current) {
    this.addPlayer(p, goal, home);
    if (this.playerLocations.containsKey(p)) {
      this.playerLocations.replace(p, current);
    }
  }

  /**
   * NOTE: Reverse dispatch
   */
  public void addPlayer(Color p, Coordinate goal, Coordinate home) {
    if (movableIndex(goal.getRow()) || movableIndex(goal.getCol())
            || movableIndex(home.getRow()) || movableIndex(home.getCol())
            || p == null || this.homeLocations.containsValue(home)) {
      throw new IllegalArgumentException(p == null ? "Tried to add null player" : "Tried to add invalid player " + p);
    }
    this.players.add(p);
    this.homeLocations.put(p, home);
    this.goalLocations.put(p, goal);
    this.playerLocations.put(p, home);
    this.playerGoalsReached.put(p, new ArrayList<>());
  }

  /**
   * NOTE: Only used for testing.
   */
  public void addPlayer(Color p) {
    Coordinate home, goal;
    do {
      home = this.generateRandomImmovableCoordinate();
      goal = this.generateRandomImmovableCoordinate();
    } while (homeLocations.containsValue(home) || goal.equals(home));

    this.addPlayer(p, goal, home);
  }

  /**
   * NOTE: Used in the JSONConverter for State JSON representations that do not have a goal property for each player.
   */
  public void addPlayerNoGoal(Color p, Coordinate home, Coordinate current) {
    if (movableIndex(home.getRow()) || movableIndex(home.getCol())
            || p == null || this.lastMove != null || this.homeLocations.containsValue(home)) {
      throw new IllegalArgumentException(p == null ? "Tried to add null player" : "Tried to add invalid player " + p);
    }
    this.players.add(p);
    this.homeLocations.put(p, home);
    this.playerLocations.put(p, current);
    this.playerGoalsReached.put(p, new ArrayList<>());
  }

  /**
   * Sets the last move of the game (integration testing)
   */
  public void setLastMove(MoveAction lastMove) {
    this.lastMove = lastMove;
  }

  /**
   * Kick a player form the game and wipe all their stored data.
   */
  public void kickPlayer(Color p) {
    if (!this.players.contains(p)) {
      throw new IllegalArgumentException("Cannot kick a player that does not exist.");
    }
    if (this.players.indexOf(p) < this.playerUp) {
      this.playerUp--;
    }
    this.players.remove(p);
    this.playerLocations.remove(p);
    this.homeLocations.remove(p);
    this.goalLocations.remove(p);
    this.playerGoalsReached.remove(p);
    this.handleNewRound();
  }

  /**
   * Resets round counter if alal players have had their turn.
   */
  private void handleNewRound() {
    if (this.playerUp >= this.players.size()) {
      this.playerUp = 0;
      this.round++;
    }
  }


  /**
   * Gets the active player whose turn it is.
   * @return the active player ; null when there are no more players
   */
  public Color whoseTurn() {
    if (this.players.isEmpty()) {
      throw new IllegalStateException("No players in game.");
    }
    return this.players.get(playerUp);
  }

  /**
   * Moves onto the next player
   */
  public void nextTurn() {
    this.playerUp++;
    this.handleNewRound();
  }

  /*
  Move Functions:
   */

  /**
   * Performs a move. If an invalid move is attempted, nothing happens and an exception is thrown.
   * @param ma the move action
   *           (index/direction of row/column, number of clockwise rotations, and coordinate to move to)
   * @throws IllegalArgumentException when the move is invalid!
   */
  public void performMove(MoveAction ma) throws IllegalArgumentException{
    this.assertValidMoveAction(ma);
    this.moveActionStay(ma);
    this.moveCurrentPlayer(ma.getMoveTo());
  }

  /**
   * Performs the sliding and inserting of the board. Also sets the currentMove to this move.
   * @param ma the move action
   */
  protected void moveActionStay(MoveAction ma) {
    int i = ma.getIndex();
    Direction d = ma.getDirection();
    int r = ma.getNumRotations();

    this.spare.rotate90(r);
    this.spare = this.board.rearrangeBoard(i, d, this.spare);
    this.handlePlayersMoving(d, i);
    this.currentMove = ma;
  }

  /**
   * Moves the currently active player to the specified coordinates if reachable. If not reachable,
   * it reverts the player's slide and insert.
   * @param c the coordinate of the player's destination
   * NOTE: Should only be called after moveActionStay!
   * @throws IllegalArgumentException when the player can't reach its destination.
   */
  private void moveCurrentPlayer(Coordinate c) throws IllegalArgumentException{
    Color activePlayer = this.players.get(playerUp);
    Coordinate current = this.playerLocations.get(activePlayer);
    List<Coordinate> validCoordinates = this.getValidCoordinates(current);
    if (validCoordinates.contains(c)) {

      this.playerLocations.replace(activePlayer, c);
      this.handlePlayerGoalChecks(activePlayer);
      this.lastMove = this.currentMove;

    } else{
      this.revertMoveAction();
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if the player is on their goal tile or if they have returned home after all goal tiles
   * have been taken.
   * @param activePlayer the current player's color
   */
  private void handlePlayerGoalChecks(Color activePlayer) {
    List<Coordinate> goalsReached = this.playerGoalsReached.get(activePlayer);
    if (this.playerReturnedHome(activePlayer)
            && this.availableGoals.isEmpty() && !goalsReached.isEmpty()) {
      this.calculateWinners();
    }

    if (this.playerOnGoal(activePlayer)) {
      this.playerGoalsReached.get(activePlayer).add(this.getPlayerGoal(activePlayer));
    }
  }

  private boolean playerOnGoal(Color activePlayer) {
    Coordinate goal = this.getPlayerGoal(activePlayer);
    Coordinate current = this.getPlayerLocation(activePlayer);
    return goal != null && goal.equals(current);
  }

  private boolean playerReturnedHome(Color activePlayer) {
    Coordinate home = this.getPlayerHome(activePlayer);
    Coordinate current = this.getPlayerLocation(activePlayer);
    Coordinate goal = this.getPlayerGoal(activePlayer);
    return home != null && home.equals(current) && home.equals(goal) && this.availableGoals.isEmpty() ;
  }


  /**
   * Reverts the current move being attempted
   */
  private void revertMoveAction() {
    if (this.currentMove == null) {
      return;
    }
    MoveAction revertMove = this.currentMove.getInverse();
    int i = revertMove.getIndex();
    Direction d = revertMove.getDirection();
    int r = revertMove.getNumRotations();

    this.spare = this.board.rearrangeBoard(i, d, this.spare);
    this.spare.rotate90(r);
    this.handlePlayersMoving(d, i);
  }

  /**
   * Shifts the players on the row/column that was moved.
   * @param d the direction
   * @param i the index
   */
  private void handlePlayersMoving(Direction d, int i) {

    //Loop through all players and their locations:
    for (Map.Entry<Color,Coordinate> entry : this.playerLocations.entrySet()) {
      Color player = entry.getKey();
      Coordinate currentLocation = entry.getValue();
      Coordinate newLocation;

      //handle slide column
      if ((d == Direction.DOWN || d == Direction.UP) && currentLocation.getCol() == i) {

        if (d == Direction.UP && currentLocation.getRow() == 0) {
          //Player located at top of column (low row value)
          newLocation = new Coordinate(this.getRows()-1, currentLocation.getCol());
        } else if (d == Direction.DOWN && currentLocation.getRow() == this.getRows()-1) {
          //Player located at bottom of column (higher row value)
          newLocation = new Coordinate(0, currentLocation.getCol());
        } else {
          //Player located in middle of column
          newLocation = currentLocation.addDirection(d);
        }
        //Set new player location in gamestate:
        this.playerLocations.replace(player, newLocation);
      }

      //handle slide row
      if ((d == Direction.RIGHT || d == Direction.LEFT) && currentLocation.getRow() == i) {

        if (d == Direction.LEFT && currentLocation.getCol() == 0) {
          //Player located at left of row (low column value)
          newLocation = new Coordinate(currentLocation.getRow(), this.getCols()-1);
        } else if (d == Direction.RIGHT && currentLocation.getCol() == this.getCols()-1) {
          //Player located at right of row (higher column value)
          newLocation = new Coordinate(currentLocation.getRow(), 0);
        } else {
          //Player located in middle of column
          newLocation = currentLocation.addDirection(d);
        }
        //Set new player location in gamestate:
        this.playerLocations.replace(player, newLocation);
      }
    }
  }

  /**
   * Returns a list of reachable coordinates from the given coordinate
   * @param current the current coordinate to assess from
   * @return a list of valid coordinates accessible from current
   */
  protected List<Coordinate> getValidCoordinates(Coordinate current) {
    List<Coordinate> result = this.board.getActionsAtPosition(current);
    result.remove(current);
    return result;
  }

  /*
  General Helper Functions:
   */

  /**
   * Returns true if the index i is movable.
   */
  private boolean movableIndex(int i) {
    return i%2 == 0;
  }

  /**
   * Returns true if the given index i is invalid.
   */
  private boolean invalidIndex(int i) {
    return i < 0 || i >= this.getRows();
  }

  /**
   * Returns true if the move action is fundamentally illegal
   */
  private boolean invalidMoveAction(MoveAction ma) {
    return (ma == null ||
            (this.lastMove != null && ma.equalsIgnoreRotation(this.lastMove.getInverse()))
            || invalidIndex(ma.getIndex()) || !movableIndex(ma.getIndex())
            || ma.getDirection() == null || ma.getNumRotations() < 0
            || ma.getMoveTo() == null
            || this.invalidIndex(ma.getMoveTo().getCol()) && this.invalidIndex(ma.getMoveTo().getRow()));
  }

  /**
   * @throws IllegalArgumentException when the MoveAction is invalid
   */
  private void assertValidMoveAction(MoveAction ma) throws IllegalArgumentException{
    if (this.invalidMoveAction(ma)) {
      throw new IllegalArgumentException();
    }
  }

  private Coordinate generateRandomImmovableCoordinate() {
    Random rand = new Random();
    int homeRow = rand.nextInt(this.getRows() / 2) * 2 + 1;
    int homeCol = rand.nextInt(this.getCols() / 2) * 2 + 1;
    return new Coordinate(homeRow, homeCol);
  }

  /*
  Handle Winners:
   */

  /**
   * Tells if the game has ended and a player has won
   * @return true if a player has won (reached goal then went home)
   */
  public boolean hasWinner() {
    return !this.winners.isEmpty();
  }

  /**
   * If the state has not encountered a game-ending move (a player reaches their goal tile after there
   * are no more available goals), then the winners are calculated once. Then the winners are returned
   * as a list of the winning players' colors. If the winners were already calculated, the winners
   * are not calculated again and the previously calculated winners are returned.
   * @return the list of the winning players' unique colors
   */
  public List<Color> getWinners() {
    if (this.winners.isEmpty()) {
      this.calculateWinners();
    }
    return this.winners;
  }

  /**
   * Calculates the winners of this state. Winners are determined by two criteria:
   * 1. A winner has reached the most number of goals
   * 2. A winner is also the closest to their next goal
   * Note: There can be multiple players that satisfy these conditions. If so, all of these players
   * are considered winners.
   */
  private void calculateWinners() {
    Set<Color> playersWithMostGoals = findPlayersWithMostGoalsReached();
    Set<Color> playersClosestToTheirNextGoal = findPlayersClosestToNextGoal(playersWithMostGoals);
    this.winners = new ArrayList<>(playersClosestToTheirNextGoal);
  }

  /**
   * Finds all players that have reached the most number of goals.
   * @return the colors of the players that reached the most number of goals
   */
  private Set<Color> findPlayersWithMostGoalsReached() {
    int most = 0;
    Set<Color> winners = new HashSet<>();

    // find players with most goals
    for (Color playerColor : this.players) {
      int playerGoalsCollected = this.playerGoalsReached.get(playerColor).size();
      if (playerGoalsCollected == most) {
        winners.add(playerColor);
      }
      else if (playerGoalsCollected > most) {
        winners = new HashSet<>();
        winners.add(playerColor);
        most = playerGoalsCollected;
      }
    }
    return winners;
  }

  /**
   * Finds all players in the given set of player colors that are the closest to their next goal.
   * @param players the colors of the players to check
   * @return the colors of the players that are the closest to their goals
   */
  private Set<Color> findPlayersClosestToNextGoal(Set<Color> players) {
    int distance = Integer.MAX_VALUE;
    Set<Color> closestToGoal = new HashSet<>();

    for(Color playerColor : players) {
      Coordinate location = this.playerLocations.get(playerColor);
      Coordinate goal = this.goalLocations.get(playerColor);
      int playerDistToNextGoal = Utils.distanceFrom(location, goal);

      if(playerDistToNextGoal < distance) {
        distance = playerDistToNextGoal;
        closestToGoal = new HashSet<>();
        closestToGoal.add(playerColor);
      }
      else if(playerDistToNextGoal == distance) {
        closestToGoal.add(playerColor);
      }
    }
    return closestToGoal;
  }


  /*
  Informational states:
   */

  /**
   * Creates a copy of the state.
   * @return a copy of the state
   */
  public State createCopy() {

    List<Color> newPlayers = new ArrayList<>(this.players);
    Map<Color, Coordinate> newHomes = new HashMap<>();
    Map<Color, Coordinate> newGoals = new HashMap<>();
    Map<Color, Coordinate> newLocations = new HashMap<>();
    Map<Color, List<Coordinate>> newReached = new HashMap<>();

    for(Color p : newPlayers) {
      newHomes.put(p, this.homeLocations.get(p));
      newGoals.put(p, this.goalLocations.get(p));
      newLocations.put(p, this.playerLocations.get(p));
      newReached.put(p, new ArrayList<>(this.playerGoalsReached.get(p)));
    }

    Queue<Coordinate> newAvailableGoals = new LinkedList<>(this.availableGoals);

    return new State(this.board.makeCopy(), this.spare.makeCopy(), newPlayers, this.playerUp,
            newLocations, newHomes, newGoals, newReached, this.lastMove, this.currentMove, this.round, newAvailableGoals);
  }

  /**
   * Create a copy of the state for the Player. The list of players needs to be organized where the
   * player with the current turn is first in the list of players.
   * @return a state
   */
  public State createPlayerState() {

    State state = createCopy();

    List<Color> newPlayers = new ArrayList<>();
    for(int i=0; i< state.players.size(); i++) {
      Color toAdd = state.players.get((i + state.playerUp) % state.players.size());
      newPlayers.add(toAdd);
    }
    state.players = newPlayers;

    return state;
  }


  /**
   * Retrieves the next goal from the remaining available goals and mutates this state to have the
   * given player's goal location contain the coordinate to the new position.
   * @param playerColor the color of the player to update
   * @return the new goal location for the player
   */
  public Coordinate updatePlayerGoal(Color playerColor) {
    Coordinate goal = this.homeLocations.get(playerColor);
    if(!this.availableGoals.isEmpty()) {
      goal = this.availableGoals.remove();
    }

    this.goalLocations.put(playerColor, goal);
    return goal;
  }

  public void setAvailableGoals(Queue<Coordinate> availableGoals) {
    this.availableGoals = availableGoals;
  }

  /**
   * Used for creating a PlayerState custom to the player.
   * @param board the board
   * @param spareTile the spare tile
   * @param p the player
   * @param current the player's current location
   */
  private State(Board board, Tile spareTile, Color p, Coordinate current, MoveAction lastMove) {
    this(board, spareTile);
    this.players.add(p);
    this.playerLocations.put(p, current);
    this.lastMove = lastMove;
  }

  /*
  Getters:
   */

  public List<Color> getPlayers() {
    return new ArrayList<>(this.players);
  }
  public Coordinate getPlayerGoal(Color p) {
    return this.goalLocations.get(p);
  }
  public Coordinate getPlayerLocation(Color p) {
    return this.playerLocations.get(p);
  }
  public Coordinate getPlayerHome(Color p) {
    return this.homeLocations.get(p);
  }

  /**
   * Determines if the player has reached their currently assigned goal.
   * @param p the player's unique color
   * @return true if the player is on their previous goal
   */
  public boolean playerReachedGoal(Color p) {
    return this.goalLocations.get(p).equals(this.playerLocations.get(p));
  }

  public Board getBoard() {
    return this.board.makeCopy();
  }
  public Tile getSpare() {
    return this.spare.makeCopy();
  }
  public MoveAction getLastMove() {
    return this.lastMove;
  }
  public int getRound() {
    return this.round;
  }
  public int getRows() {
    return this.board.getRows();
  }
  public int getCols() {
    return this.board.getCols();
  }

}
