/**
 * Coloured.java 
 * 
 * @version 0.1
 * @author Gino Sesia
 *
 */
public class ColouredDoor extends Door {
	
	private String colour;

	/**
	 * @param x,      the x coordinate of the fire tile.
	 * @param y,      the y coordinate of the fire tile.
	 */
	public ColouredDoor(int x, int y, String colour) {
		// TODO Auto-generated constructor stub
		super(x, y);
		this.colour = colour;
	}
	
	public void update(Board board, Player player, int keyboardIn) {
		if(player.checkKey(this.colour) == true && this.opened == false) {
			switch (keyboardIn) {
			case 0:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() - 1) {
					this.open(player);
				}
				break;
			case 1:
				if (this.xCoord == player.getxCoord() + 1 && this.yCoord == player.getyCoord()) {
					this.open(player);
				}
				break;
			case 2:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() + 1) {
					this.open(player);
				}
				break;
			case 3:
				if (this.xCoord == player.getxCoord() - 1 && this.yCoord == player.getyCoord()) {
					this.open(player);
				}
				break;
			}
		}
	}
	
	private void open(Player player) {
		this.setBlocking(0);
		this.opened = true;
		this.updateSprite("assets\\Floor.png");
	}
}