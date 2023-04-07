**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 7

Indicate below each bullet which file/unit takes care of each task:

The require revision calls for

    - the relaxation of the constraints on the board size
    - a suitability check for the board size vs player number 

1. Which unit tests validate the implementation of the relaxation?

We did not realize this piece of functionality. Although we did not write unit tests, we do supoprt the relaxed constraint here: https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/b31fce7ddbe46e6ed5539d4c98952ae3883fbee4/Maze/src/main/java/model/board/RectBoard.java#L31-L33

2. Which unit tests validate the suitability of the board/player combination? 

We did not realize this piece of functionality. However, we do check that each home is on a distinct tile. The Exception is commented out because this
constraint was not enforced for this test harness. We wanted to avoid failing any harness tests that do not follow this constraint.

https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/d9174162c421682f82253eb83894e4b839f4e398/Maze/src/main/java/model/state/MazeState.java#L32-L34

We specifically did not put a check on board size in relation to the number of given players within the Referee because we do not yet support the `proposeBoard0` functionality.
   
The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

