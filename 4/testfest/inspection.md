Pair: akshayd2020-nselvitelli \
Commit: [85bea29213184130394eb234082970bc2b5e0104](https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/tree/85bea29213184130394eb234082970bc2b5e0104) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F22/akshayd2020-nselvitelli/blob/b693c03d1717205295bb92863a7842e1dad021a8/4/self-4.md \
Score: 100/110 \
Grader: Connie Tang

### Self-Eval

10/10
- helpful and accurate self-eval

### Programming

90/100

- [20/20] a data definition for the return value of a call to strategy
  - WARNING: BasicTurnAction and PassAction both have data definitions but you should also include some sort of documentation in the interface
- [15/15] good name, signature/types, and purpose statement for the top-level function that *composes* generating a sequence of spots to move to and searching
- [15/15] good name, signature/types, and purpose statement for generating the sequence of alternative goals
- [15/15] good name, signature/types, and purpose statement for searching rows then columns
  - Not mentioned in the purpose statement how the search is conducted (i.e. first rows, then columns)
- [15/15] good name, signature/types, and purpose statement for some function/method that validates the location of the avatar after slide/insert is not the target and the current target is reachable
- [10/10] for unit test that produces an action to move the player
- [0/10] for unit test that forces player to pass on turn

### Design Task (Ungraded)

Your protocol does not mention setting up players with their treasure target.

Linked to post-deadline commit 85bea29213184130394eb234082970bc2b5e0104 from 10-21 12:02 AM
