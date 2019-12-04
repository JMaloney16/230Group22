
/**
 * DumbEnemy.java, implements the dumb enemy behaviour
 * 
 * @version
 * @author Ewan Bradford
 */
public class DumbEnemy extends Enemy {
	/** Creates a new dumb enemy using a position and a direction
	 * @param x, x coordinate of the enemy
	 * @param y, y coordinate of the enemy
	 * @param direction, the starting direction of the enemy
	 */
	public DumbEnemy(int x, int y, int direction) {
		super(x, y, "assets\\placeholder.png", 1, direction);
	}

	/**
	 * Does one step of the dumb enemy's behaviour
	 */
	public void update(Board board, Player player, int keyboardIn) {
		normaliseDir();
//float deltax = player.xCoord - this.xCoord;
//float deltay = player.yCoord - this.xCoord;
		float deltax = this.xCoord - player.xCoord;
		float deltay = this.yCoord - player.xCoord;
		float theta = (float) (Math.atan2(-deltay, deltax) * (180.0 / Math.PI));
		int angle = convertAngle(theta);

//		System.out.println(angle); // convertAngle((float) (Math.atan2(-1, 0)* (180.0 / Math.PI))));
//		System.out.printf("%f, %f\n", deltax, (float) (this.xCoord - player.xCoord));

		if ((angle > 315 && angle <= 360) || (angle > 0 && angle <= 45)) {
			this.setDir(0);
		}
		if (angle > 45 && angle <= 135) {
			this.setDir(1);
		}
		if (angle > 135 && angle <= 225) {
			this.setDir(2);
		}
		if (angle > 225 && angle <= 315) {
			this.setDir(3);
		}

		boolean front = this.getDirBlocking(board);

//if (front == false) {
//	this.move();
//}

		this.nextX = this.xCoord;
		this.nextY = this.yCoord;

		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.kill();
		}
	}

	private int convertAngle(float angle) {
		angle += 90;
		if (angle < 0) {
			angle = 360 + angle;
		}
		return (int) angle;
	}

//void update() {
//  float deltax = gx - ex;
//  float deltay = gy - ey;
//  float theta = atan2(deltay, deltax) * (180.0/PI);
//  dir = convertAngle(theta);
//  //dir = normaliseDir(dir);
//  dir = convertAngle(theta);
//  
//  if ((dir > 315 && dir <= 360) || (dir > 0 && dir <= 45)){
//    dir = 0;
//  }
//  if (dir > 45 && dir <= 135){
//    println("test");
//    dir = 1;
//  }
//  if (dir > 135 && dir <= 225){
//    dir = 2;
//  }
//  if (dir > 225 && dir <= 315){
//    dir = 3;
//  }
//
//  boolean front = getDir(ex, ey, dir);
//
//  println("dir", dir, "front", front, "angle", convertAngle(theta));
//
//  if (front == false) {
//    switch(dir) {
//    case 0:
//      ey -= 1;
//      break;
//    case 1:
//      ex += 1;
//      break;
//    case 2:
//      ey += 1;
//      break;
//    case 3:
//      ex -= 1;
//      break;
//    }
//
//    dir = normaliseDir(dir);
//  }
//}
}
