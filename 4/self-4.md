**If you use GitHub permalinks, make sure your links points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 4

The milestone asks for a function that performs six identifiable
separate tasks. We are looking for four of them and will overlook that
some of you may have written deep loop nests (which are in all
likelihood difficult to understand for anyone who is to maintain this
code.)

Indicate below each bullet which file/unit takes care of each task:

1. the "top-level" function/method, which composes tasks 2 and 3 

Interface method: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/Strategy.java#L19

Implementation: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L18-L33


2. a method that `generates` the sequence of spots the player may wish to move to

The method to return the possible locations: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L106-L117

The method that orders them: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L42

- Usage: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L20-L23
- Implementations: 
   - Euclid: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/EuclidStrategy.java#L13-L16 
   - Riemann: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/RiemannStrategy.java#L12-L14

3. a method that `searches` rows,  columns, etcetc. 

https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L51-L66
Helpers:
- https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L78-L97
- https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L125-L131
- https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L139-L145

4. a method that ensure that the location of the avatar _after_ the
   insertion and rotation is a good one and makes the target reachable

Called at: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Players/AbstractBasicStrategy.java#L90
Method Signature: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/State.java#L19
Method Implementation: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/MazeState.java#L35-L37
- https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/Action.java#L15
- https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/BasicTurnAction.java#L32-L54

ALSO point to

- the data def. for what the strategy returns

The Action interface: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/Action.java#L1-L35
Pass Action implementation: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/PassAction.java#L1-L20
Basic Turn Action: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/Common/BasicTurnAction.java#L1-L137

- unit tests for the strategy

https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/85bea29213184130394eb234082970bc2b5e0104/Maze/src/test/java/model/strategy/TestAbstractBasicStrategy.java#L45-L260

The ideal feedback for each of these points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality or realized
them differently, say so and explain yourself.


