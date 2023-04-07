# Revisions

To: Prof. Ben Lerner

From: Duncan Vogel, Nicholas Selvitelli

Date: 28 November 2022

Subject: Revising Player Goals - Milestone 9

Because of the new requirement to allow for players to reach multiple goal tiles, the State and Referee
were updated to accommodate this change. The change affects the game logic in gameplay and
determining winners.

A queue of Coordinates was added to the State to keep track of the available goals left for players to hunt for.
When a player reaches their current goal, the next goal in the queue will be given to them. If there are no
goals left in the queue, the player will be given their home location as their next goal, however, reaching
this goal will not count towards their total.

Because the Referee takes a state to run a game with, there is no functionality yet to assign players with initial goals.
The Referee does not know about the queue of available goals and instead asks the state to provide the
next goal location for a player. The logic of assigning a player an initial goal would only be needed if the 
Referee generated a State. 

The State's logic has been updated to work with the queue of available goals. The state now has a map from 
color to a set of Coordinates that contains all goal positions the player has reached. Whenever a player
reaches their goal, the goal is added to the set of coordinates mapped to the player's unique color.
This set is used to determine the winners in order of number of goals reached.

In terms of gameplay, every time a player reaches a goal tile, the Referee delegates to the State to 
remove the next goal from the front of the sequence and then assigns it to the player that just landed 
on a goal tile. To inform the player of the chosen goal, the referee uses the setup method in the usual manner. 
Once the sequence is empty, players are told to go home.

When the game is over, winners are determined by comparing the number of goals each player has reached.
Ties for the largest number of goals reached are broken by the euclidean distance each player is from their
next goal. All players with the same smallest euclidean distance and largest number of goals reached are the winners.