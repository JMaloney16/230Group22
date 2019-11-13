import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Networking.java
 * Handles all networking interaction (Message of the Day)
 * @version 0.1
 * @author Jack Maloney
 */

public class Networking {

  public static String getMessage() throws IOException {
    URL url = new URL("http://cswebcat.swan.ac.uk/puzzle");

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    return read(connection.getInputStream());

  }

  private static String read(InputStream is) throws IOException {
    BufferedReader in;
    String inputLine;
    try {
      in = new BufferedReader(new InputStreamReader(is));
      inputLine = in.readLine();
      in.close();
      return inputLine;
    } catch(IOException e) {
      throw e;
    }
  }
}
