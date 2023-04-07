package model.state;

import java.awt.Color;
import java.util.Optional;
import util.Direction;
import model.board.Board;
import referee.Player;
import referee.SafePlayer;
import util.Posn;
import util.Tuple;

public class PlayerData {

  private final Color avatar;

  private final Posn currentLocation;

  private final Posn homeLocation;

  private final Posn goalLocation;

  private final boolean visitedTargetedGemPairTile;

  private final boolean visitedHomeAfterVisitingGoal;

  private final Optional<SafePlayer> playerAPI;

  // This constructor is used by unit tests
  public PlayerData(Color avatar, Posn currentLocation,
      Posn homeLocation, boolean visitedTargetedGemPairTile) {
    this(avatar, currentLocation, homeLocation, new Posn(-1, -1),
        visitedTargetedGemPairTile, false, Optional.empty());
  }

  // This constructor is used by the testing harness
  public PlayerData(Posn currentLocation, Posn homeLocation, Color avatar) {
    this(avatar, currentLocation, homeLocation, new Posn(-1, -1),
        false, false, Optional.empty());
  }

  // This constructor is used by the Referee and this class to create a full Player
  public PlayerData(Color avatar, Posn currentLocation,
      Posn homeLocation, Posn goalLocation, boolean visitedTargetedGemPairTile, boolean visitedHomeAfterVisitingGoal, Optional<Player> playerAPI) {
    this.avatar = avatar;
    this.visitedTargetedGemPairTile = visitedTargetedGemPairTile;
    this.visitedHomeAfterVisitingGoal = visitedHomeAfterVisitingGoal;
    this.currentLocation = currentLocation;
    this.homeLocation = homeLocation;
    this.goalLocation = goalLocation;
    this.playerAPI = playerAPI.map(SafePlayer::new);
  }

  public Color getAvatar() {
    return avatar;
  }

  public Posn getCurrentLocation() {
    return currentLocation;
  }

  public PlayerData updateCurrentLocation(Posn currentLocation) {
    return new PlayerData(this.avatar, currentLocation,
        this.homeLocation, this.goalLocation, this.visitedTargetedGemPairTile, this.visitedHomeAfterVisitingGoal, this.playerAPI.map(SafePlayer::getPlayer));
  }

  public Posn getHomeLocation() {
    return homeLocation;
  }

  public boolean getVisitedTargetedGemPairTile() {
    return visitedTargetedGemPairTile;
  }

  public boolean getVisitedHomeAfterVisitingGoal() {
    return visitedHomeAfterVisitingGoal;
  }

  public PlayerData updateIfVisitingTargetedGemPairTile() {
    if(isPlayerOnGoal()) {
      return new PlayerData(this.avatar, this.currentLocation,
          this.homeLocation, this.goalLocation, true, this.visitedHomeAfterVisitingGoal, this.playerAPI.map(SafePlayer::getPlayer));
    }
    return this;
  }

  public PlayerData updateIfOnHomeAfterReachingGoal() {
    if(isPlayerOnHome() && this.visitedTargetedGemPairTile) {
      return new PlayerData(this.avatar, this.currentLocation,
          this.homeLocation, this.goalLocation, true, true, this.playerAPI.map(SafePlayer::getPlayer));
    }
    return this;
  }


  public boolean isPlayerOnGoal() {
    return this.currentLocation.equals(this.goalLocation);
  }

  public boolean isPlayerOnHome() {
    return this.currentLocation.equals(this.homeLocation);
  }

  /**
   * Takes in a planned board move and adjusts the player's current location accordingly.
   * @param plannedBoardMove the planned board move
   * @param width of the board
   * @param height of the board
   * @return the updated player data
   */
  public PlayerData updateCurrentLocationIfOnSlide(Optional<Tuple<Integer, Direction>> plannedBoardMove, int width, int height) {
    if(plannedBoardMove.isEmpty()) {
      return this;
    }

    int index = plannedBoardMove.get().getFirst();
    Direction dir = plannedBoardMove.get().getSecond();

    if(((dir == Direction.LEFT || dir == Direction.RIGHT) && index == currentLocation.getY())
      || ((dir == Direction.UP || dir == Direction.DOWN) && index == currentLocation.getX())) {

      Posn newPos = Direction.offsetPosnWithDirection(dir, currentLocation, 1);

      int x = newPos.getX() % width;
      x = x < 0 ? width - 1: x;

      int y = newPos.getY() % height;
      y = y < 0 ? height - 1: y;

      newPos = new Posn(x, y);
      return new PlayerData(avatar, newPos, homeLocation,  this.goalLocation,
          visitedTargetedGemPairTile, this.visitedHomeAfterVisitingGoal, this.playerAPI.map(SafePlayer::getPlayer));
    }
    return this;
  }

  public Optional<SafePlayer> getPlayerAPI() {
    return this.playerAPI;
  }

  public Posn getGoalLocation() {
    return this.goalLocation;
  }


  /**
   * Determines if this player is the same distinct player as the given player. A player is the
   * "same" if their avatar colors are equal.
   * @param other the player to compare against
   * @return if the compared players are not distinct
   */
  public boolean isSameDistinctPlayer(PlayerData other) {
    return this.avatar.equals(other.avatar);
  }


  /**
   * Returns a new PlayerData that is a copy of this PlayerData but the name and playerAPI fields
   * have been updated to reflect the given playerAPI.
   * @param playerAPI the playerAPI to update to use
   * @return the new PlayerData
   */
  public PlayerData updatePlayerAPI(Player playerAPI) {
    return new PlayerData(this.avatar, this.currentLocation, this.homeLocation,
        this.goalLocation, this.visitedTargetedGemPairTile, this.visitedHomeAfterVisitingGoal, Optional.of(playerAPI));
  }

  @Override
  public boolean equals(Object o) {
    if(o instanceof PlayerData) {
      PlayerData other = (PlayerData)o;
      return avatar.equals(other.avatar)
          && currentLocation.equals(other.currentLocation)
          && homeLocation.equals(other.homeLocation)
          && visitedTargetedGemPairTile == other.visitedTargetedGemPairTile
          && ((playerAPI.isPresent() && other.playerAPI.isPresent() && playerAPI.get().equals(other.playerAPI.get()))
              || (playerAPI.isEmpty() && other.playerAPI.isEmpty()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return avatar.hashCode() + currentLocation.hashCode()
        + homeLocation.hashCode() + Boolean.hashCode(visitedTargetedGemPairTile) + playerAPI.hashCode();
  }


}
