package Remote.Server;

import Common.*;
import Enumerations.Direction;
import Enumerations.ResponseStatus;
import Remote.ApiOperation;
import Remote.TestSocket;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ProxyPlayerTest {
    private TestSocket sock;
    private ProxyPlayer proxyPlayer;

    @Before
    public void init() {
        this.sock = new TestSocket();
        try {
            this.proxyPlayer = new ProxyPlayer(this.sock, "steve");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(timeout = 2000)
    public void testSetupOnlyCoord() {
        Coordinate testCoord = new Coordinate(1, 1);

        this.sock.fakeInput("\"void\""); // the mocked response to the API call, must be set up beforehand so that
                                               // setup has a response to read after it sends the request
        assertEquals(ResponseStatus.OK, proxyPlayer.setup(Optional.empty(), testCoord));
        String expectedRes;
        try {
            expectedRes = "[\"" + ApiOperation.setup + "\",[false," + JSONConverter.coordinateToJSON(testCoord) + "]]\n";
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedRes, this.sock.readOutput());
    }

    @Test(timeout = 2000)
    public void testSetupWithState() {
        State state = new State();
        Coordinate testCoord = new Coordinate(1, 1);

        this.sock.fakeInput("\"void\"");
        assertEquals(ResponseStatus.OK, this.proxyPlayer.setup(Optional.of(state), testCoord));
        String expectedRes;
        try {
            expectedRes = "[\"" + ApiOperation.setup + "\",[" + JSONConverter.stateToJSON(state) + "," + JSONConverter.coordinateToJSON(testCoord) + "]]\n";
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedRes, this.sock.readOutput());
    }

    @Test
    public void testSetupReceivesNoResponse() {
        Coordinate testCoord = new Coordinate(1, 1);

        Optional<ResponseStatus> response =
                Utils.callWithTimeout(() -> this.proxyPlayer.setup(Optional.empty(), testCoord), 3000);

        assertEquals(Optional.empty(), response);
    }

    @Test(timeout = 2000)
    public void testTakeTurnPass() {
        State testState = new State();

        this.sock.fakeInput("\"PASS\"");
        Action action = this.proxyPlayer.takeTurn(testState);
        assertFalse(action.isMove());

        this.sock.fakeInput("\"void\"\n");
    }

    @Test(timeout = 2000)
    public void testTakeTurnValidTurn() throws JSONException {
        State testState = new State();

        MoveAction moveAction = new MoveAction(0, Direction.RIGHT, 3, new Coordinate(1, 4));

        this.sock.fakeInput(JSONConverter.moveActionToJSON(moveAction).toString());
        Action action = this.proxyPlayer.takeTurn(testState);
        assertTrue(action.isMove());
        MoveAction resultMove = action.getMove();
        assertEquals(0, resultMove.getIndex());
        assertEquals(Direction.RIGHT, resultMove.getDirection());
        assertEquals(3, resultMove.getNumRotations());
        assertEquals(new Coordinate(1, 4), resultMove.getMoveTo());

        this.sock.fakeInput("\"void\"\n");
    }

    @Test
    public void testTakeTurnInvalidTurn() {
        State testState = new State();

        this.sock.fakeInput("[-1, \"RIGHT\", 3, 5]");

        Optional<Action> response =
                Utils.callWithTimeout(() -> this.proxyPlayer.takeTurn(testState), 3000);

        assertEquals(Optional.empty(), response);
    }

    @Test(timeout = 2000)
    public void testWon() {

        this.sock.fakeInput("\"void\"");
        assertEquals(ResponseStatus.OK, proxyPlayer.won(true));
        String expectedRes;
        expectedRes = "[\"" + ApiOperation.win + "\",[true]]\n";
        assertEquals(expectedRes, this.sock.readOutput());

        this.sock.fakeInput("\"void\"");
        assertEquals(ResponseStatus.OK, proxyPlayer.won(false));
        expectedRes = "[\"" + ApiOperation.win + "\",[false]]\n";
        assertEquals(expectedRes, this.sock.readOutput());
    }
}
