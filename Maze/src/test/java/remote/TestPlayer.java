package remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.Posn;

public class TestPlayer {

  Socket connection;
  InputStream inputStream;
  OutputStream outputStream;
  remote.Player testPlayer;

  @BeforeEach
  public void setUp(){
    connection = Mockito.mock(Socket.class);
    inputStream = new ByteArrayInputStream("\"void\"".getBytes());//Mockito.mock(InputStream.class);
    outputStream = Mockito.mock(OutputStream.class);
    try {
      Mockito.when(connection.getInputStream()).thenReturn(inputStream);
      Mockito.when(connection.getOutputStream()).thenReturn(outputStream);
    }
    catch (Exception e) {
      fail();
    }
    testPlayer = new Player(connection, "testPlayer");
  }

  @Test
  public void testRemotePlayerName() {
    assertEquals(testPlayer.name(), "testPlayer");
  }

  @Test
  public void testRemotePlayerSetup() {
    try {
      assertEquals(testPlayer.setup(Optional.empty(), new Posn(1, 1)), "void");
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testRemotePlayerWin(){
    try{
      assertEquals(testPlayer.win(true), "void");
    } catch (Exception e) {

      fail();
    }
  }

}
