import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Board.java Stores and updates the playing board of the game.
 * 
 * @version 0.4
 * @author Ewan Bradford
 * @author Sam Forster
 * @author Luke Francis
 */

public class Board {
	private Drawable[][] board;
	private ArrayList<Movable> movables = new ArrayList<Movable>();
	private ArrayList<Interactable> interactables = new ArrayList<Interactable>();
	private int levelNumber;

	/**
	 * Creates a board with from static, movable and interactable data.
	 * 
	 * @param board,         a 2D array of the static elements of the board.
	 * @param movables,      the elements that will move around the 2D board array.
	 * @param interactables, these are the elements that will interact with the
	 *                       player.
	 */
	public Board(Drawable[][] board, ArrayList<Movable> movables, ArrayList<Interactable> interactables) {
		this.board = board;
		this.movables = movables;
		this.interactables = interactables;
		this.levelNumber = 1;
	}

	/**
	 * Draws all the static tiles of the board
	 * 
	 * @param gc,      the graphics context to be drawn to
	 * @param playerX, the current x coordinate of the player
	 * @param playerY, the current y coordinate of the player
	 */
	public void drawBoard(GraphicsContext gc, int playerX, int playerY) {
		// draws board top left to bottom right
		for (int y = Math.max(0, playerY - 3); y <= Math.min(board[0].length - 1, playerY + 3); y++) {
			for (int x = Math.max(0, playerX - 3); x <= Math.min(board.length - 1, playerX + 3); x++) {
					board[x][y].draw(gc, playerX - 3, playerY - 3);

			}
		}
	}

	/**
	 * Updates all the static tiles of the board
	 * 
	 * @param player,     the player object playing the level
	 * @param keyboardIn, the current key press from the user
	 */
	public void updateBoard(Player player, int keyboardIn) {
		for (int y = 0; y < board[0].length; y++) {
			for (int x = 0; x < board.length; x++) {
				board[x][y].update(this, player, keyboardIn);
			}
		}
	}

	/**
	 * Draws all the movables to the screen
	 * 
	 * @param gc,      graphics context to be drawn to
	 * @param playerX, the current x coordinate of the player
	 * @param playerY, the current y coordinate of the player
	 */
	public void drawMovables(GraphicsContext gc, int playerX, int playerY) {
		// TODO
		// movables still draw even if off screen, causes weirdness
		for (Movable m : this.movables) {
			m.draw(gc, playerX - 3, playerY - 3);
		}
	}

	/**
	 * Updates all of the movables on the board
	 * 
	 * @param player,     the player object that is playing the board.
	 * @param keyboardIn, the current input from the keyboard. (0 representing noon,
	 *                    1 three o'clock etc)
	 */
	public void updateMovables(Player player, int keyboardIn) {
		// individually updates each movable (potential bug depending on order of this
		// arraylist)
		for (Movable m : this.movables) {
			m.update(this, player, keyboardIn);
		}
	}

	/**
	 * Draws all the interactables to the screen
	 * 
	 * @param gc,      graphics context to be drawn to
	 * @param playerX, the current x coordinate of the player
	 * @param playerY, the current y coordinate of the player
	 */
	public void drawInteractables(GraphicsContext gc, int playerX, int playerY) {
		for (Interactable i : this.interactables) {
			i.draw(gc, playerX - 3, playerY - 3);
		}
	}

	/**
	 * @param player,     the player object that is playing the board.
	 * @param keyboardIn, the current input from the keyboard. (0 representing noon,
	 *                    1 three o'clock etc)
	 */
	public void updateInteractables(Player player, int keyboardIn) {
		// individually updates the interactables
		// this is required over a for each loop as this way does not
		// result in checkForComodification being called, raising an error
		for (int i = 0; i < this.interactables.size(); i++) {
			this.interactables.get(i).update(this, player, keyboardIn);
		}
	}

	/**
	 * Search and remove a specific movable object from the board.
	 * 
	 * @param m, the movable object to be removed.
	 */
	public void removeMovable(Movable m) {
		this.movables.remove(m);
	}

	/**
	 * Search and remove a specific movable object from the board.
	 * 
	 * @param i, the interactable to be removed.
	 */
	public void removeInteractable(Interactable i) {
		this.interactables.remove(i);
	}

	/**
	 * Add a movable to the board.
	 * 
	 * @param m, the movable object to be added.
	 */
	public void addMovable(Movable m) {
		this.movables.add(m);
	}

	/**
	 * Add an interactable to the board.
	 * 
	 * @param i, the interactable to be added.
	 */
	public void addInteractable(Interactable i) {
		this.interactables.add(i);
	}

	/**
	 * Checks if there is any blocking entities at a given cell.
	 * 
	 * @param x, the x coordinate to be checked.
	 * @param y, the y coordinate to be checked.
	 * @return Returns the highest blocking value that is present at that position.
	 */
	public int getBlocking(int x, int y) {
		// TODO
		// needs to go through board[][] and movables[] and return the highest factor of
		// blockings
		int highestBlocking = 0;
		if (x < this.board.length && y < this.board[0].length && x >= 0 && y >= 0) {
			highestBlocking = this.board[x][y].getBlocking();
			
			Movable mSearch = this.searchMovavbles(x, y);
			if (mSearch != null) {
				if (mSearch.getBlocking() > highestBlocking) {
					highestBlocking = mSearch.getBlocking();
				}
			}
			
			Interactable iSearch = this.searchInteractables(x, y);
			return highestBlocking;
		}
		return -1; // needs to raise error, maybe
	}

	public Drawable[][] getBoard() {
		return this.board;
	}

	public ArrayList<Movable> getMovables() {
		return this.movables;
	}
	
	private Movable searchMovavbles(int x, int y) {
		Movable result = null;
		for (Movable m : this.movables) {
			if (m.getxCoord() == x && m.getyCoord() == y) {
				result = m;
			}
		}
		return result;
	}

	public ArrayList<Interactable> getInteractables() {
		return this.interactables;
	}
	
	private Interactable searchInteractables(int x, int y) {
		Interactable result = null;
		for (Interactable i : this.interactables) {
			if (i.getxCoord() == x && i.getyCoord() == y) {
				result = i;
			}
		}
		return result;
	}

	public int getLevelNumber() {
		return this.levelNumber;
	}

	public void setLevelNumber(int number) {
		this.levelNumber = number;
	}

	public void setNewBoard(Drawable[][] boardDrawables, ArrayList<Movable> movables, ArrayList<Interactable> interactables) {
		this.board = boardDrawables;
		this.movables = movables;
		this.interactables = interactables;
	}
}
