package Remote.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.concurrent.Callable;

import Common.*;
import Enumerations.*;
import Enumerations.Shape;
import Players.*;
import Remote.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProxyRefTest {

    private TestSocket sock;
    private DumbPlayer player;
    private ProxyReferee proxyRef;
    private State state;

    @Before
    public void init() {
        this.sock = new TestSocket();
        try {
            this.player = new DumbPlayer(new RiemannStrategy(), "Fred", Color.RED);
            this.proxyRef = new ProxyReferee(this.sock, this.player);
            this.sock.getInputStream();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void setup() {
        Utils.setAvailableGems();
        Tile[][] tiles = new Tile[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i % 2 == 1) {
                    tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.CROSS, Orientation.DEGREE0);
                } else {
                    tiles[i][j] = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
                }
            }
        }


    /*

    BOARD LOOKS LIKE:

    - - - - - - -
    + + + + + + +
    - - - - - - -
    + + + + + + +
    - - - - - - -
    + + + + + + +
    - - - - - - -

     */

        Tile initialSpare = new Tile(Gem.zircon, Gem.zircon, Shape.LINE, Orientation.DEGREE90);
        Board board = new Board(tiles);
        this.state = new State(board, initialSpare);
        this.state.addPlayer(Color.RED, new Coordinate(1, 3), new Coordinate(3, 3));
    }


    @Test
    public void testSetup1() {
        this.setup();
        JSONArray command = new JSONArray();
        command.put("setup");
        JSONArray args = new JSONArray();
        Coordinate c = new Coordinate(3, 3);
        try{
            args.put(JSONConverter.stateToJSON(this.state));
            args.put(JSONConverter.coordinateToJSON(c));
        } catch (JSONException ignore) {}
        command.put(args);

        this.sock.fakeInput(command.toString());
        Utils.callWithTimeout(() -> {this.proxyRef.run(); return 0;}, 10000);
        assertEquals("\"void\"\n", this.sock.readOutput());
        assertEquals(this.player.getDestination(), c);
    }

    @Test
    public void testSetup2() {
        this.setup();
        JSONArray command = new JSONArray();
        command.put("setup");
        JSONArray args = new JSONArray();
        Coordinate c = new Coordinate(3, 3);
        try{
            args.put(false);
            args.put(JSONConverter.coordinateToJSON(c));
        } catch (JSONException ignore) {}
        command.put(args);

        this.sock.fakeInput(command.toString());
        Utils.callWithTimeout(() -> {this.proxyRef.run(); return 0;}, 10000);
        assertEquals("\"void\"\n", this.sock.readOutput());
        assertEquals(this.player.getDestination(), c);
    }

    @Test
    public void testTakeTurn() {
        this.setup();
        this.player.setup(Optional.empty(), new Coordinate(3, 3));
        JSONArray command = new JSONArray();
        command.put("take-turn");
        JSONArray args = new JSONArray();
        try{
            args.put(JSONConverter.stateToJSON(this.state));
        } catch (JSONException ignore) {}
        command.put(args);

        this.sock.fakeInput(command.toString());
        Utils.callWithTimeout(() -> {this.proxyRef.run(); return 0;}, 10000);
        assertEquals("[2,\"LEFT\",90,{\"column#\":0,\"row#\":1}]\n", this.sock.readOutput());
    }

    @Test
    public void testWin() {
        this.setup();
        this.player.setup(Optional.empty(), new Coordinate(3, 3));
        JSONArray command = new JSONArray();
        command.put("win");
        JSONArray args = new JSONArray();
        args.put(true);
        command.put(args);

        this.sock.fakeInput(command.toString());
        Utils.callWithTimeout(() -> {this.proxyRef.run(); return 0;}, 10000);
        assertEquals("\"void\"\n", this.sock.readOutput());
    }


    public class DumbPlayer extends AIPlayer{

        public DumbPlayer(IStrategy strategy, String name, Color color) {
            super(strategy, name, color);
        }

        public Coordinate getDestination() {
            return this.destination;
        }

        public State getState() {
            return this.playerState;
        }


    }


}
