/**
 * Teleporter.java
 * 
 * @version 0.2
 * @author ginosesia
 * @author Ewan Bradford
 *
 */
public class Teleporter extends Tile {

	/**
	 * Creates a teleporter tile.
	 * 
	 * @param x, the x coordinate of the teleporter tile.
	 * @param y, the y coordinate of the teleporter tile.
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
		// used to detected when the player is moving onto the tile
		if (this.partner != null) {
			switch (keyboardIn) {
			case 0:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() - 1) {
					player.setPosX(this.partner.xCoord);
					player.setPosY(this.partner.yCoord);
				}
				break;
			case 1:
				if (this.xCoord == player.getxCoord() + 1 && this.yCoord == player.getyCoord()) {
					player.setPosX(this.partner.xCoord);
					player.setPosY(this.partner.yCoord);
				}
				break;
			case 2:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() + 1) {
					player.setPosX(this.partner.xCoord);
					player.setPosY(this.partner.yCoord);
				}
				break;
			case 3:
				if (this.xCoord == player.getxCoord() - 1 && this.yCoord == player.getyCoord()) {
					player.setPosX(this.partner.xCoord);
					player.setPosY(this.partner.yCoord);
				}
				break;
			}
		}
	}

}
