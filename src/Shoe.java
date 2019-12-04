/**
 * Shoe.java
 * Holds information about a key
 *
 * @author Jack Maloney
 * @version 0.5
 * @see Collectable
 * @see Interactable
 */
public class Shoe extends Collectable {
	private String type;

	/**
	 * Creates a new shoe
	 *
	 * @param x    X-Coordinate of shoe
	 * @param y    Y-Coordinate of shoe
	 * @param type Type of shoe (boots/flipper)
	 */
	public Shoe(int x, int y, String type) {
		super(x, y, getSprite(type.toLowerCase()), 0);
		this.type = type.toLowerCase();
	}

	private static String getSprite(String type) {
		String sprite = "assets/";
		if (type == "boots") {
			sprite += "Boots.png";
		} else {
			sprite += "placeholder.png";
		}
		return sprite;
	}

	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() &&
			this.type.equals("flippers")) {
			player.addFlippers();
			board.removeInteractable(this);
		} else if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() &&
			this.type.equals("boots")) {
			player.addBoots();
			board.removeInteractable(this);
		}
	}
}