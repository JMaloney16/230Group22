/**
 * Collectable.java A class designed to categorize the collectibles rather than
 * add anything new - this will inherit attributes from Interactables.
 * 
 * @version 0.1
 * @author Dylan Cole - 980660
 * @author Will Marsh
 */
public class Collectable extends Interactable {
	/**
	 * Constructs a new Collectable
	 * 
	 * @param x,         the x coordinate on the board this Collectable is at.
	 * @param y,         the y coordinate on the board this Collectable is at.
	 * @param String     sprite, the name of the image file used to represent key
	 * @param blockable, is the player going to be blocked by it
	 */
	public Collectable(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}
}
