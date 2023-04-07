package Remote.Server;

import Common.JSONConverter;
import Common.State;
import Common.Utils;
import Players.Player;
import Referee.Referee;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Static server class which signs up players and creates a Referee to run a game.
 */
public class RefereeServer {
    /** Setup time to wait for players to connect, in milliseconds. **/
    private static final int SETUP_TIME = 20000;
    /** Setup time to wait for players to provide a name, in milliseconds. **/
    private static final int SETUP_NAME_TIME = 2000;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;
    /** The number of times the server should wait for signups before canceling and returning a default result. **/
    private static final int NUM_SIGNUP_ROUNDS = 2;

    /**
     * This method handles the logic to run a Server that runs a single game and returns the output
     * as formatted JSON.
     * This method does the following tasks:
     * - delegates to signupPlayers to listen for clients to connect on the given port
     * - checks if enough clients have signed up. If not, returns an empty winners and kicked players result [[],[]]
     * - If there are enough players, creates a referee and runs an entire game with the signed up clients.
     *   When the game is over, the method returns the result of the referee.
     * @param port the port to accept player connections on
     * @param initState the state of the game to run
     * @return A JSONArray containing a JSONArray of sorted winner names and a JSONArray of sorted cheater names
     */
    public static JSONArray runServer(int port, State initState) {
        List<Player> players = signupPlayers(port);

        if (players.size() < MIN_PLAYERS) {
            return getDefaultResult();
        }
        // returns ending message
        return runGame(initState, players);
    }

    /**
     * Accepts players from the given port. The method runs one single round to signup players. If
     * there is not enough players signed up, a second round is run. After the signup rounds are
     * over, a list of all players that were able to sign up are returned.
     * @param port the port to accept clients on
     * @return a list of all players that were able to sign up
     */
    private static List<Player> signupPlayers(int port) {
        // NOTE: list is ordered by ascending age (youngest to oldest)
        List<Player> clientsSignedUp = new ArrayList<>();

        ServerSocket socket = getServerSocketOrQuit(port);

        for (int i = 0; i < NUM_SIGNUP_ROUNDS && clientsSignedUp.size() < MIN_PLAYERS; i++) {
            signupPlayersSingleRound(socket, clientsSignedUp);
        }
        return clientsSignedUp;
    }

    /**
     * Returns the default game result if not enough players signed up to play the game.
     * @return a JSON representation of the winners and kicked players list "[[],[]]"
     */
    private static JSONArray getDefaultResult() {
        JSONArray result = new JSONArray();
        result.put(new JSONArray());
        result.put(new JSONArray());
        return result;
    }

    /**
     * Waits for SETUP_TIME, accepting connections on ACCEPT_PORT, and creating a proxy player for
     * each connection. Adds each player created to accumulator list, which contains the players
     * already signed up. Stops signing up players if a maximum player count is reached.
     * @param serverSocket the server socket to accept clinets with
     * @param players accumulator list containing the players that have signed up, which will be modified with new
     *                signups
     */
    private static void signupPlayersSingleRound(ServerSocket serverSocket, List<Player> players) {

        long endOfSignupTime = System.currentTimeMillis() + SETUP_TIME;

        while (System.currentTimeMillis() < endOfSignupTime && players.size() < MAX_PLAYERS) {

            long currentTime = System.currentTimeMillis();
            long timeLeftMillis = endOfSignupTime - currentTime;

            Callable<Socket> task = () -> serverSocket.accept();
            Optional<Socket> connection = Utils.callWithTimeout(task, timeLeftMillis);

            if(connection.isPresent()) {
                addConnectedPlayerIfSignupComplete(connection.get(), players);
            }
        }
    }

    /**
     * Given a connected client, wait SETUP_NAME_TIME milliseconds for a JSON String representation
     * of the client's name to be sent. If a name is sent, then add the client as a proxy player to
     * the given list of players that are already signed up.
     * @param client the connected client socket
     * @param players a list accumulator of all players that successfully signed up
     */
    private static void addConnectedPlayerIfSignupComplete(Socket client, List<Player> players) {
        Optional<String> playerName = getPlayerName(client);
        if (playerName.isPresent()) {
            try {
                ProxyPlayer proxyPlayer = new ProxyPlayer(client, playerName.get());
                // NOTE: add players to the beginning of the list to keep order
                // (the youngest player is last player to sign up)
                players.add(0, proxyPlayer);
            } catch (IOException | JSONException ignored) {
                // do not kill the server or stop signup if there is a problem
                // with a particular player
            }
        }
        // if player does not provide name, do not create a new ProxyPlayer
    }

    /**
     * Listens on the socket for the Player's name to be sent, which is required for registration.
     * If the client does not send a name in the required time (SETUP_NAME_TIME milliseconds), then
     * an empty optional is returned.
     * @param client the client socket connection
     * @return An optional containing the player's name if the client sent the name in time or an
     *         empty optional if the name could not be parsed or was sent too late
     */
    private static Optional<String> getPlayerName(Socket client) {
        Callable<String> waitForNameAndParse = () -> acceptNameFromPlayer(client);
        return Utils.callWithTimeout(waitForNameAndParse, SETUP_NAME_TIME);
    }

    /**
     * Waits for a name to be sent by the client and then returns the value.
     * @param client the socket connection to the client to wait for the name
     * @return the name given by the client
     * @throws IllegalStateException if the name is invalid. This is okay to do because this method
     * is called inside a future in Utils.callWithTimeout(...) where the ExecutionException is caught.
     */
    private static String acceptNameFromPlayer(Socket client) throws IllegalStateException {
        try {
            JSONTokener tokener = new JSONTokener(client.getInputStream());
            String name = tokener.nextValue().toString();
            if(isNameValid(name)) {
                return name;
            }
        }
        catch(IOException | JSONException e) {
            throw new IllegalStateException("Could not parse name");
        }
        throw new IllegalStateException("Name does not match regex");
    }

    /**
     * Determines if the given name is valid according to the spec.
     * @param name the name to validate
     * @return true if the name is valid, false if not
     */
    private static boolean isNameValid(String name) {
        String nameRegex = "^[a-zA-Z0-9]+$";
        return name.matches(nameRegex) && name.length() >= 1 && name.length() <= 20;
    }

    /**
     * Open a ServerSocket if possible, else shutdown with error message.
     * @param port the port for the socket
     * @return the socket
     */
    private static ServerSocket getServerSocketOrQuit(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Cannot create server socket.");
            System.exit(1);
            return null; //unreachable
        }
    }

    /**
     * Creates a new Referee with the given state and players, runs the game via the Referee, and retrieves the
     * JSON-encoded result of the game.
     * @param initState the initial state to start the game with
     * @param players the players to play the game
     * @return the result of running a game with the given initial state and players
     */
    private static JSONArray runGame(State initState, List<Player> players) {
        Referee referee = new Referee();
        referee.runNewGame(initState, players);
        return JSONConverter.winnersAndKickedToJSON(referee.getWinners(), referee.getKickedPlayers());
    }
}

