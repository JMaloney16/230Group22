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
 * @version 0.7
 */

//TODO
// make class fully static please bb <3
// and make a savePlayerFile(Player, Board) that saves the game state, suga' plum ;)
// needs a method to update the leaderboard
// needs a method to get a leaderboard from a filename
// player file needs to store filepath to the level and store the highest level achieved variable

// ----> needs get all profiles method too
// sorry boo
public class FileManager {

	public static class FileReading {

		// Forgive me for I have sinned.
		private static Drawable[][] boardDrawables;
		private static ArrayList<Movable> movables;
		private static ArrayList<Interactable> interactables;
		private static String dividerLine;

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
			return in;
		}

		/**
		 * Reads and generates the common section of the player and map files
		 *
		 * @param in      Scanner of file to read
		 * @param divider The keyword to stop reading the file
		 */
		private static void readAnyFile(Scanner in, String divider, Player player) {
			int boardX = in.nextInt();
			int boardY = in.nextInt();
			System.out.println(boardX);
			System.out.println(boardY);
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
							boardDrawables[j][i] = new StaticEntity(j, i, "assets/StoneBrickWall.png", 2);
							break;
						case ".":
							System.out.print(".");
							boardDrawables[j][i] = new StaticEntity(j, i, "assets/Floor.png", 0);
							break;
						case "F":
							System.out.print("F");
							boardDrawables[j][i] = new Fire(j, i);
							break;
						case "W":
							System.out.print("W");
							boardDrawables[j][i] = new Water(j, i);
							break;
						case "T":
							System.out.print("T");
							break;
						case "D":
							System.out.print("D");
							break;
						case "G":
							System.out.print("G");
							boardDrawables[j][i] = new Goal(j, i);
							break;
						default:
							System.out.print("?");
							boardDrawables[j][i] = new StaticEntity(j, i, "assets/placeholder.png", 2);
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
						//TODO: Get the level number idek how, and player name - maybe set to
						// something else in readPlayerFile
						System.out.println("START");
						player.setPosX(posX);
						player.setPosY(posY);
						break;
					case "ITEM":
						String itemType = line.next();
						System.out.println("It's a: " + itemType);
						switch (itemType) {
							case "TOKEN":
								int tokenValue = line.nextInt();
								System.out.println("It's worth: " + tokenValue);
								interactables.add(new Token(posX, posY));
								break;
							case "FLIPPER":
								//TODO: Add difference between flipper and boots
								interactables.add(new Shoe(posX, posY, "Flipper"));
								break;
							case "BOOTS":
								interactables.add(new Shoe(posX, posY, "Shoe"));
								break;
							case "KEY":
								String colour = line.next();
								System.out.println("It's a " + colour + " key");
								interactables.add(new Key(posX, posY, colour));
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
							boardDrawables[posX][posY] = new TokenDoor(posX, posY, tokensRequired);
						} else {
							String doorColour = line.next();
							System.out.println("It's a " + doorColour.toLowerCase() + " door!");
							boardDrawables[posX][posY] = new ColouredDoor(posX, posY, doorColour);
						}
						break;
					case "TELE":
						//TODO: Add teleporter partner
						int pairX = line.nextInt();
						int pairY = line.nextInt();
						System.out.println("It's a teleporter pair: " + posX + posY + pairX + pairY);
						Teleporter tele1 = new Teleporter(posX, posY);
						Teleporter tele2 = new Teleporter(pairX, pairY);
						tele1.setPartner(tele2);
						tele2.setPartner(tele1);
						boardDrawables[posX][posY] = tele1;
						boardDrawables[pairX][pairY] = tele2;
						break;
					case "ENEMY":
						String enemyType = line.next();
						String direction = "";
						int directionInt = 0;
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
							//TODO: Add all enemy types
							case "STRAIGHT":
								System.out.println("Straight type enemy starting: " + direction);
								movables.add(new LineEnemy(posX, posY, directionInt));
								break;
							case "SMART":
								System.out.println("Smart enemy");
								movables.add(new SmartEnemy(posX, posY));
								break;
							case "FOLLOW":
								System.out.println("Follow enemy");
								movables.add(new FollowEnemy(posX, posY, directionInt, 1));
								break;
							case "DUMB":
								System.out.println("Dumb enemy");
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

		/**
		 * Loads a new map from a file
		 *
		 * @param filepath location of file to read from
		 * @param board    board to be populated
		 */
		public static void readMapFile(String filepath, Board board, Player player) {
			Scanner in = createFileScanner(filepath);
			readAnyFile(in, "LEVEL", player);
			int mapLevel = Integer.parseInt(dividerLine.split(",")[1]);
			in.nextLine();
			String currentLine = in.nextLine();

			for (int i = 0; i < 3; i++) {
				String[] topPlayers = currentLine.split(",");
				String playerName = topPlayers[0];
				int playerTime = Integer.parseInt(topPlayers[1]);
				System.out.println(playerName + " completed the level in: " + playerTime);
				if (i < 2) {
					currentLine = in.nextLine();
				}
			}

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
		public static void readPlayerFile(String filepath, Player player, Board board) { // please make it readPlayerFile(String filepath, Player player, Board board) thanks x
			Scanner in = createFileScanner(filepath);
			readAnyFile(in, "CURRENTTIME", player);
			//TODO: Find a way to pass the currenttime to the game manager
			int currentTime = Integer.parseInt(dividerLine.split(",")[1]);
			int playerLevel = Integer.parseInt(in.nextLine().split(",")[1]);
			System.out.println(in.nextLine());

			while (in.hasNextLine()) {
				Scanner line = new Scanner(in.nextLine()).useDelimiter(",");
				String itemType = line.next();
				switch (itemType) {
					case "FLIPPER":
						System.out.println("Flippers");
						player.addFlippers();
						break;
					case "BOOTS":
						System.out.println("Boots");
						player.addBoots();
						break;
					case "TOKEN":
						System.out.println("Token");
						player.addToken(line.nextInt());
						break;
					case "KEY":
						System.out.println("Key");
						player.addKey(line.next().toLowerCase());
						break;
					default:
						System.out.println("Unrecognized!");
				}

			}

			board.setNewBoard(boardDrawables, movables, interactables);
		}
		
		/** Gets all the player profile
		 * @return ArrayList<String> of all the player profile names
		 */
		public static ArrayList<String> getAllProfiles(){
			ArrayList<String> results = new ArrayList<String>();

			File[] files = new File("profiles").listFiles();
			//If this pathname does not denote a directory, then listFiles() returns null. 

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
	}

	public static class FileWriting {
		public static void savePlayerFile(Player player, Board board) {
			savePlayerFile("testPlayerFile.txt", player, board);
		}

		/**
		 * Saves the player's game to a textfile
		 *
		 * @param filename Name of the file to save to
		 * @param player   The player's object
		 * @param board    The board to save
		 */
		public static void savePlayerFile(String filename, Player player, Board board) {
			BufferedWriter writer = null;
			Drawable[][] boardArray = board.getBoard();
			ArrayList<Movable> movables = board.getMovables();
			ArrayList<Interactable> interactables = board.getInteractables();

			int boardX = boardArray[0].length;
			int boardY = boardArray.length;
			try {
				File file = new File(filename);
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file);
				writer = new BufferedWriter(fw);
				// We use .getClass().getName() to figure out what each object is
				writer.write(boardX + "," + boardY + ",");
				for (int i = 0; i < boardX; i++) {
					String currentLine = "";
					for (int j = 0; j < boardY; j++) {
						switch (boardArray[j][i].getClass().getName()) {
							case "StaticEntity":
								if (boardArray[j][i].getBlocking() == 0) {
									currentLine += "#";
								} else {
									currentLine += ".";
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
							default:
								System.out.println("Not accounted for: " + boardArray[j][i]
									.getClass().getName());
						}
					}
					writer.write(currentLine);
				}
				//TODO: Get player coords
				int playerX = 10;
				int playerY = 9;
				writer.write(playerX + "," + playerY + "," + "START");
				for (Interactable interactable : interactables) {
					int xValue = interactable.getxCoord();
					int yValue = interactable.getyCoord();
					String type = interactable.getClass().getName().toUpperCase();
					String prefix = (xValue + "," + yValue + ",");
					switch (type) {
						case "TOKENDOOR":
							writer.write(prefix + "DOOR,TOKEN,1");
							break;
						case "COLOURED":
							//TODO: Get the colour of the door
							//String colour = ((Coloured) interactable).
							writer.write(prefix + "DOOR,RED");
							break;
						case "FLIPPER":
							writer.write(prefix + "ITEM,FLIPPER");
							break;
						case "SHOE":
							writer.write(prefix + "ITEM,BOOTS");
							break;
						case "TOKEN":
							writer.write(prefix + "ITEM,TOKEN,1");
							break;
						case "KEY":
							String colour = ((Key) interactable).getColour();
							writer.write(prefix + "KEY," + colour.toUpperCase());
							break;
						case "TELEPORTER":
							//TODO: Get the teleporter's partner
							writer.write(prefix + "TELE,1");
							break;
						default:
							System.out.println("Not implemented: " + type);
					}
				}
				for (Movable moveable : movables) {
					int xValue = moveable.getxCoord();
					int yValue = moveable.getyCoord();
					String type = moveable.getClass().getName().toUpperCase();
					String prefix = (xValue + "," + yValue + "," + "ENEMY");
					if (!type.equals("PLAYER")) {
						switch (type) {
							case "LINEENEMY":
								//TODO: Add direction
								writer.write(prefix + "STRAIGHT,LEFT");
								break;
							case "SMARTENEMY":
								writer.write(prefix + "SMART");
								break;
							case "FOLLOWENEMY":
								writer.write(prefix + "FOLLOW");
								break;
							case "DUMBENEMY":
								writer.write(prefix + "DUMB");
								break;
							default:
								System.out.println("Not accounted for! " + type);
						}
					}
				}
				//TODO: Get current player time and level
				int playerTime = 23;
				//int level = player.getMaxLevel();
				int level = board.getLevelNumber();
				writer.write("CURRENTTIME," + playerTime);
				writer.write("LEVEL," + level);
				writer.write("INVENTORY");
				if (player.getFlippers()) {
					writer.write("FLIPPER");
				}
				if (player.getBoots()) {
					writer.write("BOOTS");
				}
				int tokenAmount = player.getTokens();
				writer.write("TOKEN," + tokenAmount);
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
	}
}

