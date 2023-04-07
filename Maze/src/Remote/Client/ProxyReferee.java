package Remote.Client;
import Common.Action;
import Common.Coordinate;
import Common.JSONConverter;
import Common.State;
import Players.Player;
import Remote.ApiOperation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

/**
 * Class that facilitate player-referee interactions across a network.
 */
public class ProxyReferee implements Runnable {
    // Note: this is not the JSON representation of the void string
    private static final String VOID_RESPONSE_AS_STRING = "void";
    private static final String PASS_STRING = "PASS";

    /**
     * The output stream sending info to the server
     */
    private final PrintStream out;
    /**
     * The input reader that reads info from the server
     */
    private final JSONTokener inputTokener;

    private final Player player;
    private boolean gameOver;

    /**
     * Creates a proxy referee to communicate with the real referee across the network for
     * a player
     * @param sock the socket of the real referee
     * @param player the player
     * @throws IOException when the input or output fails
     * @throws JSONException if the JSONTokener cannot be instantiated
     */
    public ProxyReferee(Socket sock, Player player) throws IOException, JSONException {
        this.out = new PrintStream(sock.getOutputStream());
        this.inputTokener = new JSONTokener(sock.getInputStream());
        this.player = player;
        this.gameOver = false;
    }

    /**
     * Facilitates communication between the referee and this player.
     *  - Listens for a request from the referee
     *  - Sends info to the player
     *  - Sends player's response back to the referee
     *  If the server's command could not be parsed, the method will stop. The method will also
     *  terminate if the game is complete.
     */
    @Override
    public void run() {
        while (!this.gameOver) {
            try {
                JSONArray command = (JSONArray) this.inputTokener.nextValue();
                System.out.println("RECEIVED: " + command);
                this.execute(command);
            } catch (JSONException | IOException e) {
                break;
            }
        }
    }

    /**
     * Executes a valid command on the player
     * @param command the command to be performed
     * @throws JSONException if the command is not valid and well-formed
     */
    private void execute(JSONArray command) throws JSONException, IOException {
        ApiOperation method = ApiOperation.fromString(command.getString(0));
        JSONArray args = command.getJSONArray(1);
        switch (method) {
            case take_turn:
                this.executeTakeTurn(args);
                return;
            case win:
                this.executeWin(args);
                return;
            case setup:
                this.executeSetup(args);
        }
    }

    /**
     * Calls setup on the player and outputs its response to the referee
     * @param args a JSONArray of the arguments of the method call
     * @throws JSONException if the arguments aren't well-formed or valid JSON
     */
    private void executeSetup(JSONArray args) throws JSONException {
        Optional<State> maybeState = Optional.empty();
        try{
            State s = JSONConverter.stateFromJSON(args.getJSONObject(0));
            maybeState = Optional.of(s);
        } catch (JSONException ignore) {}

        Coordinate destination = JSONConverter.coordinateFromJSON(args.getJSONObject(1));

        this.player.setup(maybeState, destination);
        sendToRemoteProxy(JSONObject.quote(VOID_RESPONSE_AS_STRING));
    }

    /**
     * Calls win on the player and outputs its response to the referee
     * @param args a JSONArray of the arguments of the method call
     * @throws JSONException if the arguments aren't well-formed or valid JSON
     */
    private void executeWin(JSONArray args) throws JSONException {
        boolean didWin = args.getBoolean(0);
        this.player.won(didWin);
        this.gameOver = true;
        sendToRemoteProxy(JSONObject.quote(VOID_RESPONSE_AS_STRING));
    }

    /**
     * Calls take-turn on the player and outputs its response to the referee
     * @param args a JSONArray of the arguments of the method call
     * @throws JSONException if the arguments aren't well-formed or valid JSON
     */
    private void executeTakeTurn(JSONArray args) throws JSONException {
        State state = JSONConverter.stateFromJSON(args.getJSONObject(0));
        Action a = this.player.takeTurn(state);
        if (a.isMove()) {
            sendToRemoteProxy(JSONConverter.moveActionToJSON(a.getMove()).toString());
        } else {
            sendToRemoteProxy(JSONObject.quote(PASS_STRING));
        }
    }

    /**
     * Sends the given JSONArray over the network to the TCP-connected Server. Flushes the output since PrintStream does
     * not flush automatically by default.
     * @param toSend the message to send to the Server
     */
    private void sendToRemoteProxy(String toSend) {
        System.out.println(toSend);
        this.out.print(toSend);
        this.out.flush();
    }
}
