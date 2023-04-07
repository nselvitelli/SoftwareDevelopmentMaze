Pair: akshayd2020-nselvitelli \
Commit: [45fcda8796974a6877bbb36edf4238ca094b54c9](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/tree/45fcda8796974a6877bbb36edf4238ca094b54c9) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/3b55145b6fa3dfbc4b68dd81e7e6598d72d23ec1/3/self-3.md \
Score: 54/85
Grader: Rashi Jain

-6 Self eval misdirect for point 4, if you did not implement a piece of functionality, then say so.

-15 Actions lack meaningful purpose statements describing how they:
    1. rotate the spare tile by some number of degrees
    2. shift the row/col and insert a tile
    3. move the player to a designated spot
    
Additionally, why do Actions directly affect board and players without going through the game state? What is the game state responsible for?

-5 No purpose statement for kick player method
-5 No method to check whether the active player's move has returned its avatar home
