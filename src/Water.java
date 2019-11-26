
public class Water extends Tile {

	public Water(int x, int y, String sprite, int blockable) {
		super(x, y, sprite, blockable);
	}
	
	public void update(Board board, Player player, int keyboardIn) {
		if (this.xCoord == player.getPosX() && this.yCoord == player.getPosY()) {
			if (player.getFlippers() == false) {
				player.kill();
			}
		}
	}
}
