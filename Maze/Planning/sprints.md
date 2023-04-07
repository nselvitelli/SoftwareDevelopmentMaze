# The Analysis

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 30 September 2022

Subject: Milestone 1 - Analysis of Labyrinth


In order to accomplish an implementation of the game Labyrinth for the company, a number of sprints will have to be completed. The first three sprints are descriped below and information about specific components within the sprints will be described in more detail below the sprint outline. 

## Sprint Outline
1. Sprint 1: Create Objects for Board, Tile, Player Piece
2. Sprint 2: Start Creation of Controller
3. Sprint 3: Complete Controller and Implement View

## Detailed Information About Components
* Board: XbyX size board of Tiles, board rows/columns can be slid in either direction
* Tile: Contains information about shape of path on tile (1 of 4 shapes), stores Gem information on tile
* Player Piece: Contains name for player, knows target tile and home tile, has a color
* Controller: Contains game board, handles player moves (at this time will only handle house players), handles start/end of game
* View: Renders game to screen

Sprint one will create the basic objects that are needed to contain the information about the game and its pieces. With the objects created in sprint one, sprint two will create the controller that handles player moves and sends the move information to the model to manipulate the game and reflect the changes made by the player moves. Sprint three will finalize the functionality of the controller, focusing on both the start of the game and the ending of the game. Once the game objects are completed and a controller for the game exists, the View can be created to render the Gamestate as a GUI image at each turn. 
