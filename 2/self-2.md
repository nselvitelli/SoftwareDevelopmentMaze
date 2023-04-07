## Self-Evaluation Form for Milestone 2

Indicate below each bullet which file/unit takes care of each task:

1. point to the functinality for determining reachable tiles 

   - a data representation for "reachable tiles" [Tile.java L8-L14](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Tile.java#L8-L14)
      - This method takes in a list of all physical neighbors coupled with their relative direction to this Tile. It then returns a list of all neighboring tiles that can both reach this tile and be reached by this tile. The method `findAllAccessibleTiles` in the Board7x7 implementation at [Board7x7.java L112-L131](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board7x7.java#L112-L131) searches for accessible Tiles using BFS. This Tile method is used to filter a Tile's neighbors to only the ones accessible from this Tile so that BFS will only traverse to successor nodes that are accessible.
   - its signature and purpose statement [Board.java L62-L67](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board.java#L62-L67)
   - its "cycle detection" coding (accumulator) [Board7x7.java L125](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board7x7.java#L125)
   - its unit test(s) [TestBoard7x7.java L414-L457](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/test/java/model/TestBoard7x7.java#L414-L457)

2. point to the functinality for shifting a row or column 

   - its signature and purpose statement [Board.java L40-L51](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board.java#L40-L51)
   - unit tests for rows and columns [TestBoard7x7.java L95-389](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/test/java/model/TestBoard7x7.java#L95-L389)

3. point to the functinality for inserting a tile into the open space

   - its signature and purpose statement [Board.java L40-L51](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board.java#L40-L51)
      - This functionality is shared with sliding a column or row. The method for sliding a column or ow safely takes in a tile as a parameter to place into the empty spot left on the board from sliding. The purpose statement for placing this tile on the board can be found at [Board.java L9-L16](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/main/java/model/Board.java#L9-L16).
   - unit tests for rows and columns [TestBoard7x7.java L95-389](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/master/Maze/src/test/java/model/TestBoard7x7.java#L95-L389)

If you combined pieces of functionality or separated them, explain.

If you think the name of a method/function is _totally obvious_,
there is no need for a purpose statement. 

The ideal feedback for each of these points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

