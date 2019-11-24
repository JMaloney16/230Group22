import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * GameManager.java
 * Controls flow of game and handles Player interaction with the world
 * @version 0.1
 * @author Sam Forster
 * @author ...
 * @author ...
 */


public class GameManager {
	private Player player;	//Player object
	private Board board;	//Current Board object
	private String boardFile;	//Filepath of the board (for restarts)
	private int gameState;	//Ask Ewan!!!
	private Scene gameScene;	//GUI scene for game (javaFX - Possible move to new class)
	private InputManager im;	//InputManager - handles input events
	
	private final int CELL_SIZE = 32;
	private final int TEMP_VALUE = 12;
	
	
	private Canvas canvas;	//Canvas for drawing the game on
	
	
	///////////////////////////////////////////////////////////////////
	
	public GameManager (FileManager fm) {
		int[] playerPos = fm.getStartPos();	//Start Position of player stored in 2 element array
		this.player = new Player(playerPos[0], playerPos[1], "playerPlaceholder.png");	
		this.board = fm.getBoard();
		this.boardFile = fm.getBoardFile();
		this.gameScene = this.createGameScene();
		this.gameState = 0;
		this.im = new InputManager();
	}
	
	//Called when the game is launched from the menu
	public void launchGame() {
		while(this.gameState == 0) {
			this.update();
		}
	}
	
	//Waits for a key input and updates the game according to the input
	private void update() {
		String move = this.im.processKeyEvent(event);	//TALK TO JACK ABOUT ANOYMOUS INNER CLASSES + EVENT HANDLERS!!!
		
		if(this.checkAttemptMove(move)) {
			this.movePlayer(move);
		}
		
		drawGame();
		
	}
	
	//Restarts the level when the Player dies
	private void restart() {
		
	}
	
	//Checks availability of tile that player attempted to move to
	//(Sorry for repeated code)
	private boolean checkAttemptMove(String moveToCheck) {		//Getter for board2d array? maybe?
		switch(moveToCheck){
			case "l":	//Checks tile to left of player
				if(this.board.getBoard()[player.getXCoord() - 1][player.getYCoord()].getBlockable() == 0) {
					return true;
				}else {
					return false;
				}
			case "r":	//Checks tile to right of player
				if(this.board.getBoard()[player.getXCoord() + 1][player.getYCoord()].getBlockable() == 0) {
					return true;
				}else {
					return false;
				}
				
			case "u":	//Checks tile above player
				if(this.board.getBoard()[player.getXCoord()][player.getYCoord() - 1].getBlockable() == 0) {
					return true;
				}else {
					return false;
				}
				
			case "d":	//Checks tile below player
				if(this.board.getBoard()[player.getXCoord()][player.getYCoord() + 1].getBlockable() == 0) {
					return true;
				}else {
					return false;
				}
			
			case "":
				return false;
				
		}
	}
	
	private void movePlayer(String moveToMake) {
		this.player.update(board, keyboardIn);	//TALK TO EWAN!!!
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////
	//JavaFX Scene
	
	//Creating the scene for the GUI - to JavaDoc
	private Scene createGameScene() {
		
		Pane root = this.buildGameGUI();
		this.gameScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		
		this.gameScene.addEventFilter(
				KeyEvent.KEY_PRESSED, event -> this.im.processKeyEvent(event)
		);
		
	}
	
	public void drawGame() {
		// Get the Graphic Context of the canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//Parse through the board and draw each respective sprite
		for (int x = 0; x < canvas.getHeight(); x++) {
			for (int y = 0; y < canvas.getWidth(); y++) {
				gc.drawImage(this.board.getBoard()[x][y].getSprite(), x * CELL_SIZE, y * CELL_SIZE);
			}
		}

		// Draw player at current location
		gc.drawImage(player.getSprite(), player.getXCoord() * CELL_SIZE, player.getYCoord() * CELL_SIZE);
		
	}
	
	private Pane buildGameGUI() {
		BorderPane root = new BorderPane();
		canvas = new Canvas(CELL_SIZE * BOARD_WIDTH, CELL_SIZE * BOARD_HEIGHT);	//How to get size of map from 'board' 
		root.setCenter(canvas);
		
		//HBar at top for Buttons
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10)); 
		root.setTop(toolbar);
		
		//Exit Button
		Button exitButton = new Button("Exit");
		toolbar.getChildren().add(exitButton);	//REQUIRES FUNCTIONALITY
		
		//Restart Button
		Button restartButton = new Button("Restart Level");
		toolbar.getChildren().add(restartButton);	//REQUIRES FUNCTIONALITY
		
		return root;
		
	}
	
	//Gets GameScene - to JavaDoc
	public Scene getGameScene() {
		return this.gameScene;
	}
}
