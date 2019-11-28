/**
 * Teleporter.java 
 * 
 * @version 0.1
 * @author ginosesia
 *
 */
public class Teleporter extends Tile {

	/**
	 * Creates a teleporter tile.
	 * 
	 * @param x,      the x coordinate of the teleporter tile.
	 * @param y,      the y coordinate of the teleporter tile.
	 */
	private Teleporter partner;
	
	public Teleporter(int x, int y) {
		super(x, y, "assets/Teleporter.png", 0);
	}
	
	public void setPartner(Teleporter partner) {
		this.partner = partner;
	}
	
	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			player.nextX = player.xCoord;
			player.nextY = player.yCoord;
		}
	}

}
