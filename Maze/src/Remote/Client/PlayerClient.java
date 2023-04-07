package Remote.Client;

import Players.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Static class used to connect Players to a server and create a proxyRef. Creation of Player agents is left to specific
 * system implementations.
 */
public class PlayerClient {
    /**
     * Creates a proxy referee with the given server info and starts it on a new thread.
     * @param address the server address
     * @param port the server port
     * @param player the player to play the game
     */
    public static void connectPlayerToServer(String address, int port, Player player) {
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        signupPlayerAndPlayGame(socketAddress, player);
    }

    /**
     * Attempts to connect to the given address, signup the given player, and create a ProxyReferee to interface the
     * player with the game.
     * @param address the address to connect to
     * @param player the player to connect
     */
    private static void signupPlayerAndPlayGame(InetSocketAddress address, Player player) {
        while(true) {
            try {
                Socket sock = new Socket();
                tryConnectAndSignup(sock, address, player.getName());
                launchProxyRefereeForPlayer(sock, player);
                return;
            } catch (IOException | JSONException ignored) {}
        }
    }

    /**
     * Registers a player with the server by connecting to the given socket and address, then sending the player
     * name to the server.
     * @param sock the socket to connect on
     * @param address the address of the server
     * @param playerName the name of the player to connect
     * @throws IOException
     */
    private static void tryConnectAndSignup(Socket sock, InetSocketAddress address, String playerName) throws IOException {
        sock.connect(address);
        sendPlayerName(sock, playerName);
    }

    /**
     * Given a socket connection and a player, create a ProxyReferee to allow for communication
     * between the client player and the server and launch the Referee in a separate thread.
     * @param sock the client's socket connection to the server
     * @param player the player
     * @throws JSONException if the Proxy Referee cannot be instantiated
     * @throws IOException if the Proxy Referee cannot be instantiated
     */
    private static void launchProxyRefereeForPlayer(Socket sock, Player player) throws JSONException, IOException {
        ProxyReferee proxyRef = new ProxyReferee(sock, player);
        Thread thread = new Thread(proxyRef);
        thread.start();
    }

    /**
     * Sends the given player's name as a JSON String to the given socket to the server.
     * @param server the socket to send on
     * @param playerName the player's name
     * @throws IOException if an error occurs with retrieving the server's output stream
     */
    private static void sendPlayerName(Socket server, String playerName) throws IOException {
        PrintStream serverPrintStream = new PrintStream(server.getOutputStream());
        String nameJSON = JSONObject.quote(playerName);
        serverPrintStream.println(nameJSON);
    }

}
