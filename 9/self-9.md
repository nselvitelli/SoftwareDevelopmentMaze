**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 9

Indicate below each bullet which file/unit takes care of each task.

Getting the new scoring function right is a nicely isolated design
task, ideally suited for an inspection that tells us whether you
(re)learned the basic lessons from Fundamentals I, II, and III. 

This piece of functionality must perform the following four tasks:

- find the player(s) that has(have) visited the highest number of goals

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/src/Common/State.java#L427-L448

- compute their distances to the home tile

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/src/Common/State.java#L450-L474

- pick those with the shortest distance as winners

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/src/Common/State.java#L421-L425

- subtract the winners from the still-active players to determine the losers

This was not in the spec and is not required as we determine the players who were kicked a different way. 


The scoring function per se should compose these functions,
with the exception of the last, which can be accomplised with built-ins. 

1. Point to the scoring method plus the three key auxiliaries in your code. 

Code examples are shown above.

3. Point to the unit tests of these four functions.

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/tests/Common/StateTest.java#L151-L195
- This test checks that while the red player made it home before the blue player, the blue player is considered the winner.

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/tests/Referee/RefereeTest.java#L76-L118
- This test ensures that the red player won the game because it was closer to its next goal than the blue player.

https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/2c5f1ff0adb050553567594edd407c0a0b2d2772/Maze/tests/Referee/RefereeTest.java#L135-L159
- This test checks that the spec requirement that the player moves onto a goal or home tile works with the addition of multiple goals. The player is forced to move off their goal and then back on in four moves to win the game. The player should blow up on the third move. If this functionality was not working correctly, then the player would win in fewer turns (and not be kicked).

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

