import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * FileManager.java
 * Takes a filename and retrieves specified data from it
 *
 * @author Jack Maloney
 * @version 0.1
 */

public class FileManager {

    public static void main(String[] args) {
        readMapFile(createFileScanner("assets/Level Example.txt"));
    }

    /**
     * Validates filepath given and creates a scanner if valid
     * @param filename Filepath of file to use
     * @return Scanner of file
     */
    private static Scanner createFileScanner(String filename) {
        File file = new File(filename);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        in.useDelimiter(",");
        return in;
    }

    /**
     * Generates a board from a given scanner
     * @param in Scanner of file containing board instructions
     */
    private static void readMapFile(Scanner in) {
        int boardX = in.nextInt();
        int boardY = in.nextInt();
        in.nextLine();
        for (int i = 0; i < boardY; i++) {
            String currentTileLine = in.nextLine();
            for (int j = 0; j < (boardX); j++) {
                String current = "" + currentTileLine.charAt(j);
                switch (current) {
                    case "#":
                        System.out.print("#");
                        break;
                    case ".":
                        System.out.print(".");
                        break;
                    case "F":
                        System.out.print("F");
                        break;
                    case "W":
                        System.out.print("W");
                        break;
                    case "T":
                        System.out.print("T");
                        break;
                    case "D":
                        System.out.print("D");
                        break;
                    case "G":
                        System.out.print("G");
                        break;
                    default:
                        System.out.print("?");
                }
            }
            System.out.println("");
        }
        String currentLine = in.nextLine();
        while (!currentLine.equals("LEADERBOARD")) {
            Scanner line = new Scanner(currentLine).useDelimiter(",");
            int posX = line.nextInt();
            int posY = line.nextInt();
            System.out.println(posX + ", " + posY);
            String keyword = line.next();
            switch (keyword) {
                case "START":
                    System.out.println("START");
                    break;
                case "ITEM":
                    String itemType = line.next();
                    if (itemType.equals("TOKEN")) {
                        int tokenValue = line.nextInt();
                        System.out.println("It's a token worth: " + tokenValue);
                    } else {
                        System.out.println("It's a: " + itemType);
                    }
                    break;
                case "DOOR":
                    String doorType = line.next();
                    if (doorType.equals("TOKEN")) {
                        int tokensRequired = line.nextInt();
                        System.out.println("It's a door that uses " + tokensRequired + " tokens!");
                    } else {
                        String doorColour = line.next();
                        System.out.println("It's a " + doorColour.toLowerCase() + "door!");
                    }
                    break;
                case "TELE":
                    int pairValue = line.nextInt();
                    System.out.println("It's a teleporter part of pair " + pairValue);
                    break;
                case "ENEMY":
                    String enemyType = line.next();
                    switch (enemyType) {
                        case "STRAIGHT":
                            String direction = line.next();
                          System.out.println("Straight type enemy starting: " + direction);
                            break;
                        default:
                            System.out.print("I haven't added this enemy type to the filereader!");
                    }
                    break;
                default:
                    System.out.println("Not a recognised keyword!");
            }
            currentLine = in.nextLine();
            line.close();
        }
        currentLine = in.nextLine();

        for (int i = 0; i < 3; i++) {
            String[] player = currentLine.split(",");
            String playerName = player[0];
            String playerTime = player[1];
            System.out.println(playerName + " completed the level in: " + playerTime);
            if (i < 2) {
                currentLine = in.nextLine();
            }
        }
    }
}
