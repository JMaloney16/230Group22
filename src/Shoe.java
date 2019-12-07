/**
 * Shoe.java Holds information about a key - no jack it doesnt
 * 
 * ######################################################################################################
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
		super(x, y, getSprite(type.toLowerCase()), 1);
		this.type = type.toLowerCase();
	}

	/**
	 * Sets the sprite image path needed for this shoes type
	 * 
	 * @param type of shoe this is
	 * @return the filepath the image to represent this type
	 */
	private static String getSprite(String type) {
		String sprite = "assets/";
		if (type == "boots") {
			sprite += "Boots.png";
		} else {
			sprite += "Flippers.png";
		}
		return sprite;
	}

	/**
	 * Handles the pick up of the shoe
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() && this.type.equals("flippers")) {
			player.addFlippers();
			board.removeInteractable(this);
		} else if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()
				&& this.type.equals("boots")) {
			player.addBoots();
			board.removeInteractable(this);
		}
	}

	public String getType() {
		return type;
	}
}