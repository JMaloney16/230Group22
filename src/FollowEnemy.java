
public class FollowEnemy extends Enemy {
	private int bias; // 1 right bias, -1 left bias
	private boolean forceMove;

	public FollowEnemy(int x, int y, int direction, int bias) {
		super(x, y, "assets\\placeholder.png", 1, direction);
		this.bias = bias;
		this.forceMove = false;
	}

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
			player.kill();
		}
	}
}
