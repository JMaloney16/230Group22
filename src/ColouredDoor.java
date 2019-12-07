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
	 * @param x, 		the x coordinate of the coloured door tile.
	 * @param y, 		the y coordinate of the coloured door tile.
	 * @param colour,	the colour of the door.
	 */
	public ColouredDoor(int x, int y, String colour) {
		// TODO Auto-generated constructor stub
		super(x, y, "assets\\Door.png");
		switch (colour.toLowerCase()) {
		case "red":
			this.updateSprite("assets\\DoorRed.png");
			break;
		case "blue":
			this.updateSprite("assets\\DoorBlue.png");
			break;
		case "green":
			this.updateSprite("assets\\DoorGreen.png");
			break;
		case "yellow":
			this.updateSprite("assets\\DoorYellow.png");
			break;
		}
		this.colour = colour.toLowerCase();
	}

	/**
	 * Updates the door, checks player position and behaves accordingly
	 */
	public void update(Board board, Player player, int keyboardIn) {
		if (player.checkKey(this.colour) == true && this.opened == false) {
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

	public String getColour() {
		return colour;
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
		player.removeKey(this.colour);
		board.removeInteractable(this);
	}
}