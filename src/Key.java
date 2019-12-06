/**
 * Key.java A class designed to hold the unique attributes and methods required
 * for a key, whilst inheriting the attributes from Collectable and Interactable
 * 
 * @see Collectable
 * @see Interactable
 * @author Dylan Cole - 980660
 * @author Jack Maloney
 * @version 0.5
 */
public class Key extends Collectable {
	private String colour;

	/**
	 * Constructs a new Key
	 *
	 * @param x,y,    the coordinates of the Key
	 * @param colour, the colour of the key.
	 */
	public Key(int x, int y, String colour) {
		super(x, y, getSprite(colour.toLowerCase()), 0);
		this.colour = colour.toLowerCase();
	}

	/**
	 * Finds the corresponding sprite according to the key's colour
	 *
	 * @param keyColour The colour of the key
	 * @return The filepath to the corresponding sprite
	 */
	private static String getSprite(String keyColour) {
		String sprite = "assets//";
		switch (keyColour) {
		case "red":
			sprite += "keyRed.png";
			break;
		case "blue":
			sprite += "keyBlue.png";
			break;
		case "yellow":
			sprite += "keyYellow.png";
			break;
		case "green":
			sprite += "keyGreen.png";
			break;
		default:
			// If the colour doesn't exist just set it to one of the others?
			sprite += "keyRed.png";
		}
		return sprite;
	}

	/**
	 * Handles the collection of the key.
	 *
	 * @param board,      the parent board object.
	 * @param player,     the player object that is on the board.
	 * @param keyboardIn, the current keypress.
	 */
	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if ((player.getxCoord() == this.xCoord) && (player.getyCoord() == this.yCoord)) {
			player.addKey(this.colour);
			board.removeInteractable(this);
		}
	}

	/**
	 * Returns the colour of the key
	 *
	 * @return colour, the colour of the key.
	 */
	public String getColour() {
		return this.colour;
	}

	/**
	 * Changes the colour of the key
	 *
	 * @param colour, the colour of the key.
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
}
