package Integration;

import Common.Utils;
import Players.AIPlayer;
import Players.EuclidStrategy;
import Players.RiemannStrategy;
import Remote.Client.PlayerClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class launches clients on different threads to join the given port and optional
 * IP address (default localhost). Player configurations (BadPlayerSpec2 in JSON) are accepted via
 * STDIN. For each configuration, a new client is launched.
 */
public class ClientsHarness {


    private static final String LOCAL_IP_ADDRESS = "127.0.0.1";
    private static final int CLIENT_SIGNUP_DELAY_MILLIS = 3000;

    /**
     * Main entry point into this Java script. This script is given a port and optionally an
     * address along with a BadPlayer2Spec that is passed in through the STDIN. Each player in the
     * BadPlayer2Spec will be converted into an AIPlayer. Each AIPlayer is then launched in a client
     * that runs on an independent thread. Nothing is returned from this script.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        assertCorrectNumberOfArguments(args);
        int port = Utils.getPort(args[0]);
        String address = getAddress(args);

        listenForClientsOnInputAndConnectToServer(System.in, address, port);
    }

    /**
     * This method collects a BadPlayer2Spec from the given input stream as a list of AIPlayers.
     * It then launches each player in a client on an independent thread to connect to the server at
     * the given address and port.
     * @param inputStream the input stream to parse input on
     * @param address the server's ip address
     * @param port the server's port
     */
    public static void listenForClientsOnInputAndConnectToServer(InputStream inputStream, String address, int port) {
        try {
            List<AIPlayer> players = parsePlayers(inputStream);
            launchPlayers(players, address, port);
        }
        catch (JSONException | InterruptedException e) {
            System.out.println("Malformed input");
            System.exit(1);
        }
    }

    /**
     * For each player in the given list, a client is launched to connect to the given
     * address and port.
     * @param players the players to launch
     * @param address the ip address to connect to
     * @param port the port to connect to
     * @throws InterruptedException if the delay is interrupted
     */
    private static void launchPlayers(List<AIPlayer> players, String address, int port) throws InterruptedException {
        for (AIPlayer player : players) {
            PlayerClient.connectPlayerToServer(address, port, player);
            Thread.sleep(CLIENT_SIGNUP_DELAY_MILLIS);
        }
    }

    /**
     * Parses the BadPlayerSpec2 into a list of AIPlayers
     * @param inputStream the input to parse
     * @return the players
     * @throws JSONException if the input is malformed
     */
    private static List<AIPlayer> parsePlayers(InputStream inputStream) throws JSONException {

        JSONTokener tokener = new JSONTokener(inputStream);

        JSONArray badPlayerSpec = (JSONArray) tokener.nextValue();

        List<AIPlayer> players = new ArrayList<>();

        for(int i=0; i< badPlayerSpec.length(); i++) {
            players.add(parsePlayer((JSONArray) badPlayerSpec.get(i)));
        }

        return players;
    }

    /**
     * Parses a single element of the BadPlayerSpec2 into an AIPlayer.
     * @param player the json array of data to turn into an AIPlayer
     * @return the player
     * @throws JSONException if the given input is malformed
     */
    private static AIPlayer parsePlayer(JSONArray player) throws JSONException {

        String name = player.getString(0);
        String strategy = player.getString(1);
        String bad = "none";
        int count = 1;
        boolean loop = false;
        if (player.length() > 2) {
            bad = player.getString(2);
        }
        if (player.length() > 3) {
            count = player.getInt(3);
            loop = true;
        }

        if (strategy.equals("Euclid")) {
            return new BrokenPlayer(new EuclidStrategy(), name, bad, count, loop);
        } else if (strategy.equals("Riemann")) {
            return new BrokenPlayer(new RiemannStrategy(), name, bad, count, loop);
        } else{
            throw new JSONException("Invalid strategy" + strategy);
        }
    }

    /**
     * Ensures that there are the correct number of command line arguments given.
     * @param args the command line arguments
     */
    private static void assertCorrectNumberOfArguments(String[] args) {
        int argLen = args.length;
        if(argLen < 1 || argLen > 2) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }
    }

    /**
     * Determines the ip address to use to connect the clients to. If there is no given ip address,
     * then use the Local Host address.
     * @param args the command line arguments to parse over
     * @return the address for the clients to connect to
     */
    private static String getAddress(String[] args) {
        if(args.length != 2) {
            return LOCAL_IP_ADDRESS;
        }
        return args[1];
    }
}
