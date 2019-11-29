/**
 * Token.java
 * A class designed to hold the unique methods
 * required for a Token, whilst inheriting the 
 * attributes from Collectable and Interactable
 * @version 0.1
 * @author Ewan Bradford
 */
public class Token extends Collectable {

	public Token(int x, int y) {
		super(x, y, "assets/Token.png", 0);
	}
	
	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if (player.getxCoord() == this.xCoord && player.getyCoord() == this.yCoord) {
			player.addToken(1);
			board.removeInteractable(this);
		}
	}

}
