/**
 * TokenDoor.java
 * Holds information about a token door on the board
 * @version 1.0
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
	 * @param amount the amount of tokens needed for the door to open.
	 */
	public TokenDoor(int x, int y, int amount) {
		super(x, y, "assets\\Door.png");
		this.threshold = amount;
	}

	/**
	 * Updates the door, checks player position and behaves accordingly.
	 *
	 * @param board The board the door belongs to.
	 * @param player The player object on the board.
	 * @param keyboardIn The last input from the user.
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

	/**
	 * Gets the Threshold value for opening the door
	 * @return The threshold value for opening the door
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Sets the door to be open.
	 * 
	 * @param player, the player active in the game.
	 * @param board,  the board this door is apart of.
	 */
	private void open(Player player, Board board) {
		SoundEffect.playSound("assets\\sounds\\Door.wav");
		this.setBlocking(0);
		this.opened = true;

		this.updateSprite("assets\\Floor.png");
		board.removeInteractable(this);
	}
}
