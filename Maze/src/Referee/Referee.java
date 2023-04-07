package Referee;
import java.awt.*;
import java.util.*;
import java.util.List;

import Common.*;
import Players.*;
import Enumerations.*;
/**
 * Hosts a game of Labyrinth.
 * NOTE: the referee will kick a player if they pass an invalid move according to the rules of the
 * game. Once the remote communication is added, players will be kicked for taking too long to
 * provide a move or if they provide data that can not be translated into a pass ("PASS") or MoveAction
 */
public class Referee {

  //The state of the game that the referee controls
  private State state;
  //Map of players to colors
  private final Map<Color, Player> colorToPlayer;
  //The kicked players of the game
  private final List<Color> kickedPlayers;
  //the players who have passed in the current round
  private Set<Color> playersPassed;
  //Observer to watch the game unfold
  private Observer observer;
  //Number of rounds before game ends via stalemate
  private static final int MAX_ROUNDS = 1000;
  // Amount of time to wait for a player's method to return, in milliseconds
  //private static final int PLAYER_TIMEOUT = 2000;
  private static final int PLAYER_TIMEOUT = 4000; // as specified for Milestone 9

  /**
   * Creates a Referee
   */
  public Referee() {
    this.colorToPlayer = new HashMap<>();
    this.kickedPlayers = new ArrayList<>();
    this.playersPassed = new HashSet<>();
  }

  /**
   * Creates a Referee with Observer
   */
  public Referee(Observer o) {
    this();
    this.observer = o;
  }

  /**
   * Add a player of color c to the game and associate it with the provided Player.
   * NOTE: Only used for integration testing.
   * @param p the Player API to add
   * @param c the color of the player
   * @param goal the goal coordinate of the player
   * @param home the home coordinate
   * @param current the current location coordinate
   */
  public void addPlayerToGame(Player p, Color c, Coordinate goal, Coordinate home, Coordinate current) {
    this.state.addPlayer(c, goal, home, current);
    this.colorToPlayer.put(c, p);
  }

  /**
   * Sets up the Referee with a gamestate (can be in progress or new)
   * @param state the gamestate the referee is now ready to run
   */
  public void setupGame(State state) {
    this.state = state;
    this.observe();
  }


  /**
   * Sends the players their initial states and establishes a connection.
   */
  private void sendInitialSetup() {
    List<Color> players = this.state.getPlayers();
    for(Color p : players) {
      State ps = state.createPlayerState();
      Coordinate goal = state.getPlayerGoal(p);
      this.sendSetupToPlayer(p, Optional.of(ps), goal);
    }
  }

  /**
   * Runs this referee's game to completion.
   * The tasks this method performs are:
   * - sending the initial setup to each player in the game
   * - running rounds until the game is over
   * - notifying players who won the game
   */
  public void runGame() {
    if (this.state == null) { // outdated legacy code used in testing
      return;
    }
    this.sendInitialSetup();

    while(!this.gameOver()) {
      this.runRound();
    }

    this.notifyWinners();
  }

  /**
   * Run a single round of the game. Run a single round while this round is not complete and there
   * is no winner computed.
   */
  private void runRound() {
    this.playersPassed = new HashSet<>();
    int currentRound = this.state.getRound();
    do {
      this.playerTurn();
      this.observe();
    } while(this.state.getRound() == currentRound && !this.state.hasWinner());
  }

  /**
   * Asks a Player for their turn and executes it if valid, kicking the Player if an illegal action is attempted.
   * @throws IllegalArgumentException when the player tries an invalid move
   * NOTE: this function is protected for testing purposes.
   */
  protected void playerTurn() {
    Color playerColor = this.state.whoseTurn();
    Optional<Action> moveOrPass = this.askPlayerForTurn(playerColor);

    if (moveOrPass.isPresent()) {
      Action a = moveOrPass.get();

      boolean hasPerformedSuccessfulAction = this.performActionIfValid(a, playerColor);

      if (hasPerformedSuccessfulAction) {
        this.state.nextTurn();
      } else {
        this.kickPlayer(playerColor);
      }
    }
    else {
      this.kickPlayer(playerColor);
    }
  }

  /**
   * Resolves the given Action as either a Pass, in which case it executes a pass action; or a MoveAction. If it is
   * a valid MoveAction, it executes the move.
   * @param a the Action to perform
   * @param playerColor the player performing the action
   * @return whether a move or pass was successfully performed
   */
  private boolean performActionIfValid(Action a, Color playerColor) {
    if (a.isMove()) {
      boolean successfullyMoved = this.performMoveIfValid(a.getMove());
      if (!successfullyMoved) {
        return false;
      }
      this.performPlayerReachedGoalLogic(playerColor);
    } else {
      this.playersPassed.add(playerColor);
    }
    return true;
  }

  /**
   * Tries to perform the player's move on the state. If the move is invalid, return false.
   * @param move the move to perform
   * @return true if the move was successfully performed, false if the move was invalid
   */
  private boolean performMoveIfValid(MoveAction move) {
    try{
      this.state.performMove(move);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * If the player has reached their current goal tile, then the referee assigns the player
   * with a new goal tile and then notifies the player of the change.
   * @param playerColor the player to update
   */
  private void performPlayerReachedGoalLogic(Color playerColor) {
    if (this.state.playerReachedGoal(playerColor)) {
      Coordinate newGoal = this.state.updatePlayerGoal(playerColor);
      this.sendSetupToPlayer(playerColor, Optional.empty(), newGoal);
    }
  }

  /**
   * Kicks the player and stores them for future punishment
   */
  private void kickPlayer(Color playerColor) {
    this.state.kickPlayer(playerColor);
    this.kickedPlayers.add(playerColor);
  }

  /**
   * Decides if the game is over or not
   * Instances of game ending:
   * No more players left in gamestate
   * Every player passed
   * 1000 rounds played
   * A player makes its way home after finding a goal
   * @return true if the game is over
   */
  private boolean gameOver() {
    boolean endRound = this.state.getRound() >= MAX_ROUNDS;
    boolean endPass = this.playersPassed.containsAll(this.state.getPlayers());
    boolean noPlayers = this.state.getPlayers().isEmpty();
    boolean hasWon = this.state.hasWinner();
    return endRound || endPass || hasWon || noPlayers;
  }

  /**
   * Notifies the players if they won or lost.
   */
  private void notifyWinners() {
    List<Color> winners = this.state.getWinners();
    List<Color> players = this.state.getPlayers();

    for(Color playerColor : players) {
      this.sendWonToPlayer(playerColor, winners.contains(playerColor));
    }

    if (this.observer != null) {
      List<Color> losers = new ArrayList<>(players);
      losers.removeAll(winners);
      this.observer.gameOver(winners, losers);
    }

  }

  /**
   * Runs a new game with the given state and players.
   * @param state the initial state of the game
   * @param players the players of the game
   */
  public void runNewGame(State state, List<Player> players) {
    this.updatePlayersToMatchState(state, players);
    this.setupGame(state);
    this.runGame();
  }

  /**
   * Runs a new game with the given list of players and a randomized initial state.
   * @param players the players of the game
   */
  public void runNewGame(List<Player> players) {
    State s = this.initializeState(players);
    this.runNewGame(s, players);
  }

  /**
   * Assigns the given players colors based on the player data in the state, matching the first color with the first
   * given player, the second with the second, and so on.
   * @param s the initial state to match to
   * @param players the players in order of age
   * @throws IllegalStateException if the number of players in the state and the given list of players are not equal
   */
  private void updatePlayersToMatchState(State s, List<Player> players) throws IllegalStateException {
    if(players.size() != s.getPlayers().size()) {
      throw new IllegalStateException("Unbalanced number of players in the state and given to the referee");
    }
    List<Color> colors = s.getPlayers();
    for (int i = 0; i < players.size(); i++) {
      Color color = colors.get(i);
      this.colorToPlayer.put(color, players.get(i));
    }
  }

  /**
   * Initializes a state by asking the players to propose boards.
   * NOTE: to be implemented later
   */
  private State initializeState(List<Player> players) {
    //Will be implemented later with proposeBoard to players
    return new State();
  }

  /*
  Communicating with player:
   */


  /**
   * Calls the setup method to inform a player. Kicks them if they misbehave/don't answer.
   * @param playerColor the color of the player
   * @param potentialState potentially the player state
   * @param destination the player's new destination
   */
  private void sendSetupToPlayer(Color playerColor, Optional<State> potentialState, Coordinate destination) {
    Player p = this.getPlayer(playerColor);
    Optional<ResponseStatus> statusOptional =
            Utils.callWithTimeout(() -> p.setup(potentialState, destination), PLAYER_TIMEOUT);
    if (statusOptional.isEmpty() || statusOptional.get() != ResponseStatus.OK) {
      this.kickPlayer(playerColor);
    }
  }

  /**
   * Calls the win method to inform a player if they won. Kicks them is they misbehave/don't answer.
   * @param playerColor the color of the player
   * @param didWin true if the player won
   */
  private void sendWonToPlayer(Color playerColor, boolean didWin) {
    Player p = this.getPlayer(playerColor);
    Optional<ResponseStatus> statusOptional = Utils.callWithTimeout(() -> p.won(didWin), PLAYER_TIMEOUT);
    if (statusOptional.isEmpty() || statusOptional.get() != ResponseStatus.OK) {
      this.kickPlayer(playerColor);
    }
  }


  /**
   * Calls the takeTurn method to ask a player for their turn. Returns empty if the player did not respond with
   * a valid turn
   * @param playerColor the color of the player
   * @return an optional holding the player's decided Action or an optional empty if the player did not respond with
   * a valid turn
   */
  private Optional<Action> askPlayerForTurn(Color playerColor) {
    Player p = this.getPlayer(playerColor);
    State playerState = this.state.createPlayerState();
    Optional<Action> actionOptional = Utils.callWithTimeout(() -> p.takeTurn(playerState), PLAYER_TIMEOUT);
    return actionOptional;
  }


  /*
  Getters:
   */

  /**
   * Gets the kicked players in an alphabetically sorted list.
   */
  public List<Player> getKickedPlayers() {
    List<Player> kicked = new ArrayList<>();
    for(Color c : this.kickedPlayers) {
      kicked.add(this.getPlayer(c));
    }
    kicked.sort(Comparator.comparing(Player::getName));
    return kicked;
  }

  /**
   * Gets the winning players in an alphabetically sorted list.
   */
  public List<Player> getWinners() {
    if (this.gameOver()) {
      List<Color> winnerColors = this.state.getWinners();
      winnerColors.removeAll(this.kickedPlayers);
      List<Player> winners = new ArrayList<>();
      for(Color c : winnerColors) {
        winners.add(this.getPlayer(c));
      }
      winners.sort(Comparator.comparing(Player::getName));
      return winners;
    }
    return new ArrayList<>();
  }

  /*
  Observer:
   */

  /**
   * Sends the newest state to the observers
   */
  public void observe() {
    if (this.observer != null) {
      this.observer.receiveState(this.state.createCopy());
    }
  }

  private Player getPlayer(Color c) {
    return this.colorToPlayer.get(c);
  }
}

