/**
 * Fire.java Creates a fire tile on the board
 * 
 * @version 0.3
 * @author Gino Sesia
 */
public class Fire extends Tile {

	/**
	 * Creates a fire tile.
	 * 
	 * @param x,      the x coordinate of the fire tile.
	 * @param y,      the y coordinate of the fire tile.
	 */
	public Fire(int x, int y) {
		super(x, y, "assets/Lava.png", 1);
	}

	/**
	 * Checks if the player is standing on a fire tile.
	 * 
	 * @param board,      the board object for the player to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current keypress.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			if (player.getBoots() == false) {
				player.kill();
			}
		}
	}
}
