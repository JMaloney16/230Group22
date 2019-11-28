/**
 * Door.java creates a door on the board
 * 
 * @version 0.1
 * @author Gino Sesia
 */

public abstract class Door extends Tile {

	/**
	 * Creates a Door tile.
	 * 
	 * @param x,      the x coordinate of the water tile.
	 * @param y,      the y coordinate of the water tile.
	 */
	public Door(int x, int y) {
		super(x, y, "assets/Door.png", 0);
	}

	/**
	 * Checks if the player is standing on a Door tile.
	 * 
	 * @param board,      	the board object for the player to be "placed" in.
	 * @param player, 		the player.
	 * @param keyboardIn, 	the current key pressed.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			
		}
	}
}