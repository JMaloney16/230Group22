import javafx.application.Application;
import javafx.scene.Scene;
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
	private static final int WINDOW_WIDTH = 448;
	private static final int WINDOW_HEIGHT = 500;

//////////////////////////////////////////////////////////////////////////////////////////

	public void start(Stage primaryStage) {
		Pane menu = MenuManager.Menu.buildMenuGUI(primaryStage, WINDOW_WIDTH, WINDOW_HEIGHT);	// Build the GUI
		Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT); // Create a scene from the GUI

		
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);	//Stops user from changing size of window
		primaryStage.show();
		GameManager gm = new GameManager(primaryStage, "../levels/LevelExample.txt", 448, 500, 64);
		gm.launchGame();
	}

	/*
	//Create the GUI
	private Pane buildMenuGUI() { 
		// Create top-level panel that will hold all GUI
		BorderPane root = new BorderPane();

		//HBar at top for Buttons
				HBox toolbar = new HBox();
				toolbar.setSpacing(10);
				toolbar.setPadding(new Insets(10, 10, 10, 10)); 
				root.setTop(toolbar);
		
		
		//Button to launch game
		Button launchButton = new Button("Launch Game");
		toolbar.getChildren().add(launchButton);
	
		// Add button event handlers
		launchButton.setOnAction(e -> {
			System.out.println("TRIGGERED");
		});
	
		return root;
	}
	*/

	public static void main(String[] args) {
		launch(args);
	}
}