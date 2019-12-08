import javafx.scene.canvas.GraphicsContext;

/**
 * Player.java Stores all data ascociated with the player. Subclass of Movable.
 * 
 * @version 0.1
 * @author Ewan Bradford, Luke Francis
 */
public class Player extends Movable {
	private int[] keys = new int[4]; // red blue yellow green
	private int tokens = 0;
	private boolean flippers = false;
	private boolean boots = false;
	private boolean katanna = false;
	private boolean killed = false;
	private boolean completed = false;
	private String name;
	private int currentLevel;
	private int maxLevel;
	private int currentMoves;
	private int currentTime;

	/**
	 * Creates a new player from a position and image file.
	 * 
	 * @param x,        the x coordinate of the player.
	 * @param y,        the y coordinate of the player.
	 * @param maxLevel, highest level reached by the player
	 */
	public Player(int x, int y, int maxLevel) {
		super(x, y, "assets\\player.png", 0);
		this.name = name;
		this.maxLevel = maxLevel;
	}

	/**
	 * Updates the player's position in response to the keyboard input, if move is
	 * allowed.
	 * 
	 * @param board,      the board object for the player to be "placed" in.
	 * @param keyboardIn, the current keypress.
	 * @return Returns a code, 0-player could no perform move, 1 successfully moved,
	 *         2-player is dead.
	 */
	public int update(Board board, int keyboardIn) { // return codes, 0 didn't move, 1 moved, 2 dead, 3 level completed
		System.out.println(board.getBlocking(this.xCoord, this.yCoord));
//		if (board.getBlocking(this.xCoord, this.yCoord) > 0) { // makes sure player is not inside an
//			this.killed = true;
//		}
		if (this.completed == true) {
			return 3;
		}
		if (this.killed == true) {
			return 2;
		}
		int search = -1;
		switch (keyboardIn) { // check if requested move is unblocked, if so next position is stored
		case 0:
			search = board.getBlocking(this.xCoord, this.yCoord - 1);
			if (search == -1) {
				return 0;
			} else if (search < 2) {
				this.nextX = this.xCoord;
				this.nextY = this.yCoord - 1;
			}
			break;
		case 1:
			search = board.getBlocking(this.xCoord + 1, this.yCoord);
			if (search == -1) {
				return 0;
			} else if (search < 2) {
				this.nextX = this.xCoord + 1;
				this.nextY = this.yCoord;
			}
			break;
		case 2:
			search = board.getBlocking(this.xCoord, this.yCoord + 1);
			if (search == -1) {
				return 0;
			} else if (search < 2) {
				this.nextX = this.xCoord;
				this.nextY = this.yCoord + 1;
			}
			break;
		case 3:
			search = board.getBlocking(this.xCoord - 1, this.yCoord);
			if (search == -1) {
				return 0;
			} else if (search < 2) {
				this.nextX = this.xCoord - 1;
				this.nextY = this.yCoord;
			}
			break;
		}
		this.setxCoord(this.nextX);
		this.setyCoord(this.nextY);
		return 1;
	}

	/**
	 * Removes one key if the player owns one.
	 * 
	 * @param keyColour, the colour of the key to be removed.
	 * @return Returns true if a key was present and removed, false if player did
	 *         not have a key to remove.
	 */
	public boolean removeKey(String keyColour) {
		switch (keyColour) {
		case "red":
			if (this.keys[0] > 0) {
				this.keys[0] -= 1;
				return true;
			}
			return false;
		case "blue":
			if (this.keys[1] > 0) {
				this.keys[1] -= 1;
				return true;
			}
			return false;
		case "yellow":
			if (this.keys[2] > 0) {
				this.keys[2] -= 1;
				return true;
			}
			return false;
		case "green":
			if (this.keys[3] > 0) {
				this.keys[3] -= 1;
				return true;
			}
			return false;
		default: // if asked for no existent key colour
			return false;
		}
	}

	/**
	 * Checks if a key is currently held by the player
	 * 
	 * @param keyColour, colour of the key to be checked
	 * @return true if key is currently held, false if not
	 */
	public boolean checkKey(String keyColour) {
		if (keyColour != null) {
			switch (keyColour) {
			case "red":
				if (this.keys[0] > 0) {
					return true;
				}
				return false;
			case "blue":
				if (this.keys[1] > 0) {
					return true;
				}
				return false;
			case "yellow":
				if (this.keys[2] > 0) {
					return true;
				}
				return false;
			case "green":
				if (this.keys[3] > 0) {
					return true;
				}
				return false;
			default: // if asked for no existent key colour
				return false;
			}
		}
		return false;
	}

	/**
	 * draws the player
	 * 
	 * @param gc, graphics context to be drawn to
	 */
	public void draw(GraphicsContext gc) {
		this.draw(gc, this.xCoord - 3, this.yCoord - 3);
	}

	/**
	 * Adds a key to the players keys.
	 * 
	 * @param keyColour, the colour of the key to add. (red, blue, yellow or green)
	 */
	public void addKey(String keyColour) {
		SoundEffect.playSound("assets\\sounds\\Key.wav");
		switch (keyColour) {
		case "red":
			this.keys[0] += 1;
			break;
		case "blue":
			this.keys[1] += 1;
			break;
		case "yellow":
			this.keys[2] += 1;
			break;
		case "green":
			this.keys[3] += 1;
			break;
		default:
			System.out.println(keyColour);
			System.out.println("Cant add that key"); // maybe raise an error?
		}
	}

	/**
	 * Sets the killed attribute to true, killing the play next update
	 */
	public void kill() {
		SoundEffect.playSound("assets\\sounds\\Death.wav");
		this.killed = true;
	}

	public void completeLevel() {
		this.completed = true;
	}

	/**
	 * Adds tokens to the player.
	 * 
	 * @param tokens, the number of tokens to be added.
	 */
	public void addToken(int tokens) {
		this.tokens += tokens;
	}

	/**
	 * @return Returns the current number of tokens the player has.
	 */
	public int getTokens() {
		return this.tokens;
	}

	/**
	 * Gives the player boots
	 */
	public void addBoots() {
		SoundEffect.playSound("assets\\sounds\\Boots.wav");
		this.boots = true;
	}

	/**
	 * @return Returns if the player has boot equipped.
	 */
	public boolean getBoots() {
		return this.boots;
	}

	/**
	 * Gives the player flippers.
	 */
	public void addFlippers() {
		this.flippers = true;
	}

	/**
	 * @return Returns if the player has flipper equipped.
	 */
	public boolean getFlippers() {
		return this.flippers;
	}

	/**
	 * Adds the Katanna to the players inventory
	 */
	public void addKatanna() {
		this.katanna = true;
	}

	/**
	 * Removes the Katanna from the players inventory
	 */
	public void removeKatanna() {
		this.katanna = false;
	}

	/**
	 * Checks if player has a Katanna in inventory
	 * 
	 * @return true if player has a katanna, false if not
	 */
	public boolean getKatanna() {
		return this.katanna;
	}

//	public void setPosX(int x) {
//		this.xCoord = x;
//	}
//
//	public void setPosY(int y) {
//		this.yCoord = y;
//	}
//	public int getPosX() {
//		return this.xCoord;
//	}
//	public int getPosY() {
//		return this.yCoord;
//	}
	public void setCurrentMoves(int moves) {
		this.currentMoves = moves;
	}

	public int getCurrentMoves() {
		return this.currentMoves;
	}

	public void setCurrentLevel(int level) {
		this.currentLevel = level;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel() {
		return this.maxLevel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int[] getKeys() {
		return keys;
	}
	
	public boolean getKilled() {
		return this.killed;
	}	
	public boolean getCompleted() {
		return this.completed;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
}
