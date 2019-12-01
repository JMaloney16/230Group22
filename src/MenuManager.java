
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * MenuManager.java Handles the creation, deletion and loading of profiles as
 * well as calculating the leaderboard.
 * 
 * @version 0.1
 * @author ...
 */

// TODO
/*
 * how is game going to be loaded?? file path to level and player path how to
 * make a "continue" button work
 */
public class MenuManager {

	public static class Menu {
		private static Stage primaryStage;
		private static int windowWidth;
		private static int windowHeight;
		private static int cellSize;
		private static final Insets PADDING = new Insets(10, 10, 10, 10);
		private static BorderPane rootPane;
		private static VBox profileList;
		private static Label selectedProfile;	//Label displayed in menu GUI
		private static Label selectedLevel;		//Label displayed in menu GUI
		
		private static String profileSelected;	//String used to check user has chosen a profile
		private static int levelSelected;		//int used to check if user has chosen a level
		private static ArrayList<Player> players = new ArrayList<Player>();

/////////////////////////////////////////////////////////////////////////////////////////////

		public void loadProfile(String profileName) {

		}

		public void deleteProfile(String profileName) {

		}

		public static void addProfile() {

		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Create the GUI
		public static Pane buildMenuGUI(Stage stage, int width, int height) {
			// Create top-level panel that will hold all GUI
			BorderPane root = new BorderPane();
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;
			
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
			
			profileList = new VBox();
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
				GameManager gm = new GameManager(primaryStage, "../levels/LevelExample.txt", windowWidth, windowHeight, cellSize);
			});
			
			selectedProfile = new Label("Profile: ");
			selectedProfile.setFont(new Font("Ariel", 20));
			selectedLevel = new Label("Level: ");
			selectedLevel.setFont(new Font("Ariel", 20));
			
			toolbarTop.getChildren().addAll(selectedProfile, selectedLevel);
			
			//Button for creating a new Profile
			Button newProfileButton = new Button("New Profile");
			newProfileButton.setOnAction(e -> {
				createProfile();
			});
			
			//Button for deleting a profile
			Button deleteProfileButton = new Button("Delete Profile");
			deleteProfileButton.setOnAction(e -> {
				deleteProfile();
			});

			
			players.add(new Player(0, 0, "", "testerNameOne", 4));
			players.add(new Player(0, 0, "", "Jack is fit", 40));
			
			 
			drawProfileList();		
			
			toolbarBottom.getChildren().addAll(newProfileButton, deleteProfileButton);
			
			
			
			
//			System.out.println(root.getLeft());
			rootPane = root;
			return root;
		}

		// Set stage
		public static void setStage(Stage stage) {
			MenuManager.Menu.primaryStage = stage;
		}

		// Set window size
		public static void setWindowSize(int width, int height) {
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;
		}

		// Set cell size
		public static void setCellSize(int cellSize) {
			MenuManager.Menu.cellSize = cellSize;
		}

		private static void createProfile() {
			// Temps
			Player tempPlayer = new Player(0, 0, "../assets/playerPlaceholder.png", "", 0);

			Drawable[][] tempDraw = new Drawable[10][10];
			ArrayList<Movable> move = new ArrayList<Movable>();
			ArrayList<Interactable> interact = new ArrayList<Interactable>();
			Board tempBoard = new Board(tempDraw, move, interact);

			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);

			Label profileNameLabel = new Label("Enter a New Profile Name");
			TextField profileNameBox = new TextField();
			Button createProfileButton = new Button("Create");

			createProfileButton.setOnAction(e -> {
				createProfileButton.setText("Clicked");
				players.add(new Player(0, 0, "", profileNameBox.getText(), 0));
				profileList.getChildren().add(new Button(profileNameBox.getText()));
				rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
			});


			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, createProfileButton);

		}

		private static void deleteProfile() {
			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);

			Label profileNameLabel = new Label("Enter a Profile Name to Delete");
			TextField profileNameBox = new TextField();
			Button deleteProfileButton = new Button("Delete");

			deleteProfileButton.setOnAction(e -> {
				deleteProfileButton.setText("Clicked");
				if(!delete(profileNameBox.getText())){
					System.out.println("Delete Failed");
					profileNameLabel.setText("Enter a Profile Name to Delete");
					deleteProfileButton.setText("Delete");
					
				}else {
					rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
				}
			});


			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, deleteProfileButton);
		}
		
		private static boolean delete(String profile) {
			int i = 0;
			boolean found = false;
			while(i <= players.size() - 1 && found == false) {
				if(players.get(i).getName().equals(profile)) {
					players.remove(i);
					found = true;
					drawProfileList();
					if(profileSelected.equals(profile)) {
						profileSelected = null;
						selectedProfile.setText("Profile: ");
						selectedLevel.setText("Level: ");
					}
				}
				
				i++;
			}
			
			return found;
		}
		
		private static void drawProfileList() {
			profileList.getChildren().clear();
			
			for(Player p : players) {
				Button button = new Button(p.getName());
				button.setOnAction(e -> {
					selectedProfile.setText("Profile: " + button.getText());
					profileSelected = button.getText();
				});
				
				profileList.getChildren().add(button);
						
				
			}
		}
	}

}
