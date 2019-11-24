import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Drawable.java 
 * Represents a single cell on the board
 * 
 * @version 0.1
 * @author Ewan Bradford, Luke Francis
 */
public abstract class Drawable {
	protected int xCoord;
	protected int yCoord;
	private String spritePath;
	// blockable = 0-all can move on, 1-enemies cannot move on, 2-no one can move on
	private int blockable;
	private Image spriteImage;

	/**
	 * Creates drawable using position, an image file and a blocking value
	 * 
	 * @param x,         the x coordinate on the board this cell is at.
	 * @param y,         the y coordinate on the board this cell is at.
	 * @param sprite,    the filepath to the image file to be drawn
	 * @param blockable, the blocking value of this cell. (0-Blocks nothing,
	 *                   1-Blocks enemies, 2-Blocks enemies and player)
	 */
	public Drawable(int x, int y, String sprite, int blockable) {
		this.xCoord = x;
		this.yCoord = y;
		this.spritePath = sprite;
		this.blockable = blockable;
		
		this.spriteImage = new Image(spritePath); // "../assest/sprite.png"
	}

	/**
	 * @return Returns this objects blocking value.
	 */
	public int getBlocking() {
		return this.blockable;
	}

	public void draw(GraphicsContext gc) {
		gc.drawImage(this.spriteImage, this.xCoord*GameManager.CELL_SIZE, this.yCoord*GameManager.CELL_SIZE);
	}
}
