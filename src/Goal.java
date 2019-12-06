/**
 * Goal.java, implements the goal for the player to complete the level
 * 
 * @version 0.2
 * @author Gino Sesia
 * @author Ewan Bradford
 *
 */

public class Goal extends Tile {

	/**
	 * Creates a Goal tile.
	 * 
	 * @param x, the x coordinate of the Goal tile.
	 * @param y, the y coordinate of the Goal tile.
	 */
	public Goal(int x, int y) {
		super(x, y, "assets/Goal.png", 0);
	}

	/**
	 * Updates the goal tile, checks player position and behaves accordingly
	 * 
	 * @param board,      the board object for the player to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 * 
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.completeLevel();
		}
	}

}
