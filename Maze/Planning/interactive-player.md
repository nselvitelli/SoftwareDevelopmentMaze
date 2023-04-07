# Interactive Player Mechanism

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 27 October 2022

Subject: Milestone 5 - Interactive Player Mechanism


In order for a player to interact with the game using a graphical player interface, there will first need to be a way to graphically represent the game. For this, the game will be rendered using the JFrame library that was used for the TAHBPL assignments. This library provides an easy way to render JSON values as graphics. As we have been using JSON in our integration testing, these values will also be usable for creating a visual representation of the game that a player could use to see the game.

Once a player is capable of seeing the game, they will then need to interact with the game in order to take their turn. There are three aspects of a turn, and each of them will be addressed in a different area of the graphical representation. The spare tile will be located to the side of the board, in a similar location to where the next Tetronimo would be displayed in a game of Tetris. Clicking on this tile would rotate it 90 degrees and multiple clicks would rotate it to the desired orientation. The game board would be in the center of the graphical representation, with arrows on the end of each slideable row or column. A user click on one of these arrows would slide the row/column in the corresponding direction. Finally, clicking on a square on the board would move the players avatar to the clicked coordinate.

Getting the coordinate of the players click in relation to the JFrame window will allow the program to know what is being clicked on and make the necessary function calls to change the game state. The order of a turn will be enforced by providing prompts to the user as to what they should be clicking (spare tile, row/column move arrow, or coordinate on the board). Illegal actions will be treated as breaking the rules and the player will be removed from the game for doing so.
