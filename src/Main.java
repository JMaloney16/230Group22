import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main.java
 * Game opens from here when launched 
 *
 * @author Sam Forster
 */
public class Main extends Application {

	// The dimensions of the window
	private static final int WINDOW_WIDTH = 1080;
	private static final int WINDOW_HEIGHT = 600;

	// The dimensions of the canvas
	private static int CANVAS_WIDTH = 320;
	private static int CANVAS_HEIGHT = 320;
	
	// The size of each cell
	private static int GRID_CELL_WIDTH = 32;
	private static int GRID_CELL_HEIGHT = 32;
	
	private Canvas canvas; //GUI Canvas
	
	// Loaded images
	Image player;
	Image dirt;
	
	// X and Y coordinate of player
	int playerX = 0;
	int playerY = 0;

//////////////////////////////////////////////////////////////////////////////////////////

	public void start(Stage primaryStage) {
		Pane menu = buildMenuGUI();// Build the GUI
		Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT); // Create a scene from the GUI
		
		// Load images
		player = new Image("player.png");
		dirt = new Image("StoneBrickWall + Ceiling.png");
		
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event)); // Register an event handler for key presses
		
		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
		}

	//Processing the event when a key is pressed
	public void processKeyEvent(KeyEvent event) {
		switch (event.getCode()) {
			// Right key was pressed
			case RIGHT:
				playerX = playerX + 1;
				break;
			// Left key was pressed
			case LEFT:
				playerX = playerX - 1;
				break;
			// Up key was pressed
			case UP:
				playerY = playerY + 1;
				break;
			// Down key was pressed
			case DOWN:
				playerY = playerY - 1;
				break;
			default:
				//Do Nothing
				break;
		}

		// Redraw game as the player may have moved.
		drawGame();

		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	}

	//Draw game on canvas
	public void drawGame() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Draw row of dirt images
		// We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
		// We draw the row at y value 2.
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				gc.drawImage(dirt, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
			}
		}

		// Draw player at current location
		gc.drawImage(player, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
	}

	//restart the game
	public void restartGame() {
		// We just move the player to cell (0, 0)
		playerX = 0;
		playerY = 0;
		drawGame();
	}

//Move player to (approximately) middle of the canvas
	public void movePlayerToCenter() {
		// Moving to center of canvas
		playerX = (int) ((canvas.getWidth()/32)/2);
		playerY = (int) ((canvas.getHeight()/32)/2);
		drawGame();
	}

	//Create the GUI
	private Pane buildMenuGUI() {
		// Create top-level panel that will hold all GUI
		BorderPane root = new BorderPane();
		
		// Create canvas
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);
		
		//Button to launch game
		Button launchButton = new Button("Launch Game");
		root.getChildren().add(launchButton);
	
		// Add button event handlers
		launchButton.setOnAction(e -> {
			System.out.println("TRIGGERED");
		});
	
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}