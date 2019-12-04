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
	 * @param x,      the x coordinate of the Goal tile.
	 * @param y,      the y coordinate of the Goal tile.
	 */
	public Goal(int x, int y) {
		super(x, y, "assets/Goal.png", 0);
	}
	
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.completeLevel();
		}
	}
	
	

}
