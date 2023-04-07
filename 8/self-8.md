**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 8

Indicate below each bullet which file/unit takes care of each task.

For `Maze/Remote/player`,

- explain how it implements the exact same interface as `Maze/Player/player`

The remote player class implements the Player/player interface:
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/remote/Player.java#L33

For each inherited method, the remote player serializes the parameters, sends the JSON message to the client over TCP, deserializes the response, and then
returns the response.

- explain how it receives the TCP connection that enables it to communicate with a client

The remote Player takes in a Socket as a constructor parameter that handles TCP communication to the client. Remote Players are instantiated within the Server class. When the server accepts a client to play the game, the server instantiates a new remote Player with the client connection as a constructor parameter.
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/remote/Player.java#L41-L42
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/Server/Server.java#L163

Communication to the client follows a similar pattern. For each implemented method that requires a request to the client (setup, takeTurn, and win), the parameters are serialized to JSON and then sent to the client using a abstracted send request method:
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/remote/Player.java#L124-L129

If the player does not return well-formed JSON, an IllegalStateException is thrown. For example, this process is shown clearly in the setup method:
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/remote/Player.java#L64-L80
The parameters are serialized to JSON, the request is sent, and the response is then deserialized and returned.

- point to unit tests that check whether it writes JSON to a mock output device

We do not test if the the Player writes JSON to a mock output device. But, we do test that the Player's methods return the expected output from calling the method. This tests that each method can deserialize the returned JSON instead.
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/test/java/remote/TestPlayer.java#L16-L63

For `Maze/Remote/referee`,

- explain how it implements the same interface as `Maze/Referee/referee`

The remote Referee does not implement the same interface as the Referee/referee. This is because the remote proxy Referee does a completely different function from the Referee/referee. Similar to the Server's referee, the proxy referee calls setup, taketurn, and win on the given player; however, the remote referee listens for TCP messages before invoking the given Player's method. Becausr the referee/Player does not call any of the Referee methods, we felt that the remote referee and the referee/Referee are not similar enough to warrant sharing an interface.

- explain how it receives the TCP connection that enables it to communicate with a server

The remote Referee takes in a socket as a constructor parameter that represents the connection to the server.
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/remote/Referee.java#L28-L33
The remote referee is instantiated with the server socket within the Client.
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/Client/Client.java#L40-L64

- point to unit tests that check whether it reads JSON from a mock input device

At the moment, we do not have a working unit test; however, we did create one to test the funcitonality:
https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/test/java/remote/TestReferee.java#L20-L58
The test fails because of how the input stream is mocked, not because the method fails.

For `Maze/Client/client`, explain what happens when the client is started _before_ the server is up and running:

- does it wait until the server is up (best solution)

The client does not wait and immediately tries to join. Because of this, the client fails and does not connect to the server.

- does it shut down gracefully (acceptable now, but switch to the first option for 9)

The client does not shut down gracefully on its own. In testing, we used a Runnable to run the Client in its own thread which prevented exceptions from being thrown on the main thread. So, for testing, clients do shutdown gracefully.

For `Maze/Server/server`, explain how the code implements the two waiting periods:

- is it baked in? (unacceptable after Milestone 7)

No it is not baked in. There are defined constants that determine how long each waiting period takes. Additionally, there is one method that handles a single waiting period. A different method handles the logic for running 1-2 different waiting periods.

https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/server/Server.java#L80-L167

- parameterized by a constant (correct).

https://github.khoury.northeastern.edu/CS4500-F22/ambidextrous-lions/blob/98634e0c945c921832b619b122b9fe15c3cd6eca/Maze/src/main/java/server/Server.java#L34-L35

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

