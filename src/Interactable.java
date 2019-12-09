/**
 * Interactable.java, abstract structure class that distinguishes static
 * entities to ones that change state
 * 
 * @version 0.1
 * @author Ewan Bradford
 * @author Will Marsh
 */
public abstract class Interactable extends StaticEntity {

	/**
	 * Constructor for an Interactable
	 * @param x X coordinate on the board
	 * @param y Y coordinate on the board
	 * @param sprite Name of the child of Interactable's Image file
	 * @param blockable child of Interactable's blockable status
	 */
	public Interactable(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}

	/**
	 * @param board,      the board object for the fire to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 */
	public void update(Board board, Player player, int keyboardIn) {

	}
}
