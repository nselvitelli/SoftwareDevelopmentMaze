**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 5

Indicate below each bullet which file/unit takes care of each task:

The player should support five pieces of functionality: 

- `name`
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Player.java#L20

- `propose board` (okay to be `void`)
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Player.java#L29

- `setting up`
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Player.java#L40

- `take a turn`
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Player.java#L49

- `did I win`
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Player.java#L55

Provide links. 

The referee functionality should compose at least four functions:

- setting up the player with initial information
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L71
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L83-L89

- running rounds until termination
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L115-L116
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L98-L100

- running a single round (part of the preceding bullet)
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L116-L125
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L136-L160
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L168-L173

- scoring the game
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L75
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/Players/Referee.java#L179-L252

Point to two unit tests for the testing referee:

1. a unit test for the referee function that returns a unique winner
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L322-L341
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L36-L42
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L89-L114
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L143-L147
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L149-L188

3. a unit test for the scoring function that returns several winners
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L384-L405
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L63-L69
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L89-L114
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L143-L147
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/044641c97ee4138b91ea524b040a5f61b2d3ee35/Maze/src/test/java/referee/TestReferee.java#L250-L275

The ideal feedback for each of these points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files -- in the last git-commit from Thursday evening. 

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

