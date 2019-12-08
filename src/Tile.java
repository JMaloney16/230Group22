/**
 * Tile.java places a tile on the board
 *
 * @author Gino Sesia
 * @version 0.2
 */

public abstract class Tile extends Interactable {
	/**
	 * Creates a tile on the board
	 * 
	 * @param x,         x coordinate of the tile
	 * @param y,         y coordinate of the tile
	 * @param sprite,    filepath to the image to represnt this tile
	 * @param blockable, the blocking value of this tile (0 - does not block, 1 -
	 *                   blocks enemies, 2 - block all)	
	 */
	public Tile(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}
}
