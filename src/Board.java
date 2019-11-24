import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Board.java 
 * Stores and updates the playing board of the game.
 * 
 * @version 0.2
 * @author Ewan Bradford
 * @author Sam Forster
 * @author Luke Francis
 */

public class Board {
	private Drawable[][] board;
	private ArrayList<Movable> movables = new ArrayList<Movable>();
	private ArrayList<Interactable> interactables = new ArrayList<Interactable>();

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
	}

	public void drawBoard(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// draws board top left to bottom right
		for(int y=0;y<board[0].length;y++) {
			for(int x=0;x<board.length;x++) {
				board[x][y].draw(gc);
			}
		}
	}

	/**
	 * Updates all of the movables on the board
	 * 
	 * @param player,     the player object that is playing the board.
	 * @param keyboardIn, the current input from the keyboard. (0 representing noon,
	 *                    1 three o'clock etc)
	 */
	public void updateMovable(Player player, int keyboardIn) {
		// individually updates each movable (potential bug depending on order of this
		// arraylist)
		for (Movable m : this.movables) {
			m.update(this, player, keyboardIn);
		}
	}

	/**
	 * @param player,     the player object that is playing the board.
	 * @param keyboardIn, the current input from the keyboard. (0 representing noon,
	 *                    1 three o'clock etc)
	 */
	public void updateInteractables(Player player, int keyboardIn) {
		// individually updates the interactables
		for (Interactable i : this.interactables) {
			i.update(this, player, keyboardIn);
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
		// needs to go through board[][] and movables[] and return the highest factor of
		// blockings
		if (x < this.board.length && y < this.board[0].length && x >= 0 && y >= 0) {
			return this.board[x][y].getBlocking();
		}
		return -1; // needs to raise error, maybe
	}
}
