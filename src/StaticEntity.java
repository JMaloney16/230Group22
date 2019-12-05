/**
 * StaticEntity.java, implements the simple static tiles of the game
 * 
 * @version 0.1
 * @author Ewan Bradford
 */
public class StaticEntity extends Drawable {
	/**
	 * Creates a new static entity from a position, imagepath and a blocking value
	 * 
	 * @param x,         x coordinate of the tile
	 * @param y,         y coordinate of the tile
	 * @param sprite,    filepath to the image to represnt this tile
	 * @param blockable, the blocking value of this tile (0 - does not block, 1 -
	 *                   blocks enemies, 2 - block all)
	 */
	public StaticEntity(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}
}
