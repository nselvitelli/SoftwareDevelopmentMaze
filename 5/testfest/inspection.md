Pair: akshayd2020-nselvitelli \
Commit: [ffc92d0b32f48f4e017ff86bb1e0eb78813263be](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/tree/ffc92d0b32f48f4e017ff86bb1e0eb78813263be) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/10f70a06bb9ab5af7c524095bb34c059e311c2f8/5/self-5.md \
Score: 146/160 \
Grader: Ryan Jung


- [14/20] For the self eval, see misdirect below.

The _player_ should offer four methods:
- [10/10] `name`
- [10/10] `propose board`
- [10/10] `setting up`
- [10/10] `take a turn`
- [10/10] `did I win`


The _referee_ must perform the following tasks in order and hence must have separate functions:
- [10/10] setting up the player with initial information
- [10/10] running rounds until the game is over
- [0/10] running a round
  - Does not implement a method for running a round
- [10/10] score the game
  - Links to irrelevant functionality; show me how scoring is done, not how players are informed.

- [10/10] a unit test for the referee function that returns a unique winner
- [10/10] a unit test for the scoring function that returns several winners


Design
- [10/10] rotates the tile before insertion
- [10/10] selects a row or column to be shifted and in which direction
- [10/10] selects the next place for the player's avatar

