/**
 * Shoe.java
 * Holds information about a key
 * @see Collectable
 * @see Interactable
 * @author Jack Maloney
 * @version 0.5
 */
public class Shoe extends Collectable {
	private String type;

	/**
	 * Creates a new shoe
	 * @param x X-Coordinate of shoe
	 * @param y Y-Coordinate of shoe
	 * @param type Type of shoe (boots/flipper)
	 */
	public Shoe(int x, int y, String type) {
		super(x, y, getSprite(type.toLowerCase()), 0);
		this.type = type.toLowerCase();
	}

	private static String getSprite(String type) {
		String sprite = "../assets/";
		if (type == "boots") {
			sprite += "placeholder.png";
		} else {
			sprite += "placeholder.png";
		}
		return sprite;
	}
	
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY() && this.type == flipper) {
				addFlippers();
				board.removeInteractable(flipper);
			}
	
	public void update(Board board, Player player, int keyboardIn) {
	if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY() && this.type == boots) {
				addBoots();
				board.removeInteractable(boots);
		}

}
