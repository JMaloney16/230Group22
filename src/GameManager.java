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

	private int time = 0;
	private int previousTime = 0;
	private long startTime;
	private long currentTime;

	private int lastKey = -1;

	/**
	 * Creates a new GameManager with a stage, boardfile, window size and a cell
	 * size.
	 * 
	 * @param primaryStage, the stage for the game to be drawn to
	 * @param boardLevel,   the number of the current level
	 * @param player,		the player instance
	 * @param windowWidth,  width of the screen to be played on
	 * @param windowHeight, height of the screen to be played on
	 * @param cellSize,     size of each tile on the screen
	 */
	public GameManager(Stage primaryStage, int boardLevel, Player player, int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		this.board = new Board(null, new ArrayList<Movable>(), new ArrayList<Interactable>());
		this.player = player;

		this.startTime = System.currentTimeMillis();

		if (boardLevel == -1) {
			FileManager.FileReading.readPlayerFile("profiles\\" + player.getName() + ".txt", this.player, this.board);
			this.time = this.player.getCurrentTime();
			this.previousTime = this.time;
			this.boardFile = "levels\\" + Integer.toString(this.player.getCurrentLevel()) + ".txt";
		} else {
			this.boardFile = "levels\\" + Integer.toString(boardLevel) + ".txt";
			FileManager.FileReading.readMapFile(this.boardFile, this.board, this.player);
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
	 * Allows the keyboard to be given to the gameManager
	 * 
	 * @param key, the direction of the key inputed
	 */
	public void setKey(int key) {
		this.lastKey = key;
	}

	/**
	 * Draws and Updates all parts of the board, draws and handles input for the
	 * player
	 */
	private void update() throws IOException {
		gc.clearRect(0, 0, this.windowWidth, this.windowWidth);
		this.board.drawBoard(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawMovables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.board.drawInteractables(this.gc, this.player.getxCoord(), this.player.getyCoord());
		this.player.draw(this.gc);

		// Gets inventory before a move is made
		String[] previousInventory = this.getPlayerInventory();

		this.gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> InputManager.processKeyEvent(event, this));
		if (this.lastKey != -1) {
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
			currentTime = System.currentTimeMillis();
			time = (int) ((currentTime - startTime) / 1000);
			time += previousTime;

			this.player.setCurrentTime(time);
			this.board.updateInteractables(this.player, this.lastKey);

			if (this.player.update(this.board, this.lastKey) == 0) {
				SoundEffect.playSound("assets\\Sounds\\Bump.wav");
			}

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
		}
		this.lastKey = -1;

		this.player.draw(this.gc);

		// Gets inventory after move is made
		String[] currentInventory = this.getPlayerInventory();

		// Compares whether the player inventory has changed and updates if it has
		if (!Arrays.equals(previousInventory, currentInventory)) {
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
	 * Restarts the game
	 */
	private void restart() {
		String oldPlayerName = this.player.getName();
		int oldPlayerLevel = this.player.getCurrentLevel();
		int oldPlayerMaxLevel = this.player.getMaxLevel();
		this.time = 0;
		this.previousTime = 0;
		this.startTime = System.currentTimeMillis();
		this.player = new Player(0, 0, oldPlayerMaxLevel);
		this.player.setCurrentLevel(oldPlayerLevel);
		this.player.setName(oldPlayerName);
		System.out.println("dont die next time.");
		this.board = new Board(null, new ArrayList<Movable>(), new ArrayList<Interactable>());
		FileManager.FileReading.readMapFile(this.boardFile, this.board, this.player);
		this.updateLeveling();
	}

	/**
	 * Progresses the game to the next level
	 */
	private void nextLevel() throws IOException {
		// Get the leaderboard and compare the players time to each of the high scores
		FileManager.FileWriting.updateLeaderboard(this.boardFile, player.getName(), this.time);

		this.board.setLevelNumber(this.board.getLevelNumber() + 1);
		this.boardFile = "levels\\" + Integer.toString(this.board.getLevelNumber()) + ".txt";
		System.out.println(this.boardFile);

		this.restart();
	}

	/**
	 * Updates the players current and max levels
	 */
	private void updateLeveling() {
		this.player.setCurrentLevel(this.board.getLevelNumber());

		if (this.player.getMaxLevel() < this.player.getCurrentLevel()) {
			this.player.setMaxLevel(this.player.getCurrentLevel());
			FileManager.FileWriting.savePlayerFile(this.player, this.board);
		}
	}

	/**
	 * Gets the full player inventory in one array
	 * 
	 * @return String array of the players inventory
	 */
	private String[] getPlayerInventory() {
		String[] inventory = new String[] { Integer.toString(this.time), "", "", "", "", // Keys to be added later in
																							// method
				Integer.toString(this.player.getTokens()), Boolean.toString(this.player.getBoots()),
				Boolean.toString(this.player.getFlippers()), Boolean.toString(this.player.getKatanna()) };

		for (int i = 1; i < 5; i++) {
			inventory[i] = Integer.toString(this.player.getKeys()[i - 1]);
		}

		return inventory;
	}

	/**
	 * Draws the inventory to the screen
	 * 
	 * @param firstDraw, true if the first time this method has been called for this
	 *                   gameManager
	 * @return HBox containing the inventory
	 */
	private HBox drawInventory(boolean firstDraw) {
		HBox inventoryHBox = new HBox();
		inventoryHBox.setSpacing(8);
		String[] labelNames = new String[] { "TIME", "R", "B", "Y", "G", "TOKEN", "BOOTS", "FLIPPER", "KATANNA" };
		Font font = new Font("Arial", 12);

		if (firstDraw == false) {
			this.toolbar.getChildren().remove(this.toolbar.getChildren().size() - 1);
		}

		for (int i = 0; i < 8; i++) {
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
