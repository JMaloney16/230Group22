
/**
 * GameManager.java
 * Controls flow of game and handles Player interaction with the world
 * @version 0.1
 * @author Sam Forster
 * @author ...
 * @author ...
 */


public class GameManager {
	private Player player;
	private Board board;
	private String boardFile;
	private int gameState;
	
	
	
	
	
	////////////////////////////////////
	
	public GameManager () {
		this.player = new Player();
		this.board = new Board();
	}
	
	//Waits for a key input and updates the game according to the input
	public void updateGame() {
		
	}
}
