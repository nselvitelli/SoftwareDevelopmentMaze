Pair: stoic-eagles \
Commit: [2c5f1ff0adb050553567594edd407c0a0b2d2772](https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/tree/2c5f1ff0adb050553567594edd407c0a0b2d2772) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F22/stoic-eagles/blob/fded0471e3853dd404fc31a9db1c420ecc7756e9/9/self-9.md \
Score: 90/100 \
Grader: Mike Delmonaco

Scoring logic does not belong in the state.

You actually do need to compute losers. It's just that you don't return them from the referee.
You need to know the losers so you can inform the losers that they lost. You compute this
implicitly by looping through the well-behaved players and checking membership in the winners list.

-10 Should have a test with no players.
