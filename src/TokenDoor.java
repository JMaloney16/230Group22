/**
 * TokenDoor.java
 * 
 * @version 0.1
 * @author Gino Sesia
 *
 */
public class TokenDoor extends Door {
	private int threshold;

	/**
	 * Creates a water tile.
	 * 
	 * @param x, the x coordinate of the water tile.
	 * @param y, the y coordinate of the water tile.
	 */
	public TokenDoor(int x, int y, int amount) {
		super(x, y);
		this.threshold = amount;
	}

	public void update(Board board, Player player, int keyboardIn) {
		if (player.getTokens() >= this.threshold && this.opened == false) {
			switch (keyboardIn) {
			case 0:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() - 1) {
					this.open(player, board);
				}
				break;
			case 1:
				if (this.xCoord == player.getxCoord() + 1 && this.yCoord == player.getyCoord()) {
					this.open(player, board);
				}
				break;
			case 2:
				if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord() + 1) {
					this.open(player, board);
				}
				break;
			case 3:
				if (this.xCoord == player.getxCoord() - 1 && this.yCoord == player.getyCoord()) {
					this.open(player, board);
				}
				break;
			}
		}
	}
	private void open(Player player, Board board) {
		this.setBlocking(0);
		this.opened = true;
		player.addToken(-this.threshold);
		
		this.updateSprite("assets\\Floor.png");
		board.removeInteractable(this);
	}

}
