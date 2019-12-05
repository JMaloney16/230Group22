
import java.io.IOException;
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
		private static Label selectedProfile; // Label displayed in menu GUI
		private static Label selectedLevel; // Label displayed in menu GUI
		private static final String LEVEL = "Level: "; // Constant shown in selectedLevel label
		private static final String PROFILE = "Profile: "; // Constant shown in selectProfile label
		private static final String PROFILE_ERROR = "Choose Profile";
		private static final String LEVEL_ERROR = "Choose Level";
		private static final String NETWORK_ERROR = "Network Error - No Word Of The Day :(";

		private static String profileSelected; // String used to check user has chosen a profile
		private static int levelSelected; // int used to check if user has chosen a level
		private static ArrayList<Player> players = new ArrayList<Player>();


/////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Create the GUI
		public static Pane buildMenuGUI(Stage stage, int width, int height) {
			// Create top-level panel that will hold all GUI
			BorderPane root = new BorderPane();
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;

			// Set JavaFX elements
			setStage(stage);
			setWindowSize(width, height);

			// HBar at top for Buttons
			HBox toolbarTop = new HBox();
			toolbarTop.setSpacing(10);
			toolbarTop.setPadding(PADDING);
			root.setTop(toolbarTop);

			VBox lower = new VBox();
			root.setBottom(lower);

			HBox toolbarBottom = new HBox();
			toolbarBottom.setSpacing(10);
			toolbarBottom.setPadding(PADDING);
			lower.getChildren().add(toolbarBottom);

			profileList = new VBox();
			profileList.setSpacing(10);
			profileList.setPadding(PADDING);
			root.setLeft(profileList);

			// Button to quit game
			Button quitButton = new Button("Quit");
			toolbarTop.getChildren().add(quitButton);

			// Quit Button event handler
			quitButton.setOnAction(e -> {
				primaryStage.close();
			});

			// Button to launch game
			Button launchButton = new Button("Launch Game");
			toolbarTop.getChildren().add(launchButton);

			// Launch Button event handler
			launchButton.setOnAction(e -> {
				if (profileSelected != "" && levelSelected != 0) {
					GameManager gm = new GameManager(primaryStage,
							"levels\\" + Integer.toString(levelSelected) + ".txt", profileSelected, 
							windowWidth, windowHeight, cellSize);

				}
			});

			selectedProfile = new Label(PROFILE);
			selectedProfile.setAlignment(javafx.geometry.Pos.CENTER);
			selectedProfile.setFont(new Font("Arial", 15));
			selectedLevel = new Label(LEVEL);
			selectedProfile.setAlignment(javafx.geometry.Pos.CENTER);
			selectedLevel.setFont(new Font("Arial", 15));

			toolbarTop.getChildren().addAll(selectedProfile, selectedLevel);
			profileSelected = "";
			levelSelected = 0;

			// Button for creating a new Profile
			Button newProfileButton = new Button("New Profile");
			newProfileButton.setOnAction(e -> {
				createProfile();
			});

			// Button for deleting a profile
			Button deleteProfileButton = new Button("Delete Profile");
			deleteProfileButton.setOnAction(e -> {
				deleteProfile();
			});

			drawProfileList();

			Label wordOfDay = new Label("");
			try {
				wordOfDay.setText(Networking.getMessage());
			} catch (IOException e) {
				wordOfDay.setText(NETWORK_ERROR);
			}

			wordOfDay.setWrapText(true);
			wordOfDay.setFont(new Font("Arial", 12));
			wordOfDay.setPadding(new Insets(10));
			lower.getChildren().add(wordOfDay);

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

			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);

			Label profileNameLabel = new Label("Enter a New Profile Name");
			TextField profileNameBox = new TextField();
			Button createProfileButton = new Button("Create");
			Label warning = new Label("Max of 15 characters");

			createProfileButton.setOnAction(e -> {
				if (profileNameBox.getText().length() < 16) {
					createProfileButton.setText("Clicked");
					players.add(new Player(0, 0, 0));
					FileManager.FileWriting.createNewPlayer(profileNameBox.getText());
					drawProfileList();
					rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
				}
			});
			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, createProfileButton, warning);

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
				if (!delete(profileNameBox.getText())) {
					System.out.println("Delete Failed");

				} else {
					rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
				}
			});
			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, deleteProfileButton);
		}

		private static boolean delete(String profile) {
			int i = 0;
			boolean found = false;
			while (i <= players.size() - 1 && found == false) {
				if (players.get(i).getName().equals(profile)) {
					players.remove(i);
					FileManager.FileWriting.deletePlayer(profile);
					found = true;
					drawProfileList();
					if (profileSelected.equals(profile)) {
						profileSelected = null;
						levelSelected = 0;
						selectedProfile.setText(PROFILE);
						selectedLevel.setText(LEVEL);
					}
				}
				i++;
			}
			return found;
		}

		private static void drawProfileList() {

			players.clear();
			ArrayList<String> profiles = FileManager.FileReading.getAllProfiles();
			for (String s : profiles) {
				System.out.println(s);
				Player p = new Player(0, 0, 0);
				p.setName(s);
				/// temp shit
				Board b = new Board(new Drawable[10][10], new ArrayList<Movable>(), new ArrayList<Interactable>());
				FileManager.FileReading.readPlayerFile("profiles\\" + s + ".txt", p, b);
				///
				players.add(p);
			}

			profileList.getChildren().clear();

			for (Player p : players) {
				Button button = new Button(p.getName());
				button.setOnAction(e -> {
					selectedProfile.setText(PROFILE + button.getText());
					profileSelected = button.getText();
					selectedLevel.setText(LEVEL);
					levelSelected = 0;

					buildLevelSelectPane(p);
				});

				profileList.getChildren().add(button);
			}
		}

		public static void buildLevelSelectPane(Player p) {
			BorderPane innerRoot = new BorderPane();
			VBox levelList = new VBox();

			levelList.getChildren().add(new Label("Select Level"));
			if (p.getMaxLevel() == 0) {
				Button button = new Button("1");
				button.setOnAction(e -> {
					int level = Integer.parseInt(button.getText());
					levelSelected = level;
					selectedLevel.setText(LEVEL + level);
					buildLeaderboardPane(FileManager.FileReading.getLeaderboard("levels\\" + level +".txt"));
				});
				levelList.getChildren().add(button);
			} else {

				for (int i = 1; i <= p.getMaxLevel(); i++) {
					Button button = new Button(Integer.toString(i));

					button.setOnAction(e -> {
						int level = Integer.parseInt(button.getText());
						levelSelected = level;
						selectedLevel.setText(LEVEL + level);

					});
					levelList.getChildren().add(button);
				}
			}

			innerRoot.setLeft(levelList);
			rootPane.setCenter(innerRoot);

		}
		
		
		private static void buildLeaderboardPane(ArrayList<String> leaderboard) {
			VBox leaderBoardPane = new VBox();
			System.out.println("Activated");
			for(String s: leaderboard) {
				System.out.println(s);
			}

		}
	}
}
