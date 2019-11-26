/**
 * Water.java Creates a water tile on the board
 * 
 * @version 0.4
 * @author Gino Sesia
 */
public class Water extends Tile {

	/**
	 * Creates a water tile.
	 * 
	 * @param x,      the x coordinate of the water tile.
	 * @param y,      the y coordinate of the water tile.
	 */
	public Water(int x, int y) {
		super(x, y, "../assets/Water.png", 1);
	}

	/**
	 * Checks if the player is standing on a water tile.
	 * 
	 * @param board,      	the board object for the player to be "placed" in.
	 * @param player, 		the player.
	 * @param keyboardIn, 	the current keypress.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			if (player.getFlippers() == false) {
				player.kill();
			}
		}
	}
}
