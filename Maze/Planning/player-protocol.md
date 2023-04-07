# Player Protocol

To: Prof. Ben Lerner

From: Joe Kwilman, Nathan Moore

Date: 20 October 2022

Subject: Milestone 4 - Designing the Player Protocol

The UML diagram for our player protocol is below. More detail will be gone into beneath that.

<img width="755" alt="image" src="https://media.github.khoury.northeastern.edu/user/11179/files/eadd8a26-a4f8-4d3d-b2a3-67fb1ce92b89">


## Protocol
1. A player will request to join the game. Provided a player can be added, the state will call the function addPlayer. Upon successful completion of this function, the game will respond with a confirmation to the player that they have been added to the game.
2. Once the players have been added to the game, the state will announce to all players that the game is starting. The state will call nextTurn to start the game. This function will give each player a mockState which allows them to see their own goals and the current gameboard.
3. If it is someone elses turn other than the player, the player will be alerted to wait. At the completion of another persons turn, the state will call nextTurn and give each player a new mockState to use.
4. On the players turn, the state will alert the player that it is their turn. The player will choose their move and send their move to the state in the form of moveAction and Coordinate.
5. The state will validate their move, and if it is valid it will call the performMove function. Following the completion of the move, all players will be given a new mockState once the nextTurn function is called. If the move is unacceptable, the state will call kickPlayer and remove them from the game, call nextTurn, and continue with the game.
6. In the event that a player wins the game, the player will be alerted that they have won. All other players will be alerted that they have lost.
