## Self-Evaluation Form for Milestone 3

Indicate below each bullet which file/unit takes care of each task:

1. rotate the spare tile by some number of degrees
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/MazeState.java#L46
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L70
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L101-L104

2. shift a row/column and insert the spare tile
   - plus its unit tests
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/MazeState.java#L46
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L31-L66
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L71
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L106-L114

Valid shift tests:
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/test/java/model/state/TestBasicTurnAction.java#L247-L333
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/test/java/model/state/TestMazeState.java#L126-L172

Invalid shift tests:
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/test/java/model/state/TestBasicTurnAction.java#L134-L170

   
3. move the avatar of the currently active player to a designated spot
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/MazeState.java#L46
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L72
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/BasicTurnAction.java#L116-L119

4. check whether the active player's move has returned its avatar home
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/MazeState.java#L66
Our interpretation of the spec was that the referee would check whether the player's move has returned its avatar home based on the game state.

5. kick out the currently active player
https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/45fcda8796974a6877bbb36edf4238ca094b54c9/Maze/src/main/java/model/state/MazeState.java#L75-L79

The ideal feedback for each of these points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

