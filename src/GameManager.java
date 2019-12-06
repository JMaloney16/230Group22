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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * GameManager.java Controls flow of game and handles Player interaction with
 * the world
 * 
 * @version 0.5
 * @author Ewan Bradford, Sam Forster
 */
public class GameManager {
	private Player player;
	private Board board;
	private String boardFile;

	private GraphicsContext gc;
	private Scene gameScene;
	private Stage stage;

	private int windowWidth;
	private int windowHeight;
	private int cellSize;

	private int moves = 0;

	private int lastKey = -1;

	private int frameCount = 0;

	// TODO
	// make sure it works when a playerfile is loaded to be continued
	// make cell size to work

	/**
	 * Creates a new GameManager with a stage, boardfile, window size and a cell
	 * size.
	 * 
	 * @param primaryStage, the stage for the game to be drawn to
	 * @param boardFile,    the file of the current level
	 * @param windowWidth,  width of the screen to be played on
	 * @param windowHeight, height of the screen to be played on
	 * @param cellSize,     size of each tile on the screen
	 */
	public GameManager(Stage primaryStage, int boardLevel, String playerName, int windowWidth, int windowHeight,
			int cellSize) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.cellSize = cellSize;

//		this.player = new Player(1, 1, "assets\\player.png", "test", 0);
		this.board = new Board(null, new ArrayList<Movable>(), new ArrayList<Interactable>());
		this.player = new Player(0, 0, 0);
		this.player.setName(playerName);

//		Drawable[][] temp = new Drawable[16][16];
//		for (int y = 0; y < 16; y++) {
//			for (int x = 0; x < 16; x++) {
//				if (x == 0 || x == 15 || y == 0 || y == 15) {
//					temp[x][y] = new StaticEntity(x, y, "assets\\stoneBrickWall.png", 2);
//				} else {
//					temp[x][y] = new StaticEntity(x, y, "assets\\Floor.png", 0);
//				}
//			}
//		}
		
		
		if (boardLevel == -1) {
			FileManager.FileReading.readPlayerFile("profiles\\" + playerName + ".txt", this.player, this.board);
			this.boardFile = "levels\\" + Integer.toString(this.player.getCurrentLevel()) + ".txt";
		} else {	
			this.boardFile = "levels\\" + Integer.toString(boardLevel) + ".txt";
			FileManager.FileReading.readMapFile(this.boardFile, this.board, this.player);
		}
		
		this.updateLeveling();

		this.createGameScene();

		primaryStage.setScene(this.gameScene);
		this.stage = primaryStage;

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), ae -> update()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Draws and Updates all parts of the board, draws and handles input for the
	 * player
	 */
	private void update() {
		this.frameCount++;
		gc.clearRect(0, 0, this.windowWidth, this.windowWidth);

		this.board.drawBoard(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawMovables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawInteractables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.player.draw(this.gc);

		this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> InputManager.processKeyEvent(event, this));
		if (this.lastKey != -1) {
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
			this.moves += 1;
			this.player.setCurrentMoves(this.moves);
//			System.out.print("key: ");
//			System.out.println(this.lastKey);
//			
//			System.out.print("player move: ");
//			System.out.println(this.player.update(this.board, this.lastKey));
			this.board.updateInteractables(this.player, this.lastKey);
			int playerState = this.player.update(this.board, this.lastKey);
			this.board.updateBoard(this.player, this.lastKey);
			this.board.updateMovables(this.player, this.lastKey);
			
			if (playerState == 2) {
				this.restart();
			}
			if (playerState == 3) {
				this.nextLevel();
			} else {
				this.board.updateInteractables(this.player, this.lastKey);
			}
//			System.out.printf("Player pos: %dx%d\n", this.player.getxCoord(), this.player.getyCoord());
		}
		this.lastKey = -1;

		this.player.draw(this.gc);
	}

	/**
	 * Sets up the gameScene so that the gameManager can draw the board to the
	 * screen
	 */
	private void createGameScene() {
		Pane root = this.buildGameGUI();
		this.gameScene = new Scene(root, windowWidth, windowHeight);
	}

	/**
	 * Adds all components to the screen ready for the game to be played
	 * 
	 * @return Returns the constructed Pane
	 */
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
		toolbar.getChildren().add(exitButton);
		exitButton.setOnAction(e -> {
			Pane menu = MenuManager.Menu.buildMenuGUI(this.stage, this.windowWidth, this.windowHeight); // Build the GUI
			Scene scene = new Scene(menu, this.windowWidth, this.windowHeight); // Create a scene from the GUI
			this.stage.setScene(scene);
		});

		// Restart Button
		Button restartButton = new Button("Restart Level");
		toolbar.getChildren().add(restartButton);
		restartButton.setOnAction(e -> {
			this.restart();
		});

		return root;
	}

	/**
	 * Allows the keyboard to be given to the gameManager
	 * 
	 * @param key, the direction of the key inputed
	 */
	public void setKey(int key) {
		this.lastKey = key;
	}

	/**
	 * Gets the current moves the player has made during their life
	 * 
	 * @return Int of the moves since last start
	 */
	public int getMoves() {
		return this.moves;
	}

	/**
	 * Restarts the game
	 */
	private void restart() {
		System.out.println("kjhasgdjhgdfjdsijfljsdhfgkj;sdfhnjkfgn;dfjkgjndfg;fdkdf;gjndgfgd");
		System.out.println(this.boardFile);
		this.moves = 0;
		this.player.setCurrentMoves(this.moves);
		System.out.println("dont die next time.");
		this.board = new Board(null, new ArrayList<Movable>(), new ArrayList<Interactable>());
		this.player = new Player(0, 0, 0);
		FileManager.FileReading.readMapFile(this.boardFile, this.board, this.player);
		this.updateLeveling();
	}

	/**
	 * Progesses the game to the next level
	 */
	private void nextLevel() {
		
//		System.out.println("Level has been completed");
		this.board.setLevelNumber(this.board.getLevelNumber() + 1);
		this.boardFile = "levels\\" + Integer.toString(this.board.getLevelNumber()) + ".txt";
		System.out.println(this.boardFile);

//		if (this.board.getLevelNumber() > this.player.getMaxLevel()) {
//			this.player.setMaxLevel(this.board.getLevelNumber());
//		}
		this.restart();
	}
	
	private void updateLeveling() {
		System.out.println("jashkdvjkhysagdhjkagsdjhygadsfdsf----------");
		this.player.setCurrentLevel(this.board.getLevelNumber());
		System.out.println(this.player.getCurrentLevel());
		System.out.println(this.player.getMaxLevel());
		if (this.player.getMaxLevel() < this.player.getCurrentLevel()) {
			System.out.println("asdfbv BLAR");
			this.player.setMaxLevel(this.player.getCurrentLevel());
			System.out.println(this.player.getCurrentLevel());
			System.out.println(this.player.getMaxLevel());
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
		}
//		if (boardLevel > this.player.getCurrentLevel()) {
//			this.player.setCurrentLevel(boardLevel);
//		}
	}
}
