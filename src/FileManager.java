import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager.java Takes a filename and retrieves specified data from it or
 * saves player data to a file
 *
 * @author Jack Maloney
 * @version 1
 */

public class FileManager {
	public static class FileReading {
		private static Drawable[][] boardDrawables;
		private static ArrayList<Movable> movables;
		private static ArrayList<Interactable> interactables;
		private static String dividerLine;
		private static ArrayList<String> leaderboard = new ArrayList<String>();

		/**
		 * Loads a new map from a file
		 *
		 * @param filepath location of file to read from
		 * @param board    board to be populated
		 */
		public static void readMapFile(String filepath, Board board, Player player) {
			Scanner in = createFileScanner(filepath);
			leaderboard.clear();
			readAnyFile(in, "LEVEL", player);
			// First line after readAnyFile is done is LEVEL
			int mapLevel = Integer.parseInt(dividerLine.split(",")[1]);
			player.setCurrentLevel(mapLevel);

			in.close();
			board.setNewBoard(boardDrawables, movables, interactables);
			board.setLevelNumber(mapLevel);
		}

		/**
		 * Loads a map and player's stats from a file
		 *
		 * @param filepath location of file to read
		 * @param player   player to edit
		 * @param board    board to edit
		 */
		public static void readPlayerFile(String filepath, Player player, Board board) {
			Scanner in = createFileScanner(filepath);
//			String playerName = filepath.substring(0, filepath.length() - 4);
			readAnyFile(in, "CURRENTTIME", player);

			getPlayerDetails(player, in, dividerLine);

			board.setNewBoard(boardDrawables, movables, interactables);
			in.close();
		}

		/**
		 * Loads a player's stats from a file
		 *
		 * @param filepath location of file to read
		 * @param player   player to edit
		 * @deprecated
		 */
		public static void readPlayerFile(String filepath, Player player) {
			Scanner in = createFileScanner(filepath);
			String currentLine = in.nextLine();

			while (!currentLine.startsWith("CURRENTTIME")) {
				currentLine = in.nextLine();
			}
			getPlayerDetails(player, in, currentLine);
			in.close();
		}

		/**
		 * Reads a map file and returns a list of the top 3 players and their times
		 *
		 * @param filepath Location of the map file
		 * @return ArrayList containing top 3 players and their times
		 */
		public static ArrayList<String> getLeaderboard(String filepath) {
			Scanner in = createFileScanner(filepath);
			leaderboard.clear();
			String currentLine = in.nextLine();
			// Skip everything in the file before the leaderboard
			while (!currentLine.equals("LEADERBOARD")) {
				currentLine = in.nextLine();
			}
			currentLine = in.nextLine();
			for (int i = 0; i < 3; i++) {
				String[] currentPlayer = currentLine.split(",");
				String playerName = currentPlayer[0];
				String playerTime = currentPlayer[1];
				leaderboard.add(playerName);
				leaderboard.add(playerTime);
				if (i < 2) {
					currentLine = in.nextLine();
				}
			}
			in.close();
			return leaderboard;
		}

		/**
		 * Gets all the player profile
		 *
		 * @return ArrayList<String> of all the player profile names
		 */
		public static ArrayList<String> getAllProfiles() {
			ArrayList<String> results = new ArrayList<String>();

			File[] files = new File("profiles").listFiles();
			// If this pathname does not denote a directory, then listFiles() returns null.

			for (File file : files) {
				if (file.isFile()) {
					if (file.getName().contains(".txt")) {
						String profileName = file.getName().replace(".txt", "");
						results.add(profileName);
					}
				}
			}
			return results;
		}

		/**
		 * Gets the information for the player object only from the player save file
		 *
		 * @param player      PLayer object to edit
		 * @param in          Scanner containing file
		 * @param currentLine The line the scanner was on before being passed
		 */
		private static void getPlayerDetails(Player player, Scanner in, String currentLine) {
			int currentMoves = Integer.parseInt(currentLine.split(",")[1]);
			player.setCurrentTime(currentMoves);
			int playerLevel = Integer.parseInt(in.nextLine().split(",")[1]);
			player.setCurrentLevel(playerLevel);
			int playerMaxLevel = Integer.parseInt((in.nextLine().split(",")[1]));
			player.setMaxLevel(playerMaxLevel);
			System.out.println(in.nextLine());
			// Go through the inventory list of the file
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
				case "KEY":
					player.addKey(line.next().toLowerCase());
					break;
				case "KATANNA":
					player.addKatanna();
					break;
				default:
					System.out.println("Unrecognized!");
				}
			}
		}

		/**
		 * Validates filepath given and creates a scanner if valid
		 *
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
			System.out.println("Reading: " + filename);
			return in;
		}

		/**
		 * Reads and generates the common section of the player and map files.
		 *
		 * @param in      Scanner of file to read
		 * @param divider The keyword to stop reading the file
		 */
		private static void readAnyFile(Scanner in, String divider, Player player) {
			int boardX = in.nextInt();
			int boardY = in.nextInt();
			boardDrawables = new Drawable[boardX][boardY];
			movables = new ArrayList<Movable>();
			interactables = new ArrayList<Interactable>();
			in.nextLine();
			// Generate the drawables array
			for (int i = 0; i < boardY; i++) {
				String currentTileLine = in.nextLine();
				for (int j = 0; j < boardX; j++) {
					String current = "" + currentTileLine.charAt(j);
					switch (current) {
					case "#":
						boardDrawables[j][i] = new StaticEntity(j, i, "assets/StoneBrickWall.png", 2);
						break;
					case ".":
						boardDrawables[j][i] = new StaticEntity(j, i, "assets/Floor.png", 0);
						break;
					case "F":
						boardDrawables[j][i] = new Fire(j, i);
						break;
					case "W":
						boardDrawables[j][i] = new Water(j, i);
						break;
					case "T":
						boardDrawables[j][i] = new StaticEntity(j, i, "assets/Floor.png", 1);
						break;
					case "D":
						boardDrawables[j][i] = new StaticEntity(j, i, "assets/Floor.png", 0);
						break;
					case "G":
						boardDrawables[j][i] = new Goal(j, i);
						break;
					default:
						boardDrawables[j][i] = new StaticEntity(j, i, "assets/placeholder.png", 2);
					}
				}
				System.out.println();
			}
			// Load interactables and enemies
			String currentLine = in.nextLine();
			while (!currentLine.startsWith(divider)) {
				Scanner line = new Scanner(currentLine).useDelimiter(",");
				int posX = (line.nextInt() - 1);
				int posY = (line.nextInt() - 1);
				String keyword = line.next();
				switch (keyword) {
				case "START": // Set the player's position
					player.setxCoord(posX);
					player.setyCoord(posY);
					break;
				case "ITEM":
					String itemType = line.next();
					switch (itemType) {
					case "TOKEN":
						int tokenValue = line.nextInt();
						interactables.add(new Token(posX, posY));
						break;
					case "FLIPPER":
						interactables.add(new Shoe(posX, posY, "flippers"));
						break;
					case "BOOTS":
						interactables.add(new Shoe(posX, posY, "boots"));
						break;
					case "KATANNA":
						interactables.add(new Katanna(posX, posY));
						break;
					case "KEY":
						String colour = line.next();
						interactables.add(new Key(posX, posY, colour));
						break;
					default:
						System.out.println("Unrecognised! " + itemType);
						break;
					}
					break;
				case "DOOR":
					String doorType = line.next();
					if (doorType.equals("TOKEN")) {
						int tokensRequired = line.nextInt();
						interactables.add(new TokenDoor(posX, posY, tokensRequired));
					} else {
						String doorColour = line.next();
						interactables.add(new ColouredDoor(posX, posY, doorColour));
					}
					break;
				case "TELE":
					int pairX = line.nextInt() - 1;
					int pairY = line.nextInt() - 1;
					Teleporter tele1 = new Teleporter(posX, posY);
					Teleporter tele2 = new Teleporter(pairX, pairY);
					tele1.setPartner(tele2);
					tele2.setPartner(tele1);
					boardDrawables[posX][posY] = tele1;
					boardDrawables[pairX][pairY] = tele2;
					interactables.add(tele1);
					break;
				case "ENEMY":
					String enemyType = line.next();
					String direction = "";
					int directionInt = 0;
					// If the enemy isn't smart convert it's direction in the file to an int
					if (!enemyType.equals("SMART")) {
						direction = line.next();
						switch (direction) {
						case "LEFT":
							directionInt = 3;
							break;
						case "RIGHT":
							directionInt = 1;
							break;
						case "UP":
							directionInt = 2;
							break;
						case "DOWN":
							directionInt = 0;
							break;
						}
					}
					switch (enemyType) {
					case "STRAIGHT":
						movables.add(new LineEnemy(posX, posY, directionInt));
						break;
					case "SMART":
						movables.add(new SmartEnemy(posX, posY));
						break;
					case "FOLLOW":
						movables.add(new FollowEnemy(posX, posY, directionInt, line.nextInt()));
						break;
					case "DUMB":
						movables.add(new DumbEnemy(posX, posY, directionInt));
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
			dividerLine = currentLine;
		}
	}

	public static class FileWriting {
		public static void savePlayerFile(Player player, Board board) {
			savePlayerFile("profiles\\" + player.getName() + ".txt", player, board);
		}

		/**
		 * Creates a new player's save file.
		 *
		 * @param playerName The player's name
		 */
		public static void createNewPlayer(String playerName) {
			BufferedWriter writer = null;
			File file = new File("profiles/" + playerName + ".txt");
			try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file, false);
				writer = new BufferedWriter(fw);
				writer.write(copyFileContents("assets/newplayer.txt"));
				// We make a copy of the default profile for all new players
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}

		/**
		 * Saves the player's game to a textfile.
		 *
		 * @param filename Name of the file to save to
		 * @param player   The player's object
		 * @param board    The board to save
		 */
		public static void savePlayerFile(String filename, Player player, Board board) {
			System.out.println("SAVING FILE: " + filename);
			BufferedWriter writer = null;
			Drawable[][] boardArray = board.getBoard();
			ArrayList<Movable> movables = board.getMovables();
			ArrayList<Interactable> interactables = board.getInteractables();
			FileWriter fw = null;
			int boardX = boardArray.length;
			int boardY = boardArray[0].length;
			try {
				File file = new File(filename);
				if (!file.exists()) {
					file.createNewFile();
				}

				fw = new FileWriter(file, false);
				writer = new BufferedWriter(fw);
				// We use .getClass().getName() to figure out what each object is
				writer.write(boardX + "," + boardY + "," + System.lineSeparator());
				// Write the drawables array to the file
				for (int i = 0; i < boardY; i++) {
					String currentLine = "";
					for (int j = 0; j < boardX; j++) {
						switch (boardArray[j][i].getClass().getName()) {
						case "StaticEntity":
							switch (boardArray[j][i].getBlocking()) {
							case 2:
								currentLine += "#";
								break;
							case 0:
								currentLine += ".";
								break;
							default:
								currentLine += "T";
								break;
							}
							break;
						case "Fire":
							currentLine += "F";
							break;
						case "Water":
							currentLine += "W";
							break;
						case "Teleporter":
							currentLine += "T";
							break;
						case "Goal":
							currentLine += "G";
							break;
						default:
							System.out.println("Not accounted for: " + boardArray[j][i].getClass().getName());
						}
					}
					writer.write(currentLine + System.lineSeparator());

				}
				int playerX = player.getxCoord() + 1;
				int playerY = player.getyCoord() + 1;
				writer.write(playerX + "," + playerY + "," + "START" + System.lineSeparator());
				// Save all the interactables on the board to the file
				for (Interactable interactable : interactables) {
					int xValue = interactable.getxCoord() + 1;
					int yValue = interactable.getyCoord() + 1;
					String type = interactable.getClass().getName().toUpperCase();
					String prefix = (xValue + "," + yValue + ",");
					switch (type) {
					case "TOKENDOOR":
						int tokensReq = ((TokenDoor) interactable).getThreshold();
						writer.write(prefix + "DOOR,TOKEN," + tokensReq);
						break;
					case "COLOUREDDOOR":
						String colour = ((ColouredDoor) interactable).getColour().toUpperCase();
						writer.write(prefix + "DOOR,KEY," + colour);
						break;
					case "SHOE":
						if (((Shoe) interactable).getType().equals("flippers")) {
							writer.write(prefix + "ITEM,FLIPPER");
						} else {
							writer.write(prefix + "ITEM,BOOTS");
						}
						break;
					case "TOKEN":
						writer.write(prefix + "ITEM,TOKEN,1");
						break;
					case "KEY":
						String keyColour = ((Key) interactable).getColour();
						writer.write(prefix + "ITEM,KEY," + keyColour.toUpperCase());
						break;
					case "TELEPORTER":
						// Get the teleporter's partner
						Teleporter partner = ((Teleporter) interactable).getPartner();
						String partnerPos = "," + (partner.xCoord + 1) + "," + (partner.yCoord + 1);
						writer.write(prefix + "TELE" + partnerPos);
						break;
					case "KATANNA":
						writer.write(prefix + "ITEM,KATANNA");
						break;
					default:
						System.out.println("Not implemented: " + type);
					}
					writer.write(System.lineSeparator());
				}
				// Save all the enemies on the board to the file
				for (Movable moveable : movables) {
					String type = moveable.getClass().getName().toUpperCase();
					if (!type.equals("PLAYER")) {
						int xValue = moveable.getxCoord() + 1;
						int yValue = moveable.getyCoord() + 1;
						String prefix = (xValue + "," + yValue + "," + "ENEMY,");
						String direction = "";
						if (!type.equals("SMARTENEMY")) {
							switch (((Enemy) moveable).getDir()) {
							case 0:
								direction = "DOWN";
								break;
							case 1:
								direction = "RIGHT";
								break;
							case 2:
								direction = "UP";
								break;
							case 3:
								direction = "LEFT";
								break;
							}
						}
						switch (type) {
						case "LINEENEMY":
							// Add direction
							writer.write(prefix + "STRAIGHT," + direction);
							break;
						case "SMARTENEMY":
							writer.write(prefix + "SMART");
							break;
						case "FOLLOWENEMY":
							int bias = ((FollowEnemy) moveable).getBias();
							writer.write(prefix + "FOLLOW," + direction + "," + bias);
							break;
						case "DUMBENEMY":
							writer.write(prefix + "DUMB," + direction);
							break;
						default:
							System.out.println("Not accounted for! " + type);
						}
					}
					writer.write(System.lineSeparator());
				}
				// Get current player time and level
				int playerMoves = player.getCurrentTime();
				int level = player.getCurrentLevel();
				int maxLevel = player.getMaxLevel();
				writer.write("CURRENTTIME," + playerMoves + System.lineSeparator());
				writer.write("LEVEL," + level + System.lineSeparator());
				writer.write("MAXLEVEL," + maxLevel + System.lineSeparator());
				writer.write("INVENTORY" + System.lineSeparator());
				if (player.getFlippers()) {
					writer.write("FLIPPER" + System.lineSeparator());
				}
				if (player.getBoots()) {
					writer.write("BOOTS" + System.lineSeparator());
				}
				if (player.getKatanna()) {
					writer.write("KATANNA" + System.lineSeparator());
				}
				int tokenAmount = player.getTokens();
				writer.write("TOKEN," + tokenAmount + System.lineSeparator());
				int[] playerKeys = player.getKeys();
				String keyColour = "";
				for (int i = 0; i < 3; i++) {
					if (playerKeys[i] > 0) {
						switch (i) {
						case 0:
							keyColour = "RED";
							break;
						case 1:
							keyColour = "BLUE";
							break;
						case 2:
							keyColour = "YELLOW";
							break;
						case 3:
							keyColour = "GREEN";
							break;
						}
						writer.write("KEY," + keyColour + System.lineSeparator());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
						fw.close();
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}

		/**
		 * Updates the leaderboard after a level has been completed
		 *
		 * @param boardFile  filepath of the board to update
		 * @param playerName Name of the player who has completed the level
		 * @param moves      Amount of moves the player made
		 * @throws IOException File cannot be found
		 */
		public static void updateLeaderboard(String boardFile, String playerName, int moves) throws IOException {
			ArrayList<String> leaderboard = FileManager.FileReading.getLeaderboard(boardFile);
			// We create a string of the old leaderboard to use in the replacetext method
			String oldLeaderboard = leaderboard.get(0) + "," + leaderboard.get(1) + System.lineSeparator()
					+ leaderboard.get(2) + "," + leaderboard.get(3) + System.lineSeparator() + leaderboard.get(4) + ","
					+ leaderboard.get(5) + System.lineSeparator();
			int firstTime = Integer.parseInt(leaderboard.get(1));
			int secondTime = Integer.parseInt(leaderboard.get(3));
			int thirdTime = Integer.parseInt(leaderboard.get(5));
			if (moves < firstTime) {
				if (leaderboard.get(0).equals(playerName)) {
					leaderboard.remove(1);
					leaderboard.remove(0);
				}
				leaderboard.add(0, playerName);
				leaderboard.add(1, String.valueOf(moves));
			} else if (moves > firstTime && moves < secondTime) {
				if (leaderboard.get(0).equals(playerName)) {
//					System.out.println("Player already has a faster time on the leaderboard.");
				} else {
					leaderboard.add(2, playerName);
					leaderboard.add(3, String.valueOf(moves));
				}
			} else if (moves > secondTime && moves < thirdTime) {
				if ((leaderboard.get(0).equals(playerName)) || leaderboard.get(3).equals(playerName)) {
//					System.out.println("Player already has a faster time on the leaderboard.");
				} else {
					leaderboard.add(4, playerName);
					leaderboard.add(5, String.valueOf(moves));
				}
			}
			// The updated leaderboard only has the top 3 times so any more are ignored
			String newLeaderboard = leaderboard.get(0) + "," + leaderboard.get(1) + System.lineSeparator()
					+ leaderboard.get(2) + "," + leaderboard.get(3) + System.lineSeparator() + leaderboard.get(4) + ","
					+ leaderboard.get(5) + System.lineSeparator();
			// Replace the leaderboard in the save file
			replaceText(boardFile, oldLeaderboard, newLeaderboard);
		}

		public static void deletePlayer(String playerName) {
			File file = new File("profiles/" + playerName + ".txt");
			file.delete();
		}

		/**
		 * Copies all text in a file to a string.
		 *
		 * @param filepath file to copy
		 * @return String containing file's contents
		 * @throws IOException
		 */
		private static String copyFileContents(String filepath) throws IOException {
			File file = new File(filepath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String contents = "";
			String currentline = br.readLine();
			while (currentline != null) {
				contents = contents + currentline + System.lineSeparator();
				currentline = br.readLine();
			}
			br.close();
			return contents;
		}

		/**
		 * Replaces a specified string in a text file
		 *
		 * @param filepath File to be edited
		 * @param oldText  String to be replaced
		 * @param newText  New string to add
		 * @throws IOException
		 */
		private static void replaceText(String filepath, String oldText, String newText) throws IOException {
			File file = new File(filepath);
			String oldFile = copyFileContents(filepath);
			// Get a string of the contents of the whole file

			FileWriter writer = new FileWriter(file);

			String newFile = oldFile.replaceAll(oldText, newText);
			writer.write(newFile);
			writer.close();
		}
	}
}
