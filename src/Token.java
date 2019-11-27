/**
 * Token.java
 * A class designed to hold the unique methods
 * required for a Token, whilst inheriting the 
 * attributes from Collectable and Interactable
 * @version 0.1
 * @author ...
 */
public class Token extends Collectable {

	public Token(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}

}
