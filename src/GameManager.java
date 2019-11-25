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
 * GameManager.java Controls flow of game and handles Player interaction with
 * the world
 * 
 * @version 0.2
 * @author Sam Forster
 * @author Ewan Bradford
 * @author ...
 */

public class GameManager {
	private Player player;      // Player object
	private Board board;       // Current Board object
	private String boardFile; // Filepath of the board (for restarts)
	private int gameState;   //==== Current state of game, 0 - game in play, 1 - resetting, 2 - quitting game
	private Scene gameScene;     // GUI scene for game (javaFX - Possible move to new class)
	private InputManager im;    // InputManager - handles input events
	private int time = 0;

	public final static int WINDOW_WIDTH; // fixed cell size
	public final static int WINDOW_HEIGHT; // fixed cell size
	public final static int CELL_SIZE; // fixed cell size

	private Canvas canvas; // Canvas for drawing the game on

	///////////////////////////////////////////////////////////////////

	public GameManager(String boardFile, int WINDOW_WIDTH, int WINDOW_HEIGHT, int CELL_SIZE) {
		this.WINDOW_WIDTH = WINDOW_WIDTH;
		this.WINDOW_HEIGHT = WINDOW_HEIGHT;
		this.CELL_SIZE = CELL_SIZE;
		
		this.boardFile = boardFile; // 
		this.player = null;
		this.board = null;
		FileManager.FileReading.readPlayerFile(boardFile, this.player, this.board);
		this.createGameScene();
		this.gameState = 1; // 0 - game in play, 1 - reset, 2 - quit
		this.im = new InputManager();
	}

	// Called when the game is launched from the menu
	public void launchGame() {
		while (this.gameState == 0) {
			this.update();
		}
		
		switch(this.gameState) {
		case 1:
			restart();
			break;
		case 2:
			System.out.println("Call quitting function");
			break;
		}
		System.out.println(this.gameState);
	}

	// Waits for a key input and updates the game according to the input
	private void update() {
		Integer keyPressed = null;
		this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> this.im.processKeyEvent(event, keyPressed));
		this.board.drawBoard(canvas);
		int playerStatus = -1;
		int keyboardIn = (int) keyPressed;
		if (keyboardIn != -1) {
			this.board.updateInteractables(this.player, keyboardIn);
			this.board.updateMovable(this.player, keyboardIn);
			playerStatus = this.player.update(this.board, keyboardIn);
			this.time += 1;
			
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
		}

		switch (playerStatus) {
		case 0:
			this.gameState = 0;
		case 1:
			this.gameState = 0;
		case 2:
			this.gameState = 1;
		}

//		String move = this.im.processKeyEvent(event);	//TALK TO JACK ABOUT ANOYMOUS INNER CLASSES + EVENT HANDLERS!!!
//		
//		if(this.checkAttemptMove(move)) {
//			this.movePlayer(move);
//		}
//		
//		drawGame();

	}

	// Restarts the level when the Player dies
	private void restart() {
		this.time = 0;
		System.out.println("dont die next time.");
	}

	// Checks availability of tile that player attempted to move to
	// (Sorry for repeated code)
//	private boolean checkAttemptMove(String moveToCheck) { // Getter for board2d array? maybe?
//		switch (moveToCheck) {
//		case "l": // Checks tile to left of player
//			if (this.board.getBoard()[player.getXCoord() - 1][player.getYCoord()].getBlockable() == 0) {
//				return true;
//			} else {
//				return false;
//			}
//		case "r": // Checks tile to right of player
//			if (this.board.getBoard()[player.getXCoord() + 1][player.getYCoord()].getBlockable() == 0) {
//				return true;
//			} else {
//				return false;
//			}
//
//		case "u": // Checks tile above player
//			if (this.board.getBoard()[player.getXCoord()][player.getYCoord() - 1].getBlockable() == 0) {
//				return true;
//			} else {
//				return false;
//			}
//
//		case "d": // Checks tile below player
//			if (this.board.getBoard()[player.getXCoord()][player.getYCoord() + 1].getBlockable() == 0) {
//				return true;
//			} else {
//				return false;
//			}
//
//		case "":
//			return false;
//
//		}
//	}

//	private void movePlayer(String moveToMake) {
//		this.player.update(board, keyboardIn); // TALK TO EWAN!!!
//	}

///////////////////////////////////////////////////////////////////////////////////////////////////////
	// JavaFX Scene

	// Creating the scene for the GUI - to JavaDoc
	private void createGameScene() {

		Pane root = this.buildGameGUI();
		this.gameScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> this.im.processKeyEvent(event));

	}

//	public void drawGame() {
//		// Get the Graphic Context of the canvas
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//
//		// Clear canvas
//		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//		// Parse through the board and draw each respective sprite
//		for (int x = 0; x < canvas.getHeight(); x++) {
//			for (int y = 0; y < canvas.getWidth(); y++) {
//				gc.drawImage(this.board.getBoard()[x][y].getSprite(), x * CELL_SIZE, y * CELL_SIZE);
//			}
//		}
//
//		// Draw player at current location
//		gc.drawImage(player.getSprite(), player.getXCoord() * CELL_SIZE, player.getYCoord() * CELL_SIZE);
//
//	}

	private Pane buildGameGUI() {
		BorderPane root = new BorderPane();
		canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT); // How to get size of map from 'board'
		root.setCenter(canvas);

		// HBar at top for Buttons
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(toolbar);

		// Exit Button
		Button exitButton = new Button("Exit");
		toolbar.getChildren().add(exitButton); // REQUIRES FUNCTIONALITY

		// Restart Button
		Button restartButton = new Button("Restart Level");
		toolbar.getChildren().add(restartButton); // REQUIRES FUNCTIONALITY

		return root;

	}

	// Gets GameScene - to JavaDoc
	public Scene getGameScene() {
		return this.gameScene;
	}
}
