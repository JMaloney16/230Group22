
/**
 * DumbEnemy.java, implements the dumb enemy behaviour
 * 
 * @version
 * @author Ewan Bradford
 */
public class DumbEnemy extends Enemy {
	/**
	 * Creates a new dumb enemy using a position and a direction
	 * 
	 * @param x,         x coordinate of the enemy
	 * @param y,         y coordinate of the enemy
	 * @param direction, the starting direction of the enemy
	 */
	public DumbEnemy(int x, int y, int direction) {
		super(x, y, "assets\\Mummy.png", 1, direction);
	}

	/**
	 * Does one step of the dumb enemy's behaviour
	 * @param board,      the board object for the enemy to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		normaliseDir();
		float deltax = player.xCoord - this.xCoord;
		float deltay = this.yCoord - player.yCoord;
		float theta = (float) (Math.atan2(-deltay, deltax) * (180.0 / Math.PI));
		int angle = convertAngle(theta);

		if ((angle >= 315 && angle <= 360) || (angle > 0 && angle <= 45)) {
			this.setDir(0);
		}
		if (angle >= 45 && angle <= 135) {
			this.setDir(1);
		}
		if (angle >= 135 && angle <= 225) {
			this.setDir(2);
		}
		if (angle >= 225 && angle <= 315) {
			this.setDir(3);
		}

		boolean front = this.getDirBlocking(board);

		if (front == false) {
			this.move();
		}

		this.nextX = this.xCoord;
		this.nextY = this.yCoord;

		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			if (player.getKatanna()) {
				SoundEffect.playSound("assets\\Sounds\\Clang.wav");
				board.removeMovable(this);
				player.removeKatanna();
			} else {
				player.kill();
			}
		}
	}

	/**
	 * Converts the angle to positive value and clockwise referenced, take degrees
	 * 
	 * @param angle raw angle
	 * @return converted and cleaned angle, (0 degrees is noon, clockwise)
	 */
	private int convertAngle(float angle) {
		angle += 90;
		if (angle < 0) {
			angle = 360 + angle;
		}
		return (int) angle;
	}
}
