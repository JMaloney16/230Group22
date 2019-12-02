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

	/**
	 * Prepares the game to be launched
	 */
	public void start(Stage primaryStage) {
		Pane menu = MenuManager.Menu.buildMenuGUI(primaryStage, WINDOW_WIDTH, WINDOW_HEIGHT);	// Build the GUI
		Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT); // Create a scene from the GUI

		
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);	//Stops user from changing size of window
		primaryStage.show();
		


	}

	public static void PlaySound(){
		SoundEffect test = new SoundEffect();
		
		String soundplay = "../CS230Group22/230Group22/assets/Sounds/Dungeon_004.wav";
		
		//single play sound
		//test.playSound(soundplay);
		//play loop
		test.playLoop(soundplay);
	}

	public static void main(String[] args) {
		PlaySound();
		launch(args);
	}
}