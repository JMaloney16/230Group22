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
	 * @param x,y,    the coordinates of the Katanna
	 * @param colour, the colour of the Katanna
	 */
	public Katanna(int x, int y) {
		super(x, y, "assets/katanna.png", 1);
	}

	/**
	 * Handles the collection of the Katanna
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() && !player.getKatanna()) {
			player.addKatanna();
			board.removeInteractable(this);
		}
	}
}
