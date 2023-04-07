**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 8

Indicate below each bullet which file/unit takes care of each task.

For `Maze/Remote/player`,

- explain how it implements the exact same interface as `Maze/Player/player`

Our ProxyPlayer does implement the Player interface. This ensures it is compatible with our Referee and has all the needed methods. The Referee can run a game with both players across a network and local AI Players.
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/f53ead4c4085a964c9d3cf8c3b3ce112223d5200/Maze/src/Remote/Server/ProxyPlayer.java#L16-L21

- explain how it receives the TCP connection that enables it to communicate with a client

The ProxyPlayer is given the TCP connection in its constructor. It is the Server that handles initial connection with players during the signup period, so it is the Server that creates the ProxyPlayer and hands over that connection.
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/f53ead4c4085a964c9d3cf8c3b3ce112223d5200/Maze/src/Remote/Server/RefereeServer.java#L78-L92

- point to unit tests that check whether it writes JSON to a mock output device
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/1cd2a9060ea7319f6e8cdcc930abb03a38d7e692/Maze/tests/Remote/Server/ProxyPlayerTest.java#L20-L126

For `Maze/Remote/referee`,

- explain how it implements the same interface as `Maze/Referee/referee`

Our ProxyReferee class implements Runnable, not Referee. We chose to do this to allow the ProxyReferee to be easily runnable and have it listen for incoming messages. The methods that the ProxyReferee has that it shares with a normal referee would be executeTakeTurn, executeWin, and executeSetup. These methods simply query the player for their response when the actual referee across the network prompts it.

- explain how it receives the TCP connection that enables it to communicate with a server

The TCP connection is given to the proxy referee upon creation by the PlayerConnector. The PlayerConnector is a class that establishes the connection given the information and a player. This allows us to easily create new client classes for other imlementations of Player.

https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/5d6217cda604296733187a10f3c2bc13cfaa8edb/Maze/src/Remote/Client/PlayerConnector.java#L15-L31
- point to unit tests that check whether it reads JSON from a mock input device

https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/1cd2a9060ea7319f6e8cdcc930abb03a38d7e692/Maze/tests/Remote/Client/ProxyRefTest.java#L33-L168

For `Maze/Client/client`, explain what happens when the client is started _before_ the server is up and running:

- does it wait until the server is up (best solution)
- 
Our AIPlayerClient is constructed with the network information it needs to connect to the server. Once it's ready to connect, the method run() must be called and this will create a local AIPlayer and establish a connection to the Server.
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/9930fd042be264e203b6decc890bcd22a14b38cd/Maze/src/Remote/Client/AIPlayerClient.java#L50-L54
- does it shut down gracefully (acceptable now, but switch to the first option for 9)

As soon as the Client sets up the connection, it hands all ork over to the ProxyReferee and shuts down. The ProxyReferee shuts down once it has recieved the won() call over the network, indicating that the game is over.
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/9930fd042be264e203b6decc890bcd22a14b38cd/Maze/src/Remote/Client/ProxyReferee.java#L62-L80


For `Maze/Server/server`, explain how the code implements the two waiting periods:

- is it baked in? (unacceptable after Milestone 7)

The two waiting periods are baked in as of now. We did not know that this would be a variable in setting up games.
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/6f0e4f2c26db9eb41bdfde9e3ef4f2cfe98e92f8/Maze/src/Remote/Server/RefereeServer.java#L46-L49
- parameterized by a constant (correct).

Our Server has the following constants to sign up players:
https://github.khoury.northeastern.edu/CS4500-F22/fearless-rabbits/blob/6f0e4f2c26db9eb41bdfde9e3ef4f2cfe98e92f8/Maze/src/Remote/Server/RefereeServer.java#L20-L26

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

