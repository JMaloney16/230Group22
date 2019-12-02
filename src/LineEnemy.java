
public class LineEnemy extends Enemy {
	public LineEnemy(int x, int y, int direction) {
		super(x, y, "assets\\placeholder.png", 1, direction);
	}

	public void update(Board board, Player player, int keyboardIn) {
		normaliseDir();

		boolean front = this.getDirBlocking(board);

		if (front == true) {
			this.changeDir(2);
			normaliseDir();
		} else if (front == false) {
			move();			
		}
		
		this.nextX = this.xCoord;
		this.nextY = this.yCoord;
		
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.kill();
		}
	}
}
