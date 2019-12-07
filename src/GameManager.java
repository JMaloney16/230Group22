import java.io.File;
import java.io.IOException;
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
import javafx.scene.image.Image;
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

	private int frameCount = 100;
	
	private Image TheAlmightyLiamOreillyLadOfAllLadsAndSaviourHimself;

	/**
	 * Creates a new GameManager with a stage, boardfile, window size and a cell
	 * size.
	 * 
	 * @param primaryStage, the stage for the game to be drawn to
	 * @param boardLevel,    the number of the current level
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

		//File imageLoader = new File("assets\\TheAlmightyLiamOreillyLadOfAllLadsAndSaviourHimself.jpg");
		//this.TheAlmightyLiamOreillyLadOfAllLadsAndSaviourHimself = new Image(imageLoader.toURI().toString());
		
		if (boardLevel == -1) {
			FileManager.FileReading.readPlayerFile("profiles\\" + playerName + ".txt", this.player, this.board);
			this.moves = this.player.getCurrentMoves();
			this.boardFile = "levels\\" + Integer.toString(this.player.getCurrentLevel()) + ".txt";
		} else {	
			this.boardFile = "levels\\" + Integer.toString(boardLevel) + ".txt";
			FileManager.FileReading.readMapFile(this.boardFile, this.board, this.player);
			this.player.setMaxLevel(maxLevel);
		}
		this.updateLeveling();

		this.createGameScene();

		primaryStage.setScene(this.gameScene);
		this.stage = primaryStage;

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), ae -> {
			try {
				update();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Draws and Updates all parts of the board, draws and handles input for the
	 * player
	 */
	private void update() throws IOException {
		this.frameCount++;
		gc.clearRect(0, 0, this.windowWidth, this.windowWidth);

		this.board.drawBoard(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawMovables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawInteractables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.player.draw(this.gc);
		
		if (this.frameCount < 20) {
			this.gc.drawImage(this.TheAlmightyLiamOreillyLadOfAllLadsAndSaviourHimself, 0, 0, this.windowWidth, this.windowHeight);
		}
		
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
			
			this.player.update(this.board, this.lastKey);

			this.board.updateBoard(this.player, this.lastKey);
			this.board.updateMovables(this.player, this.lastKey);
			
			if (this.player.getKilled()) {
				this.restart();
			}
			if (this.player.getCompleted()) {
				this.nextLevel();
			} else {
				this.board.updateInteractables(this.player, -1);
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
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
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
		this.frameCount = 0;
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
	private void nextLevel() throws IOException {
//		System.out.println("Level has been completed");
		//Get the leaderboard and compare the players time to each of the high scores

		FileManager.FileWriting.updateLeaderboard(this.boardFile, player.getName(), this.moves);

		this.board.setLevelNumber(this.board.getLevelNumber() + 1);
		this.boardFile = "levels\\" + Integer.toString(this.board.getLevelNumber()) + ".txt";
		System.out.println(this.boardFile);

//		if (this.board.getLevelNumber() > this.player.getMaxLevel()) {
//			this.player.setMaxLevel(this.board.getLevelNumber());
//		}
		this.restart();
	}
	
	private void updateLeveling() {
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
		String[] inventory = new String[] {
				Integer.toString(this.moves),
				"", "", "", "", //Keys to be added later in method
				Integer.toString(this.player.getTokens()),
				Boolean.toString(this.player.getBoots()),
				Boolean.toString(this.player.getFlippers()),
				Boolean.toString(this.player.getKatanna())
				
		};
		
		for(int i = 1; i < 5; i++) {
			inventory[i] = Integer.toString(this.player.getKeys()[i - 1]);
		}
		
		return inventory;
	}
	
	
	//Drawing the inventory at the top of the screen
	private HBox drawInventory(boolean firstDraw) {
		HBox inventoryHBox = new HBox();
		inventoryHBox.setSpacing(8);
		String[] labelNames = new String[]{"TIME", "R", "B", "Y", "G",
				"TOKEN", "BOOTS", "FLIPPER", "KATANNA"};
		Font font = new Font("Arial", 12);
		
		if(firstDraw == false) {
			this.toolbar.getChildren().remove(this.toolbar.getChildren().size() - 1);
		}
		
		
		for(int i = 0; i < 8; i++) {
			VBox box = new VBox();
			box.setAlignment(javafx.geometry.Pos.CENTER);
			Label name = new Label(labelNames[i]);
			name.setFont(font);
			Label value = new Label(this.getPlayerInventory()[i]);
			value.setFont(font);
			
			box.getChildren().addAll(name, value);
			inventoryHBox.getChildren().add(box);
		}
		
		
		return inventoryHBox;
		
	}
	
	
}
