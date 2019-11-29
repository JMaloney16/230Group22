
/**
 * Lava.java implements the lava tile
 * 
 * @version 0.1
 * @author Ewan Bradford
 *
 */
public class Lava extends Tile {	
	/**
	 * Creates a water tile.
	 * 
	 * @param x,      the x coordinate of the water tile.
	 * @param y,      the y coordinate of the water tile.
	 */
	public Lava(int x, int y) {
		super(x, y, "assets/Lava.png", 1);
	}
	/**
	 * Checks if the player is standing on this lava tile.
	 * 
	 * @param board,      	the board object for the player to be "placed" in.
	 * @param player, 		the player.
	 * @param keyboardIn, 	the current key pressed.
	 */
	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			if (player.getBoots() == false) {
				player.kill();
			}
		}
	}

}
