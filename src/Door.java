/**
 * Door.java creates a door on the board
 * 
 * @version 0.2
 * @author Gino Sesia
 */

public abstract class Door extends Tile {
	protected boolean opened = false;
	/**
	 * Creates a Door tile.
	 * 
	 * @param x, the x coordinate of the Door tile.
	 * @param y, the y coordinate of the Door tile.
	 */
	public Door(int x, int y, String sprite) {
		super(x, y, sprite, 2);
	}

	/**
	 * Checks if the player is standing on a Door tile.
	 * 
	 * @param board,      the board object for the player to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 */

	@Override
	public void update(Board board, Player player, int keyboardIn) {		

	}
}
