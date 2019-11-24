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

//////////////////////////////////////////////////////////////////////////////////////////

	public void start(Stage primaryStage) {
		Pane menu = buildMenuGUI();// Build the GUI
		Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT); // Create a scene from the GUI

		
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}


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

	public static void main(String[] args) {
		launch(args);
	}
}