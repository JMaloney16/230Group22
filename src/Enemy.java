/**
 * Enemy.java holds the common behaviours of enemies
 * 
 * @version 0.1
 * @author Ewan Bradford
 */
public abstract class Enemy extends Movable {
	private int dir;

	/**
	 * Constructor for the common attributes of enemies
	 * @param x, x coordinate of the enemy
	 * @param y, y coordinate of the enemy
	 * @param sprite, the filepath to the image representing this enemy
	 * @param blockable, the blocking value of this enemy
	 * @param direction, the direction the enemy is facing
	 */
	public Enemy(int x, int y, String sprite, int blockable, int direction) {
		super(x, y, sprite, blockable);
		this.dir = direction;
	}

	/** Checks the blocking value of the tile infront of this enemy
	 * @param board, the board this enemy is on
	 * @return a boolean true if the tile infront is blocking this enemy
	 */
	protected boolean getDirBlocking(Board board) {
		return this.getDirBlocking(this.dir, board);
	}

	/** Checks the blocking value of the tile in the direction of this enemy
	 * @param dir, direction to check in
	 * @param board, the board this enemy is on
	 * @return a boolean true if the tile in the direction is blocking this enemy
	 */
	protected boolean getDirBlocking(int dir, Board board) {
		dir = normaliseDir(dir);
		if (this.xCoord < 1 || this.xCoord > board.getBoard().length - 1 || this.yCoord < 1
				|| this.yCoord > board.getBoard()[0].length - 1) {
			return true;
		}

		switch (dir) {
		case 0:
			return board.getBlocking(this.xCoord, this.yCoord - 1) > 0;
		case 1:
			return board.getBlocking(this.xCoord + 1, this.yCoord) > 0;
		case 2:
			return board.getBlocking(this.xCoord, this.yCoord + 1) > 0;
		case 3:
			return board.getBlocking(this.xCoord - 1, this.yCoord) > 0;
		default:
			return true;
		}
	}

	/**
	 * Move one step in the direction that the enemy is facing
	 */
	protected void move() {
		switch (dir) {
		case 0:
			this.yCoord -= 1;
			break;
		case 1:
			this.xCoord += 1;
			break;
		case 2:
			this.yCoord += 1;
			break;
		case 3:
			this.xCoord -= 1;
			break;
		}
	}

	/** Change the enemies direction by an amount
	 * @param delta, the magnitude of the change
	 */
	protected void changeDir(int delta) {
		this.dir += delta;
		normaliseDir();
	}

	/** Sets the direction of the enemy
	 * @param dir, the new direction of this enemy
	 */
	protected void setDir(int dir) {
		this.dir = dir;
		normaliseDir();
	}

	/** Forces the value of direction to be 0 to 3
	 * @param dir, direction to be normalised
	 * @return the normalised direction
	 */
	protected int normaliseDir(int dir) {
		if (dir < 0) {
			return 3;
		}
		if (dir > 3) {
			return dir & 3;
		}
		return dir;
	}

	/**
	 * Normalises this enemies current direction
	 */
	protected void normaliseDir() {
		this.dir = this.normaliseDir(this.dir);
	}

	/**
	 * @return the direction the this enemy is facing in
	 */
	protected int getDir() {
		return this.dir;
	}
}
