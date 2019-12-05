
/**
 * LineEnemy.java implements the line enemy behaviour
 * 
 * @version 0.1
 * @author Ewan bradford
 */
public class LineEnemy extends Enemy {
	/** Creates a new line enemy using a position and a starting direction
	 * @param x, x coordinate of the enemy
	 * @param y, y coordinate of the enemy
	 * @param direction, the starting direction of the enemy
	 */
	public LineEnemy(int x, int y, int direction) {
		super(x, y, "assets\\Mummy.png", 1, direction);
	}

	/**
	 * Does one step of the line enemy's behaviour
	 */
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
			if (player.getKatanna()){
				SoundEffect.playSound("assets\\Sounds\\Clang.wav");
				board.removeMovable(this);
				player.removeKatanna();
			} else {
				player.kill();
			}
		}
	}
}
