
public class Teleporter extends Tile {

	private Teleporter partner;

	public Teleporter(int x, int y) {
		super(x, y, "assets/placeholder.png", 0);
		// TODO Auto-generated constructor stub
	}

	public void setPartner(Teleporter partner) {
		this.partner = partner;
	}

	@Override
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getxCoord() && this.yCoord == player.yCoord) {
			player.nextX = partner.xCoord;
			player.nextY = partner.yCoord;
		}
	}
}
