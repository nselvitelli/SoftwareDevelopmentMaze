# Turning Distributed

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 3 November 2022

Subject: Milestone 6 - Distributed Software System

To run the game with remote players, communication between players and the referee must be achieved. Gathering players will require the software to allow remote connections and for players to be able to connect to it. A class called Connection will be used to handle establishing connections and facilitating all communication between the referee and the players. 

Connection will first open multiple sockets, one for each player that is expected to play the game. Each socket will bind to a designated port number and wait for a connection to be established from a players machine. Once this connection is opened, the Connection class will send a simple "hello" message and wait for an acknowledgement from the player to ensure that the socket is working as expected. This acknowledgement can be anything. If a player does not connect to a socket in a certain amount of time (timeout), the socket will be closed. After all sockets have a connection or have been closed due to timeout, Connection will communicate to the referee to signal that a game can begin. 

Beginning a game will start by polling all the players to ask for a game board to be used. The expected response to this is a valid Board JSON object as specified int he spec. If provided, the referee will select one of the boards provided and use that to establish the game. Each player will be sent the beginning game board and their goal tiles. An alertPlayer method in the Communication class will be called by the referee to transmit data to the player. The referee will expect this method to return True from the Communication class, which would mean the Communication class heard something back from the players. The Communication class will then write to the socket of the corresponding player to transfer the data to them as a State JSON and a Coordinate (described in spec). The Communication class will return false to the referee if the player times out without responding with anything.

On a players turn, the Communication class will send a message to the player alerting them of their turn and listen to a response from the player. If the player does not respond with a move/pass in the timeout window, the connection will be closed and the Connection class will alert the referee to kick the player from the game.
The data recieved must be a PASS or a valid move (JSONArray of Index, Direction, Degree, Coordinate). In the event that a player passes an invalid move to the referee that breaks the rules of the game, the referee will alert the Connection that the player has been kicked and the socket for that player will be closed.

At the completion of a game, the Connection class will alert each player to the outcome of the game using the sockets. After an acknowledgement from the player that they have received their game outcome, the Connection class will close all sockets and exit gracefully. 
