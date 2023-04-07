**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 7

Indicate below each bullet which file/unit takes care of each task:

The require revision calls for

    - the relaxation of the constraints on the board size
    - a suitability check for the board size vs player number 

1. Which unit tests validate the implementation of the relaxation? 

We include tests which run on 3x3 boards. For example, [this slide row test](https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/045dec22e7f778a2274631cb4e34ea5217471e21/Maze/tests/BoardTest.java#L81-L91)
that is set up [here](https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/045dec22e7f778a2274631cb4e34ea5217471e21/Maze/tests/BoardTest.java#L31-L40) \
Unfortunately we did not include tests on boards larger than 7x7.

2. Which unit tests validate the suitability of the board/player combination? 

We do not have an explicit check that the number of players can fit on the board, we simply
throw an exception [here](https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/1d3398a1452d1d08a811e53f57fd6eb53fd46199/Maze/src/Common/State.java#L95-L99)
when a player is added but is invalid (one of the reasons for this can be invalid home tile).
Therefore, we did not write a test for this case.
   
The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

