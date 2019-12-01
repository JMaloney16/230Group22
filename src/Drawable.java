import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Drawable.java 
 * Represents a single cell on the board
 * 
 * @version 0.3
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
		
		if (sprite != "") { // add a check to make sure image has been loaded
			this.updateSprite(sprite);
		}
	}
	protected void updateSprite(String sprite) {
		this.spritePath = sprite;
		File imageLoader = new File(spritePath);
		this.spriteImage = new Image(imageLoader.toURI().toString()); // "../assest/sprite.png"
	}
	
	/**
	 * Handles the movement of this object.
	 * 
	 * @param board,      the parent board object.
	 * @param player,     the player object that is on the board.
	 * @param keyboardIn, the current keypress.
	 */
	public void update(Board board, Player player, int keyboardIn) {
	}

	/**
	 * @return Returns this objects blocking value.
	 */
	public int getBlocking() {
		return this.blockable;
	}
	
	public void setBlocking(int newBlocking) {
		if (newBlocking >= 0 && newBlocking <= 2) {
			this.blockable = newBlocking;
		}
	}

	/**
	 * Draws the sprite at the correct location and size
	 * @param gc graphics context to be drawn to
	 */
	public void draw(GraphicsContext gc, int offsetX, int offsetY) {
//		System.out.println(this.spriteImage.isError());
		gc.drawImage(this.spriteImage, (this.xCoord-offsetX)*64, (this.yCoord-offsetY)*64, 64, 64);
	}

	public int getxCoord() {
		return xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}
	
	public void sexCoord(int x) {
		this.xCoord = x;
	}
	public void seyCoord(int y) {
		this.yCoord = y;
	}
}
