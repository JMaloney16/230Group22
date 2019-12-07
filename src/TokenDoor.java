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
	 * Creates a door tile.
	 * 
	 * @param x, the x coordinate of the door tile.
	 * @param y, the y coordinate of the door tile.
	 */
	public TokenDoor(int x, int y, int amount) {
		super(x, y, "assets\\Door.png");
		this.threshold = amount;
	}

	/**
	 * Updates the door, checks player position and behaves accordingly
	 */
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

	public int getThreshold() {
		return threshold;
	}

	/**
	 * Sets the door to be open
	 * 
	 * @param player, the player active in the game
	 * @param board,  the board this door is apart of
	 */
	private void open(Player player, Board board) {
		SoundEffect.playSound("assets\\sounds\\Door.wav");
		this.setBlocking(0);
		this.opened = true;

		this.updateSprite("assets\\Floor.png");
		board.removeInteractable(this);
	}

}
