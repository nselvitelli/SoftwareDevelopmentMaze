Pair: akshayd2020-nselvitelli \
Commit: [1d82077021e24a857f85d1752078e8cc28dfa805](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/tree/1d82077021e24a857f85d1752078e8cc28dfa805) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/bbe44f6ac8b0f161f50215d596d1b30b98194799/2/self-2.md \
Score: 60/80 \
Grader: James Packard \

> Please use GitHub permalinks when referencing lines of code in your self-eval. If you later rename/remove the file, the permalink will continue to work.

[28/40] an operation that determines the reachable tiles from some spot
  1. [+6/10] : data definition for coordinates
     > (-4) Broken link to Tile.java  
     > Your Posn class has no purpose statements.
  2. [+6/10] : signature/purpose statement
     > (-4) Broken link to Board.java  
     > should this method return a list? does the order of reachable tiles matter and should no duplicates be a guarantee?
  3. [+6/10] : method is clear about how it checks for cycles
     > (-4) Broken link to Board7x7.java  
     > This method is 23 lines long. Can it be separated into methods that each have one task?
  4. [+10] : at least one unit test with a non-empty expected result
     > Link doesn't point to all the right lines but close enough

---

[26/30] an operation for sliding a row or column in a direction 
  - [+6/10] signature/purpose statement
    > Broken link to Board.java
  - [+10] the method checks that row/column has even index 
  - [+10] at least one unit test for rows and one for columns
    > Link doesn't point to all the right lines but close enough

---

[6/10] an operation for rotating and inserting the spare tile
  > (-4) Broken link to Board.java

---

Design task (ungraded):

> The game state should keep track of the spare tile, since your board does not contain it.  
> The game state should also contain the last move, so that the referee can enforce that it is not undone by the next move.  
> Recall the definition of a 'wish list' from Fundamentals I and II.  
