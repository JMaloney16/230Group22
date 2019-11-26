import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * MenuManager.java
 * Handles the creation, deletion and loading of profiles as well as calculating the leaderboard.
 * @version 0.1
 * @author ... 
 */
public class MenuManager {
	
	public static class Menu{
		private static Stage primaryStage;
		private static int windowWidth;
		private static int windowHeight;
		private static int CELL_SIZE;
		
		private static String profileSelected;
		private static String levelSelected;
		
		
		
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////

		
		
		
		public void loadProfile(String profileName) {

		}

		public void deleteProfile(String profileName) {

		}
		
		public void addProfile() {
			
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Create the GUI
		public static Pane buildMenuGUI(Stage stage, int width, int height) {
			// Create top-level panel that will hold all GUI
			BorderPane root = new BorderPane();
	
			//Set JavaFX elements
			setStage(stage);
			setWindowSize(width, height);
			
			
			//HBar at top for Buttons
			HBox toolbarTop = new HBox();
			toolbarTop.setSpacing(10);
			toolbarTop.setPadding(new Insets(10, 10, 10, 10)); 
			root.setTop(toolbarTop);
			
			HBox toolbarBottom = new HBox();
			toolbarBottom.setSpacing(10);
			toolbarBottom.setPadding(new Insets(10, 10, 10, 10));
			root.setBottom(toolbarBottom);
			
			//Button to quit game
			Button quitButton = new Button("Quit");
			toolbarTop.getChildren().add(quitButton);
			
			//Quit Button event handler
			quitButton.setOnAction(e -> {
				primaryStage.close();
			});
			
			//Button to launch game
			Button launchButton = new Button("Launch Game");
			toolbarTop.getChildren().add(launchButton);
		
			// Launch Button event handler
			launchButton.setOnAction(e -> {
				System.out.println("TRIGGERED");
				//GameManager gm = new GameManager();
			});
			
			Label selectedProfile = new Label("Profile: " + profileSelected);
			selectedProfile.setFont(new Font("Ariel", 20));
			Label selectedLevel = new Label("Level: " + levelSelected);
			selectedLevel.setFont(new Font("Ariel", 20));
			
			toolbarTop.getChildren().add(selectedProfile);
			toolbarTop.getChildren().add(selectedLevel);
			
			//Button for creating a new Profile
			Button newProfileButton = new Button("New Profile");
			toolbarBottom.getChildren().add(newProfileButton);
			
			Button deleteProfileButton = new Button("Delete Profile");
			toolbarBottom.getChildren().add(deleteProfileButton);
			
			
			return root;
		}
		
		//Set stage
		public static void setStage (Stage stage) {
			MenuManager.Menu.primaryStage = stage;
		}
		
		//Set window size
		public static void setWindowSize(int width, int height) {
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;
		}
		
		//Set cell size
		public static void setCellSize(int cellSize) {
			MenuManager.Menu.CELL_SIZE = cellSize;
		}
	}

}
