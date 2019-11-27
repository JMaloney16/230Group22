import java.io.File;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameManager {
	private Player player;
	private Board board;
	private String boardFile;

	private GraphicsContext gc;
	private Scene gameScene;

	private int windowWidth;
	private int windowHeight;
	private int cellSize;
	
	private int lastKey = -1;

	private int frameCount = 0;

	public GameManager(Stage primaryStage, String boardFile, int windowWidth, int windowHeight, int cellSize) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.cellSize = cellSize;

//		this.gc = gc;
		this.boardFile = boardFile;

		this.player = new Player(1, 1, "assets\\placeholder.png", "test", 0);

		Drawable[][] temp = new Drawable[7][7];
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				if (x == 0 || x == 6 || y == 0 || y == 6) {
					temp[x][y] = new StaticEntity(x, y, "assets\\Water.png", 2);
				} else {
					temp[x][y] = new StaticEntity(x, y, "assets\\Lava.png", 0);
				}
			}
		}
		temp[3][3] = new StaticEntity(3, 3, "assets\\Water.png", 2);
		this.board = new Board(temp, new ArrayList<Movable>(), new ArrayList<Interactable>());

		this.createGameScene();

		primaryStage.setScene(this.gameScene);

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), ae -> update()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private void update() {
		this.frameCount++;
//		System.out.println(this.frameCount);
		this.board.drawBoard(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.player.draw(this.gc);
		Integer keyPressed = new Integer(-1);
		this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> InputManager.processKeyEvent(event, this));
		if (this.lastKey != -1) {
			System.out.print("key: ");
			System.out.println(this.lastKey);
			
			System.out.print("player move: ");
			System.out.println(this.player.update(this.board, this.lastKey));
			System.out.printf("Player pos: %dx%d\n", this.player.getxCoord(), this.player.getyCoord());
		}
		this.lastKey = -1;
	}

	private void createGameScene() {
		Pane root = this.buildGameGUI();
		this.gameScene = new Scene(root, windowWidth, windowHeight);

		// this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event ->
		// this.im.processKeyEvent(event));

	}

	private Pane buildGameGUI() {
		BorderPane root = new BorderPane();
		Canvas canvas = new Canvas(windowWidth, windowHeight); // How to get size of map from 'board'
		root.setCenter(canvas);

		this.gc = canvas.getGraphicsContext2D();

//		File imageLoader = new File("src\\Lava.png");
//		Image test = new Image(imageLoader.toURI().toString());
////	System.out.println(test.isError());
////	System.out.println(test.exceptionProperty());
//		this.gc.drawImage(test, 100, 100, 64, 64);

//		this.board.drawBoard(this.gc);

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
	
	public void setKey(int key) {
		this.lastKey = key;
	}
}

//import java.io.File;
//import java.util.ArrayList;
//
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//
///**
// * GameManager.java Controls flow of game and handles Player interaction with
// * the world
// * 
// * @version 0.2
// * @author Sam Forster
// * @author Ewan Bradford
// * @author ...
// */
//
//public class GameManager {
//	private Player player; // Player object
//	private Board board; // Current Board object
//	private String boardFile; // Filepath of the board (for restarts)
//	private int gameState; // ==== Current state of game, 0 - game in play, 1 - resetting, 2 - quitting
//							// game
//	private Scene gameScene; // GUI scene for game (javaFX - Possible move to new class)
//	private Stage primaryStage;
//	private GraphicsContext gc;
//	private BorderPane root;
//	private InputManager im; // InputManager - handles input events
//	private int time = 0;
//
//	public static int windowWidth; // fixed cell size
//	public static int windowHeight; // fixed cell size
//	public static int cellSize; // fixed cell size
//
//	private Canvas canvas; // Canvas for drawing the game on
//
//	///////////////////////////////////////////////////////////////////
//
//	public GameManager(Stage primaryStage, String boardFile, int windowWidth, int windowHeight, int cellSize) {
//		this.primaryStage = primaryStage;
//		this.windowWidth = windowWidth;
//		this.windowHeight = windowHeight;
//		this.cellSize = cellSize;
//
//		this.boardFile = boardFile; //
//		this.player = new Player(1, 1, "../assets/placeholder.png", "test", 0);
//
//		Drawable[][] temp = new Drawable[7][7];
//		for (int y = 0; y < 7; y++) {
//			for (int x = 0; x < 7; x++) {
//				if (x == 0 || x == 6 || y == 0 || y == 6) {
//					temp[x][y] = new StaticEntity(x, y, "D:\\Documents\\GitHub\\230Group22\\src\\Water.png", 2);
//				}
//				else {
//					temp[x][y] = new StaticEntity(x, y, "D:\\Documents\\GitHub\\230Group22\\src\\Lava.png", 0);
//				}
//			}
//		}
//		this.board = new Board(temp, new ArrayList<Movable>(), new ArrayList<Interactable>());
//
////		FileManager.FileReading.readPlayerFile(boardFile, this.player, this.board);
//		this.createGameScene();
//		this.gameState = 0; // 0 - game in play, 1 - reset, 2 - quit
//		this.im = new InputManager();
//		
//		this.createGameScene();
//		this.buildGameGUI();
//		
//		this.primaryStage.setScene(this.gameScene);
//		
////		this.canvas = new Canvas()
//	}
//
//	// Called when the game is launched from the menu
//	public void launchGame() {
//		System.out.println("Launched Game");
////		while (this.gameState == 0) {
//			this.update();
//			this.primaryStage.setScene(this.gameScene);
////		}
//
////		switch (this.gameState) {
////		case 1:
////			restart();
////			break;
////		case 2:
////			System.out.println("Call quitting function");
////			break;
////		}
//		System.out.println(this.gameState);
//	}
//
//	// Waits for a key input and updates the game according to the input
//	private void update() {
////		Integer keyPressed = null;
////		this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> this.im.processKeyEvent(event, keyPressed));
//		
//		this.canvas = (Canvas)this.root.getCenter();
//		this.gc = this.canvas.getGraphicsContext2D();
//
//		this.board.drawBoard(this.gc);
//		
//		
//		File imageLoader = new File("src\\Water.png");
//		Image test = new Image(imageLoader.toURI().toString());
////		System.out.println(test.isError());
////		System.out.println(test.exceptionProperty());
//		this.gc.drawImage(test, 200, 100, 64, 64);
//		
//		this.root.setCenter(this.canvas);
//		this.primaryStage.setScene(this.gameScene);
//		this.primaryStage.show();
//		
//		
//		int playerStatus = -1;
//		
////		if (keyPressed != null) {
////			int keyboardIn = (int) keyPressed;
////			this.board.updateInteractables(this.player, keyboardIn);
////			this.board.updateMovable(this.player, keyboardIn);
////			playerStatus = this.player.update(this.board, keyboardIn);
////			this.time += 1;
////
//////			FileManager.FileWriting.savePlayerFile(this.player, this.board);
////		}
//
//		switch (playerStatus) {
//		case 0:
//			this.gameState = 0;
//		case 1:
//			this.gameState = 0;
//		case 2:
//			this.gameState = 1;
//		}
//
////		String move = this.im.processKeyEvent(event);	//TALK TO JACK ABOUT ANOYMOUS INNER CLASSES + EVENT HANDLERS!!!
////		
////		if(this.checkAttemptMove(move)) {
////			this.movePlayer(move);
////		}
////		
////		drawGame();
//
//	}
//
//	// Restarts the level when the Player dies
//	private void restart() {
//		this.time = 0;
//		System.out.println("dont die next time.");
//	}
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	// JavaFX Scene
//
//	// Creating the scene for the GUI - to JavaDoc
//	private void createGameScene() {
//
//		Pane root = this.buildGameGUI();
//		this.gameScene = new Scene(root, windowWidth, windowHeight);
//
//		// this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event ->
//		// this.im.processKeyEvent(event));
//
//	}
//
//	private Pane buildGameGUI() {
//		BorderPane root = new BorderPane();
//		this.canvas = new Canvas(windowWidth, windowHeight); // How to get size of map from 'board'
//		root.setCenter(this.canvas);
//		//////////////////////////
////		System.out.println(windowWidth);
//		this.gc = this.canvas.getGraphicsContext2D();
//		File imageLoader = new File("src\\Lava.png");
//		Image test = new Image(imageLoader.toURI().toString());
////		System.out.println(test.isError());
////		System.out.println(test.exceptionProperty());
//		this.gc.drawImage(test, 100, 100, 64, 64);
//		
//		this.board.drawBoard(this.gc);
//		/////////////////////////////
//
//		// HBar at top for Buttons
//		HBox toolbar = new HBox();
//		toolbar.setSpacing(10);
//		toolbar.setPadding(new Insets(10, 10, 10, 10));
//		root.setTop(toolbar);
//
//		// Exit Button
//		Button exitButton = new Button("Exit");
//		toolbar.getChildren().add(exitButton); // REQUIRES FUNCTIONALITY
//
//		// Restart Button
//		Button restartButton = new Button("Restart Level");
//		toolbar.getChildren().add(restartButton); // REQUIRES FUNCTIONALITY
//		
//		this.root = root;
//		return root;
//
//	}
//
//	// Gets GameScene - to JavaDoc
//	public Scene getGameScene() {
//		return this.gameScene;
//	}
//}
