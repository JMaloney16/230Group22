/**
 * Token.java A class designed to hold the unique methods required for a Token,
 * whilst inheriting the attributes from Collectable and Interactable
 * 
 * @version 0.1
 * @author Ewan Bradford
 * @author William Marsh
 */
public class Token extends Collectable {

	/**
	 * Creates a new token from with just a positon
	 * 
	 * @param x, x coordinate of the token
	 * @param y, y coordinate of the token
	 */
	public Token(int x, int y) {
		super(x, y, "assets/Token.png", 1);
	}

	/**
	 * Handles the token pickup
	 */
	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if (player.getxCoord() == this.xCoord && player.getyCoord() == this.yCoord) {
			player.addToken(1);
			board.removeInteractable(this);
		}
	}

}
