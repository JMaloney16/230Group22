/**
 * FollowEnemy.java, implements the follow enemy behaviour
 * 
 * @version 0.1
 * @author Ewan Bradford
 */
public class FollowEnemy extends Enemy {
	private int bias; // 1 right bias, -1 left bias
	private boolean forceMove;

	/**
	 * Creats a new follow enemy using a position, a direction and a search bias
	 * 
	 * @param x,         x coordinate of the enemy
	 * @param y,         y coordinate of the enemy
	 * @param direction, the starting direction of the enemy
	 * @param bias,      the searching bias of the enemy (-1 for left bias, 1 for
	 *                   right bias)
	 */
	public FollowEnemy(int x, int y, int direction, int bias) {
		super(x, y, "assets\\Spider.png", 1, direction);
		this.bias = bias;
		this.forceMove = false;
	}

	/**
	 * Does one step of the follow enemy's behaviour
	 * @param board,      the board object for the fire to be "placed" in.
	 * @param player,     the player.
	 * @param keyboardIn, the current key pressed.
	 */
	public void update(Board board, Player player, int keyboardIn) {
		normaliseDir();
		boolean front = getDirBlocking(board);
		boolean search = getDirBlocking(this.getDir() + bias, board);

		if (front == true && search == true) {
			this.changeDir(-bias);
			this.forceMove = false;
		} else if ((search == true && front == false) || (this.forceMove == true && front == false)) {
			this.forceMove = false;
			move();
		} else if (search == false) {
			this.changeDir(bias);
			this.forceMove = true;
		}

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
	 * Get the search bias of this enemy, (-1 left bias, 1 right bias)
	 * 
	 * @return the search bias
	 */
	public int getBias() {
		return bias;
	}
}
