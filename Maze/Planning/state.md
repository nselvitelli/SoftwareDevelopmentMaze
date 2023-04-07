# The Gamestate (Referee)

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 7 October 2022

Subject: Milestone 2 - Designing the Gamestate


The gamestate will act as the referee for the game Labyrinth. It will regulate player turns and ensure only valid moves are made. The gamestate should be able to access any information regarding the game and carry out any manipulations of the game's state.

## Data Representations
1. Board: the gamestate will hold a Board object that will be the only board for the game.
2. Players: the gamestate will hold a Queue of Player objects. The Player object will hold information about its home, current location, goal tile, and anything else that will come up in the future regarding the player.

## Functionality
1. playTurn(Player p, Boolean slideRow, Integer index, Orientation o, int row, int col): performs a player's turn
    1. slide(Boolean slideRow, Integer index): slide the given row/column at the specified index.
    2. insertSparePiece(Orientation o): insert the spare piece at the given orientation.
    3. movePlayer(Player p, int row, int col): move the player to the given position.
    4.validateTurn(): ensures the turn is valid.
2. nextPlayerTurn(): pops the player whose turn it is from the Player Queue and returns the Player object.
3. kickPlayer(Player p): removes the Player p from the Player Queue. To be used in the case of cheating or invalid turns.
4. gameOver(): returns true if the game is over.


