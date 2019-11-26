import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager.java
 * Takes a filename and retrieves specified data from it or saves player data to a file
 *
 * @author Jack Maloney
 * @version 0.3
 */
// make class fully static please bb <3
// and make a savePlayerFile(Player, Board) that saves the game state, suga' plum ;)
// needs a method to update the leaderboard
// needs a method to get a leaderboard from a filename
// player file needs to store filepath to the level and store the highest level achieved variable
public class FileManager {

    public static class FileReading {

        // Forgive me for I have sinned.
        private static Drawable[][] boardDrawables;
        private static ArrayList<Movable> movables;
        private static ArrayList<Interactable> interactables;

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
         * Reads and generates the common section of the player and map files
         * @param in Scanner of file to read
         * @param divider The keyword to stop reading the file
         */
        private static void readAnyFile(Scanner in, String divider) {
            int boardX = in.nextInt();
            int boardY = in.nextInt();
            boardDrawables = new Drawable[boardX][boardY];
            movables = new ArrayList<>();
            interactables = new ArrayList<>();
            in.nextLine();
            for (int i = 0; i < boardY; i++) {
                String currentTileLine = in.nextLine();
                for (int j = 0; j < (boardX); j++) {
                    String current = "" + currentTileLine.charAt(j);
                    switch (current) {
                        case "#":
                            System.out.print("#");
                            boardDrawables[j][i] = new StaticEntity(j, i, "placeholder.png", 2);
                            break;
                        case ".":
                            System.out.print(".");
                            boardDrawables[j][i] = new StaticEntity(j, i, "placeholder.png", 0);
                            break;
                        case "F":
                            System.out.print("F");
                            boardDrawables[j][i] = new StaticEntity(j, i, "placeholder.png", 1);
                            break;
                        case "W":
                            System.out.print("W");
                            boardDrawables[j][i] = new StaticEntity(j, i, "placeholder.png", 1);
                            break;
                        case "T":
                            System.out.print("T");
                            break;
                        case "D":
                            System.out.print("D");
                            break;
                        case "G":
                            System.out.print("G");
                            boardDrawables[j][i] = new Goal();
                            break;
                        default:
                            System.out.print("?");
                            boardDrawables[j][i] = new StaticEntity(j, i, "placeholder.png", 2);
                    }
                }
                System.out.println("");
            }
            String currentLine = in.nextLine();
            while (!currentLine.startsWith(divider)) {
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
                        System.out.println("It's a: " + itemType);
                        switch (itemType) {
                            case "TOKEN":
                                int tokenValue = line.nextInt();
                                System.out.println("It's worth: " + tokenValue);
                                interactables.add(new Token());
                                break;
                            case "FLIPPER":
                                interactables.add(new Shoe());
                                break;
                            case "BOOTS":
                                interactables.add(new Shoe());
                                break;
                            default:
                                System.out.println("Unrecognised!");
                                break;
                        }
                        break;
                    case "DOOR":
                        String doorType = line.next();
                        if (doorType.equals("TOKEN")) {
                            int tokensRequired = line.nextInt();
                            System.out.println("It's a door that uses " + tokensRequired + " tokens!");
                            boardDrawables[posX][posY] = new TokenDoor();
                        } else {
                            String doorColour = line.next();
                            System.out.println("It's a " + doorColour.toLowerCase() + " door!");
                            boardDrawables[posX][posY] = new Coloured();
                        }
                        break;
                    case "TELE":
                        int pairValue = line.nextInt();
                        System.out.println("It's a teleporter part of pair " + pairValue);
                        boardDrawables[posX][posY] = new Teleporter();
                        break;
                    case "ENEMY":
                        String enemyType = line.next();
                        switch (enemyType) {
                            case "STRAIGHT":
                                String direction = line.next();
                                System.out.println("Straight type enemy starting: " + direction);
                                movables.add(new DumbEnemy(posX, posY, "placeholder.png", 1));
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
        }

        /**
         * Loads a new map from a file
         * @param filepath location of file to read from
         * @param board board to be populated
         */
        public static void readMapFile(String filepath, Board board) {
            Scanner in = createFileScanner(filepath);
            readAnyFile(in, "LEADERBOARD");
            String currentLine = in.nextLine();

            for (int i = 0; i < 3; i++) {
                String[] player = currentLine.split(",");
                String playerName = player[0];
                int playerTime = Integer.parseInt(player[1]);
                System.out.println(playerName + " completed the level in: " + playerTime);
                if (i < 2) {
                    currentLine = in.nextLine();
                }
            }

            board = new Board(boardDrawables, movables, interactables);
        }

        /**
         * Loads a map and player's stats from a file
         * @param filepath location of file to read
         * @param player player to edit
         * @param board board to edit
         */
        public static void readPlayerFile(String filepath, Player player, Board board) { // please make it readPlayerFile(String filepath, Player player, Board board) thanks x
            Scanner in = createFileScanner(filepath);
            readAnyFile(in, "CURRENTTIME");
            String currentLine= in.nextLine();
            int currentTime = Integer.parseInt(currentLine.split(",")[1]);
            int playerLevel = Integer.parseInt(in.nextLine().split(",")[1]);
            System.out.println(in.nextLine());

            while (in.hasNextLine()) {
                Scanner line = new Scanner(in.nextLine()).useDelimiter(",");
                String itemType = line.next();
                switch (itemType) {
                    case "FLIPPER":
                        player.addFlippers();
                        break;
                    case "BOOTS":
                        player.addBoots();
                        break;
                    case "TOKEN":
                        player.addToken(line.nextInt());
                        break;
                    default:
                        System.out.println("Unrecognized!");
                }

            }
            board = new Board(boardDrawables, movables, interactables);
        }
    }

    public static class FileWriting {
        public static void savePlayerFile(Player player, Board board) {
            BufferedWriter writer = null;
            try {
                File file = new File("testsave.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(fw);
                writer = new BufferedWriter(fw);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer!=null){
                        writer.close();
                    }
                } catch (Exception ex){
                    System.out.println(ex);
                }
            }

        }
    }
}

