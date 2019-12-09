/**
 * Katanna.java
 * 
 * @author Jack Maloney
 * @author Will Marsh
 * @version 0.5
 */
public class Katanna extends Collectable {
	/**
	 * Constructs a new Katanna
	 *
	 * @param x,	the x cooirdinate of the Katanna
	 * @param y,    the y coordinate of the Katanna
	 */
	public Katanna(int x, int y) {
		super(x, y, "assets/katanna.png", 1);
	}

	/**
	 * Handles the collection of the Katanna
	 * @param board,      the board object for the Katanna to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() && !player.getKatanna()) {
			player.addKatanna();
			board.removeInteractable(this);
		}
	}
}
