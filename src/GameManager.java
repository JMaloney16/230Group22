import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
	private HBox toolbar;

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
	public GameManager(Stage primaryStage, int boardLevel, String playerName, int maxLevel, int windowWidth, int windowHeight,
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
			this.player.setMaxLevel(maxLevel);
		}
		System.out.println(this.player.getMaxLevel());
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
		
		//Gets inventory before a move is made
		String[] previousInventory = this.getPlayerInventory();
		
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
		
		//Gets inventory after move is made
		String[] currentInventory = this.getPlayerInventory();
		
		//Compares whether the player inventory has changed and updates if it has
		if(!Arrays.equals(previousInventory, currentInventory)) {
			this.toolbar.getChildren().add(this.drawInventory(false));
		}
		
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
		this.toolbar = new HBox();
		this.toolbar.setSpacing(10);
		this.toolbar.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(this.toolbar);

		// Exit Button
		Button exitButton = new Button("Exit");
		this.toolbar.getChildren().add(exitButton);
		exitButton.setOnAction(e -> {
			Pane menu = MenuManager.Menu.buildMenuGUI(this.stage, this.windowWidth, this.windowHeight); // Build the GUI
			Scene scene = new Scene(menu, this.windowWidth, this.windowHeight); // Create a scene from the GUI
			this.stage.setScene(scene);
		});

		// Restart Button
		Button restartButton = new Button("Restart Level");
		this.toolbar.getChildren().add(restartButton);
		restartButton.setOnAction(e -> {
			this.restart();
		});
		
		this.toolbar.getChildren().add(drawInventory(true));

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
		String oldPlayerName = this.player.getName();
		int oldPlayerLevel = this.player.getCurrentLevel();
		int oldPlayerMaxLevel = this.player.getMaxLevel();
		this.moves = 0;
		this.player = new Player(0, 0, oldPlayerMaxLevel);
		this.player.setCurrentLevel(oldPlayerLevel);
		this.player.setName(oldPlayerName);
		System.out.println("dont die next time.");
		this.board = new Board(null, new ArrayList<Movable>(), new ArrayList<Interactable>());
//		this.player = new Player(0, 0, 0);
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
		System.out.println("ajshkdvhjdsagvfkhjgdsafjhgsdkfhjbgsdfsdffs");
		System.out.println(this.player.getMaxLevel());
		this.player.setCurrentLevel(this.board.getLevelNumber());

		if (this.player.getMaxLevel() < this.player.getCurrentLevel()) {
			this.player.setMaxLevel(this.player.getCurrentLevel());
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
		}
//		if (boardLevel > this.player.getCurrentLevel()) {
//			this.player.setCurrentLevel(boardLevel);
//		}
	}
	
	//Getting the whole player inventory (for comparisons)
	private String[] getPlayerInventory() {
		String[] inventory = new String[8];
		
		for(int i = 0; i < 4; i++) {
			inventory[i] = Integer.toString(this.player.getKeys()[i]);
		}
		inventory[4] = Integer.toString(this.player.getTokens());
		inventory[5] = Boolean.toString(this.player.getBoots());
		inventory[6] = Boolean.toString(this.player.getFlippers());
		inventory[7] = Boolean.toString(this.player.getKatanna());
		
		return inventory;
	}
	
	
	//Drawing the inventory at the top of the screen
	private HBox drawInventory(boolean firstDraw) {
		HBox inventoryHBox = new HBox();
		inventoryHBox.setSpacing(8);
		String[] labelNames = new String[]{"R", "B", "Y", "G",
				"TOKEN", "BOOTS", "FLIPPER", "KATANNA"};
		Font font = new Font("Arial", 10);
		
		if(firstDraw == false) {
			this.toolbar.getChildren().remove(this.toolbar.getChildren().size() - 1);
		}
		
		for(int i = 0; i < 8; i++) {
			VBox box = new VBox();
			Label name = new Label(labelNames[i]);
			name.setAlignment(javafx.geometry.Pos.CENTER);
			name.setFont(font);
			Label value = new Label(this.getPlayerInventory()[i]);
			value.setAlignment(javafx.geometry.Pos.CENTER);
			value.setFont(font);
			
			box.getChildren().addAll(name, value);
			inventoryHBox.getChildren().add(box);
		}
		
		return inventoryHBox;
		
	}
	
	
}
