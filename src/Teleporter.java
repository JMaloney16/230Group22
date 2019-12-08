/**
 * Teleporter.java
 * 
 * @version 0.2
 * @author Gino Sesia
 * @author Ewan Bradford
 * @author Jack Maloney
 */
public class Teleporter extends Tile {
	private Teleporter partner;

	/**
	 * Creates a teleporter tile.
	 * 
	 * @param x, the x coordinate of the teleporter tile.
	 * @param y, the y coordinate of the teleporter tile.
	 */
	public Teleporter(int x, int y) {
		super(x, y, "assets/Teleporter.png", 1);
	}

	/**
	 * Adds the destination teleporter
	 * 
	 * @param partner, teleporter object to be taken to
	 */
	public void setPartner(Teleporter partner) {
		this.partner = partner;
	}

	/**
	 * Updates the teleporter and moves the player if needed
	 */

	public void update(Board board, Player player, int keyboardIn) {
		// used to detected when the player is moving onto the tile
		if (this.partner != null) {
			switch (keyboardIn) {
			case 0:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
					player.setxCoord(this.partner.xCoord);
					player.setyCoord(this.partner.yCoord - 1);
				}
				break;
			case 1:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
					player.setxCoord(this.partner.xCoord + 1);
					player.setyCoord(this.partner.yCoord);
				}
				break;
			case 2:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
					player.setxCoord(this.partner.xCoord);
					player.setyCoord(this.partner.yCoord + 1);
				}
				break;
			case 3:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
					player.setxCoord(this.partner.xCoord - 1);
					player.setyCoord(this.partner.yCoord);
				}
				break;
			}
		}
	}

	/**
	 * @return the partner this teleporter is paired with
	 */
	public Teleporter getPartner() {
		return partner;
	}
}