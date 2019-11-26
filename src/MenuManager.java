import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
		private static final Insets PADDING = new Insets(10, 10, 10, 10);
		private static BorderPane rootPane;
		
		private static String profileSelected;
		private static int levelSelected;
		
		
		
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////

		
		
		
		public void loadProfile(String profileName) {

		}

		public void deleteProfile(String profileName) {

		}
		
		public static void addProfile() {
			
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
			toolbarTop.setPadding(PADDING); 
			root.setTop(toolbarTop);
			
			HBox toolbarBottom = new HBox();
			toolbarBottom.setSpacing(10);
			toolbarBottom.setPadding(PADDING);
			root.setBottom(toolbarBottom);
			
			VBox profileList = new VBox();
			profileList.setSpacing(10);
			profileList.setPadding(PADDING);
			root.setLeft(profileList);		
			
			
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
			
			toolbarTop.getChildren().addAll(selectedProfile, selectedLevel);
			
			//Button for creating a new Profile
			Button newProfileButton = new Button("New Profile");
			
			
			newProfileButton.setOnAction(e -> {
				createProfile();
			});
			
			Button deleteProfileButton = new Button("Delete Profile");
			deleteProfileButton.setOnAction(e -> {
				deleteProfile();
			});
			
			
			Button button1 = new Button("Profile");
			Button button2 = new Button("Profile");
			Button button3 = new Button("Profile");
			Button button4 = new Button("Profile");
			
			profileList.getChildren().addAll(button1, button2, button3, button4);
			
			
			
			
			
			toolbarBottom.getChildren().addAll(newProfileButton, deleteProfileButton);
			
			
			System.out.println(root.getLeft());
			rootPane = root;
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
		
		private static void createProfile() {
			String returnValue = "";
			Player tempPlayer = new Player(0, 0, "playerPlaceholder.png");
			
			Drawable[][] tempDraw = new Drawable[10][10];
			Board tempBoard = new Board();
			
			
			
			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);
			
			Label profileNameLabel = new Label("Enter a New Profile Name");
			TextField profileNameBox = new TextField();
			Button createProfileButton = new Button("Create");
			
			createProfileButton.setOnAction(e -> {
				
				FileManager.FileWriting.savePlayerFile("../assets/" + profileNameBox.getText(), null, null);
				rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
			});
			
			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, createProfileButton);
			
						
		}
		
		private static void deleteProfile() {
			VBox profileList = (VBox) rootPane.getLeft();
			if(profileList.getChildren().size() != 0) {
				profileList.getChildren().remove(profileList.getChildren().size() - 1);
			}
		}
	}

}
