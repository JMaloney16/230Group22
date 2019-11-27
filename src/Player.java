import java.util.ArrayList;
// TODO
// -----> stores max level

/**
 * Player.java
 * Stores all data ascociated with the player. 
 * Subclass of Movable.
 * 
 * @version 0.1
 * @author Ewan Bradford, Luke Francis
 */
public class Player extends Movable {
	private int[] keys = new int[4]; // red blue yellow green
	private int tokens = 0;
	private boolean flippers = false;
	private boolean boots = false;
	private boolean killed = false;

	/**
	 * Creates a new player from a position and image file.
	 * 
	 * @param x,      the x coordinate of the player.
	 * @param y,      the y coordinate of the player.
	 * @param sprite, the filepath of the image for the player.
	 */
	public Player(int x, int y, String sprite) {
		super(x, y, sprite, 0);
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
	public int update(Board board, int keyboardIn) { // return codes, 0 didn't move, 1 moved, 2 dead
		// keyboard in 0 noon through to 3 at 9 o'clock
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
		this.xCoord = this.nextX;
		this.yCoord = this.nextY;
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
			if (this.keys[2] > 0) {
				this.keys[2] -= 1;
				return true;
			}
			return false;
		default: // if asked for no existent key colour
			return false;
		}
	}

	/**
	 * Adds a key to the players keys.
	 * 
	 * @param keyColour, the colour of the key to add. (red, blue, yellow or green)
	 */
	public void addKey(String keyColour) {
		switch (keyColour) {
		case "red":
			this.keys[0] += 1;
		case "blue":
			this.keys[1] += 1;
		case "yellow":
			this.keys[2] += 1;
		case "green":
			this.keys[3] += 1;
		default:
			System.out.println("Cant add that key"); // maybe raise an error?
		}
	}
	
	/**
	 * Sets the killed attribute to true, killing the play next update
	 */
	public void kill() {
		this.killed = true;
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
	
	public int getPosX() {
		return this.xCoord;
	}
	public int getPosY() {
		return this.yCoord;
	}
}
