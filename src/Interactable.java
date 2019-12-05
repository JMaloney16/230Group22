/**
 * Interactable.java, abstract structure class that distinguishes static
 * entities to ones that change state
 * 
 * @version 0.1
 * @author Ewan Bradford
 * @author Will Marsh
 */
public abstract class Interactable extends StaticEntity {

	public Interactable(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}

	public void update(Board board, Player player, int keyboardIn) {

	}
}
