
public class Water extends Tile {

	public Water(int x, int y) {
		super(x, y, "../assets/Water.png", 1);
	}
	
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			if (player.getFlippers() == false) {
				player.kill();
			}
		}
	}
}
