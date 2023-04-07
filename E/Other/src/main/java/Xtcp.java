import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

/*
 1. Permits a single client to connect at the port given as a command-line argument
 2. Consumes a series of JSON objects from the client
 3. Converts each JSON object to the corresponding special character
 4. Once the client-in connection is closed, writes a JSON array of special character Strings to the
    client output
 5. Closes client connection and shuts down
*/

public class Xtcp {

  public static void main(String[] args) {

    try {
      int port = Integer.parseInt(args[0]);
      Socket client = acceptClient(port);

      PrintStream output = new PrintStream(client.getOutputStream());
      InputStream input = client.getInputStream();

      List<SpecialChar> characters = parseInputStream(input);

      String out = getOutputAsJsonArray(characters);
      output.println(out);

      client.close();

    } catch (IOException e) {
      System.out.println("Something went wrong. Server shut down.");
    }
  }



  private static Socket acceptClient(int port) throws IOException {
    ServerSocket serverSocket = new ServerSocket(port);
    return serverSocket.accept();
  }



  private static List<SpecialChar> parseInputStream(InputStream input) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(Feature.AUTO_CLOSE_SOURCE, false);

    JsonFactory factory = mapper.getJsonFactory();
    JsonParser parser = factory.createJsonParser(input);

    ArrayList<SpecialChar> charAccumulator = new ArrayList<>();


    JsonToken token = parser.nextToken();
    while(token == JsonToken.START_OBJECT) {

      SpecialChar specialChar = mapper.readValue(parser, SpecialChar.class);
      charAccumulator.add(specialChar);

      token = parser.nextToken();
    }
    return charAccumulator;
  }



  private static String getOutputAsJsonArray(List<SpecialChar> characters) throws IOException {
    ArrayList<String> converted = new ArrayList<>();
    for(SpecialChar special : characters) {
      converted.add(special.toString());
    }
    return new ObjectMapper().writeValueAsString(converted);
  }

}
