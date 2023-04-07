# The Player

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 11 October 2022

Subject: Milestone 3 - Designing the Player



## Player Data:
The information the player needs has to do with what the player wants to do. The strategy for the game is to get to the goal then get to the home, so the player needs to know these. The player also has access to other players' home tiles, but it does not have access to the goal tiles of the other players. The only information necessary about the player itself is it's current location. Finally, the player needs access to the board of tiles and the spare tile to know the configuration of the game and how to make valid moves.

* Coordinate position: the position of the player.
* Coordinate goal: the location of the tile with the Gem the player is trying to collect.
* Map<Player, Coordinate> homes: a mapping of the players to their respective homes. This includes the information regarding the home of this player as well a all others.
* Tile[][] gameboard: the board of tiles.
* Tile spare: the spare tile to the side of the board. This is publicly accessible as the players need to know it to perform a move.


## Player Functionality
The highest priority of the player is the functionality that allows them to perform valid moves. An action is defined by a direction to slide the row/column, the index of the row\column to be moved, and the coordinate the player wants to move to. The player must also be able to orient the spare piece through rotations before performing their move. The player might also want to leave the game without being kicked, so we will account for this.

* List<Tiles> reachableTiles(): Returns a list of tiles that the player can reach from its current location.
* Boolean validAct(Direction d, int i, Coordinate c): returns true if the move specified is a valid move.
* void act(Direction d, int i, Coordinate c): slides the row/column at the index i the direction d and moves the player to the coordinate c.
* void rotate90(int times): rotates the spare piece 90 degrees the specified number of times.
* void leaveGame(): allows the player to leave the game if they quit the game or disconnect.








