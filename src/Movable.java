/**
 * Movable.java dictates the behaviour for all movable subclasses. Subclass of
 * Drawable.
 * 
 * @version 0.1
 * @author Ewan Bradford, Luke Francis
 */
public abstract class Movable extends Drawable {
	protected int nextX;
	protected int nextY;

	/**
	 * Movable classes instantiated from a position, image file and a blocking
	 * value.
	 * 
	 * @param x,         the x coordinate currently at.
	 * @param y,         the y coordinate currently at.
	 * @param sprite,    the filepath of the image for this movable.
	 * @param blockable, the blocking value for this movable. (0-Blocks nothing,
	 *                   1-Blocks enemies, 2-Blocks enemies and player)
	 */
	public Movable(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
		this.nextX = x;
		this.nextY = y;
	}

	/**
	 * Handles the movement and drawing of this object.
	 * 
	 * @param board,      the parent board object.
	 * @param player,     the player object that is on the board.
	 * @param keyboardIn, the current keypress.
	 */
	public void update(Board board, Player player, int keyboardIn) {

	}
}
