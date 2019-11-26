/**
 * Key.java
 * A class designed to hold the unique attributes
 * and methods required for a key, whilst inheriting 
 * the attributes from Collectable and Interactable
 * @version 0.1
 * @author Dylan Cole - 980660
 */
public class Key extends Collectable {
	private String colour;

	/**
	 * Constructs a new Key
	 * @param int x,y, 			the co-ordinates of the Key 
	 * @param String sprite, 	the name of the image file used to represent key
	 * @param blockable, 		is the player going to be blocked by it
	 * @param colour, 			the colour of the key.
	 */
	public Key(int x, int y, String sprite, int blockable, String colour) {
		super(x, y, sprite, blockable);
		this.colour = colour;
	}
	
	/**
	 * Handles the collection of the key.
	 * 
	 * @param board,      the parent board object.
	 * @param player,     the player object that is on the board.
	 * @param keyboardIn, the current keypress.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if ((player.x == this.x) && (player.y == this.y)){
			addKey(this.colour);
			board.removeInteractable(this);
		}
	}
	
	/**
	 * Returns the colour of the key
	 * @return colour,			the colour of the key.
	 */
	public String getcolour(){
		return this.colour;
	}
	
	/**
	 * Changes the colour of the key
	 * @param colour,			the colour of the key.
	 */
	public void setcolour(String colour){
		this.colour = colour;
	}
}
