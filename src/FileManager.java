import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * FileManager.java
 * Takes a filename and retrieves specified data from it
 * @version 0.1
 * @author Jack Maloney
 *
 * */

public class FileManager {


  private static Scanner createFileScanner(String filename) {
    File file = new File(filename);
    Scanner in = null;
    try {
      in = new Scanner(file);
    } catch (FileNotFoundException e) {
      System.out.println("File does not exist");
      System.exit(0);
    }
    return in;
  }


}
