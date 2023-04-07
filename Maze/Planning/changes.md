# Changes

To: Prof. Ben Lerner

From: Joseph Kwilman, Duncan Vogel

Date: 10 November 2022

Subject: Milestone 7 - The Clean Up


#### Blank tiles in the board
*Difficulty*: 5/5 -
This would require refactoring not only the data design for the board class, but also
nearly everywhere in the code to include checks for empty tiles and conditional logic. There are
over 80 usages of Tile in our code, and in each of these spots we would either need to consider
Optional<Tile> or turn Tile into an interface which can be asked if it is empty, representing a huge
amount of work and plenty of potential bugs that need to be avoided.


#### Movable goal tiles
*Difficulty*: 3/5 -
We currently implement player goals as Coordinates. This means that allowing movable goals would 
require constant communication with the player alerting them of goal changes.
We would need to represent player goals as Tiles instead, and then create Tile lookups 
whenever the goals need to be considered, which is a fairly large change. 
This would involve us updating the strategies to consider the spare tile as a potential goal, which 
would be a considerable endeavor.


#### Ask players to sequentially pursue several goals, one at a time
*Difficulty*: 2/5 -
This would require us to implement goals as a Queue of Coordinates instead of a single Coordinate.
As players reach their goal, we could remove the goal from the queue. We know a player should head home
when their queue is empty. We would communicate to the players their new goals as the game is played
by calling setup() on the player. This change would not be considerably hard.