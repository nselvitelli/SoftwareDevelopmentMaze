package Remote.Server;
import Common.*;
import Enumerations.*;
import Players.*;
import Remote.ApiOperation;
import org.json.*;

import java.io.*;
import java.net.Socket;
import java.util.Optional;


/**
 * A proxy which abstracts the network layer for Players. Each ProxyPlayer runs on a referee server and is connected to
 * a single ProxyReferee on a player client, forwarding API calls from the actual referee to the proxy referee and
 * passing the responses back to the referee.
 */
public class ProxyPlayer implements Player {
    private final PrintStream out;
    private final JSONTokener inputTokener;
    private final String name;

    // Note: this is not the JSON representation of the void string
    private static final String VOID_RESPONSE_AS_STRING = "void";

    public ProxyPlayer(Socket sock, String name) throws IOException, JSONException {
        this.out = new PrintStream(sock.getOutputStream());
        this.inputTokener = new JSONTokener(sock.getInputStream());
        this.name = name;
    }

    @Override
    public Board proposeBoard(int rows, int columns) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
        JSONArray arguments = ProxyPlayer.formatSetupJSONArguments(ps, destination);
        JSONArray toSend = this.createOutputJSON(ApiOperation.setup.toString(), arguments);
        this.sendToRemoteProxy(toSend);
        return this.getConfirmationFromRemoteProxy();
    }

    /**
     * Create JSONArray which matches network protocol for setup API call by encoding the given arguments.
     * @param ps the state to encode or empty if no state
     * @param destination the destination coordinate to encode
     * @return the encoded arguments
     */
    private static JSONArray formatSetupJSONArguments(Optional<State> ps, Coordinate destination) {
        JSONArray arguments = new JSONArray();
        try {
            if (ps.isPresent()) {
                arguments.put(ProxyPlayer.getJSONState(ps.get()));
            } else {
                arguments.put(false);
            }
            arguments.put(JSONConverter.coordinateToJSON(destination));
            return arguments;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Action takeTurn(State ms) {
        JSONArray arguments = new JSONArray();
        arguments.put(ProxyPlayer.getJSONState(ms));
        JSONArray toSend = this.createOutputJSON(ApiOperation.take_turn.toString(), arguments);
        this.sendToRemoteProxy(toSend);
        return this.getActionFromRemoteProxy();
    }

    /**
     * Encode a JSONState. Escalates exceptions since a valid State should always encode correctly into JSON.
     * @param ms the State to encode
     * @return the encoded state
     */
    private static JSONObject getJSONState(State ms) {
        try{
            return JSONConverter.stateToJSON(ms);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseStatus won(Boolean w) {
        JSONArray arguments = new JSONArray();
        arguments.put(w);
        JSONArray toSend = this.createOutputJSON(ApiOperation.win.toString(), arguments);
        this.sendToRemoteProxy(toSend);
        return this.getConfirmationFromRemoteProxy();
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Waits for a "void" response from the client. If the client responds with the correct JSON
     * syntax, then the JsonTokener.nextValue will return a String object of the parsed JSONString.
     * This means that the method does not have to worry about extra quotes.
     * @return the ResponseStatus of waiting for a "void" confirmation
     */
    private ResponseStatus getConfirmationFromRemoteProxy() {
        try {
            String response = this.inputTokener.nextValue().toString();
            if(VOID_RESPONSE_AS_STRING.equals(response)) {
                return ResponseStatus.OK;
            }
        }
        catch(JSONException | ClassCastException e) {
            throw new IllegalStateException("Could not parse name");
        }
        return ResponseStatus.ERROR;
    }

    /**
     * This method parses the next value in the input from the client as an action.
     * This method only returns a well-formed action and can run infinitely if no well-formed Action
     * is given by the input.
     * @return the parsed action
     * @throws IllegalStateException if the action response is malformed
     */
    private Action getActionFromRemoteProxy() {
        try {
            Object response = this.inputTokener.nextValue();

            String passAction = "PASS";
            if(passAction.equals(response.toString())) {
                return new Pass();
            }
            else {
                JSONArray action = (JSONArray) response;
                return JSONConverter.moveActionFromJSON(action);
            }
        }
        catch (JSONException e) {
            throw new IllegalStateException("Unable to parse response");
        }
    }

    /**
     * Format arguments and API call name to send on network.
     */
    private JSONArray createOutputJSON(String apiName, JSONArray array) {
        JSONArray output = new JSONArray();
        output.put(apiName);
        output.put(array);
        return output;
    }

    /**
     * Sends the given JSONArray over the network to the TCP-connected Player. Flushes the output since PrintStream does
     * not flush automatically by default.
     * @param toSend the JSON representation of [APIOperation, [<arguments>]] to send following the remote protocol
     */
    private void sendToRemoteProxy(JSONArray toSend) {
        this.out.print(toSend.toString());
        this.out.flush();
    }
}
