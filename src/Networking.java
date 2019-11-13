import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.server.ExportException;


/**
 * Networking.java Handles all networking interaction and decryption (Message of the Day).
 *
 * @author Jack Maloney
 * @version 0.9
 */

public class Networking {

  /**
   * Retrieves the message of the day.
   *
   * @return Message of the Day
   * @throws IOException Data cannot be retrieved from the URL
   */
  public static String getMessage() throws IOException {
    final String PUZZLE_ADDRESS = "http://cswebcat.swan.ac.uk/puzzle";
    final String QUERY_ADDRESS = "http://cswebcat.swan.ac.uk/message?solution=";
    String puzzle = getRequest(PUZZLE_ADDRESS);
    return getRequest(QUERY_ADDRESS + decodePuzzle(puzzle));
  }

  /**
   * Retrieves the contents of the body of a URL address.
   *
   * @param address The URL to access
   * @return String containing the contents of the body
   * @throws IOException Data cannot be retrieved from the URL
   */
  private static String getRequest(String address) throws IOException {
    URL url = new URL(address);

    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      return read(connection.getInputStream());
    } catch (UnknownHostException e) {
      return "Cannot connect to specified URL!";
    }

  }

  /**
   * Reads an InputStream and converts it to a String.
   *
   * @param is InputStream to convert
   * @return String of the contents of the InputStream
   * @throws IOException Data cannot be retrieved from the InputStream
   */
  private static String read(InputStream is) throws IOException {
    BufferedReader in;
    String inputLine;
    try {
      in = new BufferedReader(new InputStreamReader(is));
      inputLine = in.readLine();
      in.close();
      return inputLine;
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Decodes the given puzzle and returns the solution.
   *
   * @param puzzle The puzzle line to be decoded
   * @return The solution to the puzzle
   */
  private static String decodePuzzle(String puzzle) {
    StringBuilder query = new StringBuilder(puzzle);
    // For each character convert it to ascii, increment if it's index is even else decrement
    for (int i = 0; i < query.length(); i++) {
      int currentASCII = query.charAt(i);
      if (i % 2 == 0) {
        if (currentASCII == 90) {
          currentASCII = 65;
        } else {
          currentASCII++;
        }
      } else {
        if (currentASCII == 65) {
          currentASCII = 90;
        } else {
          currentASCII--;
        }
      }
      query.setCharAt(i, (char) currentASCII);
    }
    return query.toString();
  }
}









