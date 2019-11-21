import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

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
		// Need Player Constructor this.player = new Player();
		//Need Board Constructor this.board = new Board();
	}
	
	//Waits for a key input and updates the game according to the input
	public void update() {
		//Requires scene.addEventFilter anonymous inner class 
		
		Scene scene = new Scene();(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
		//InputManager im = new InputManager();
		//char input = im.processKeyEvent()
	}
	
	//Restarts the level when the Player dies
	private void restart() {
		
	}
}
