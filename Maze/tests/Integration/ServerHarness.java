package Integration;

import Common.JSONConverter;
import Common.State;
import Common.Utils;
import Remote.Server.RefereeServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.Optional;

/**
 * Given a port to host as a command line parameter and a RefereeState from the STDIN, this script
 * starts a server to allow clients to connect to. When the server is done, the result of the game is
 * printed.
 */
public class ServerHarness {
    /**
     * Main entry point into this Java script. This script is given a port and optionally an
     * address along with a RefereeState that is passed in through the STDIN. A RefereeServer is created and passed
     * the given state, and then run until completion. Prints the resulting winners and losers once the game is
     * complete.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        assertCorrectNumArguments(args);

        int port = Utils.getPort(args[0]);

        State state = getState(System.in);

        JSONArray gameResult = RefereeServer.runServer(port, state);

        System.out.println(gameResult);
    }

    /**
     * Parses the given input for a RefereeState object
     * @param input the input stream to parse over
     * @return the state expected in the input
     */
    private static State getState(InputStream input) {
        try {
            JSONTokener tokener = new JSONTokener(input);

            JSONObject stateRaw = (JSONObject) tokener.nextValue();

            return JSONConverter.stateFromJSON(stateRaw);
        } catch (JSONException e) {
            System.out.println("Failed to parse JSON from STDIN.");
            System.exit(1);
        }
        return null; //unreachable
    }

    /**
     * Asserts that the given array of strings has only one argument.
     * @param args the command line arguments passed into main
     */
    private static void assertCorrectNumArguments(String[] args) {
        if(args.length != 1) {
            System.out.println("Incorrect number of arguments");
            System.exit(1);
        }
    }
}
