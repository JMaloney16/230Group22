/**
 * Katanna.java
 * @author Jack Maloney
 * @version 0.5
 */
public class Katanna extends Collectable {
	public Katanna(int x, int y) {
		super(x, y, "assets/katanna.png", 0);
	}

	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.addKatanna();
			board.removeInteractable(this);
		}
	}
}
