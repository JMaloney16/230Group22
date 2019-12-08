
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
 * @version 0.4
 * @author Sam Forster, Ewan Bradford
 */

public class MenuManager {
	public static class Menu {
		private static Stage primaryStage;
		private static int windowWidth;
		private static int windowHeight;
		private static final Insets PADDING = new Insets(10, 10, 10, 10);
		private static BorderPane rootPane;
		private static VBox profileList;
		private static Label selectedProfile; // Label displayed in menu GUI
		private static Label selectedLevel; // Label displayed in menu GUI
		private static final String LEVEL = "Level: "; // Constant shown in selectedLevel label
		private static final String PROFILE = "Profile: "; // Constant shown in selectProfile label
		private static final String NETWORK_ERROR = "Network Error - No Word Of The Day :(";

		private static String profileSelected; // String used to check user has chosen a profile
		private static int levelSelected; // int used to check if user has chosen a level
		private static ArrayList<Player> players = new ArrayList<Player>();

		
		
		/**
		 * Creates the pane for the menu displayed on the primary stage
		 * 
		 * @param stage The main stage displayed by the program
		 * @param width The width of the window in pixels
		 * @param height The height of the window in pixels
		 * @return Pane used to display on the scene
		 */
		public static Pane buildMenuGUI(Stage stage, int width, int height) {
			// Create top-level panel that will hold all GUI
			BorderPane root = new BorderPane();
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;

			// Set JavaFX elements
			setStage(stage);
			setWindowSize(width, height);

			// HBox at top for Buttons
			HBox toolbarTop = new HBox();
			toolbarTop.setSpacing(10);
			toolbarTop.setPadding(PADDING);
			root.setTop(toolbarTop);

			VBox lower = new VBox();
			root.setBottom(lower);

			// VBox at bottom for Buttons
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
			// Only allows the game to run when a profile and level have been selected
			launchButton.setOnAction(e -> {
				System.out.println(levelSelected);
				if (profileSelected != "" && levelSelected != 0) {
					Player player = null;
					for (Player p : players) {
						if (p.getName().equals(profileSelected)) {
							player = p;
						}
					}
					GameManager gm = new GameManager(primaryStage, levelSelected, player, windowWidth, windowHeight);
				}

			});

			// Labels for displaying the chosen profile and level
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

			// Label for displaying the word of the day
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

			rootPane = root;
			return root;
		}

		/**
		 * Setting the stage so the scene is displayed
		 * @param stage The stage to display the scene
		 */
		public static void setStage(Stage stage) {
			MenuManager.Menu.primaryStage = stage;
		}

		/**
		 * Setting the size of the window
		 * @param width The width of the display in pixels
		 * @param height The height of the display in pixels
		 */
		public static void setWindowSize(int width, int height) {
			MenuManager.Menu.windowWidth = width;
			MenuManager.Menu.windowHeight = height;
		}

		/**
		 * Displays the levels available to the user based on the maxLevel available to the player
		 * @param p The player profile the user has chosen
		 */
		public static void buildLevelSelectPane(Player p) {
			BorderPane innerRoot = new BorderPane();
			VBox levelList = new VBox();

			//Giving the user the option to continue a level they have already started
			levelList.getChildren().add(new Label("Select Level"));
			if (p.getCurrentTime() > 0) {
				Button button = new Button("Continue Level " + Integer.toString(p.getCurrentLevel()));

				button.setOnAction(e -> {
					selectedLevel.setText("Continuing");
					System.out.println("levels\\" + p.getCurrentLevel() + ".txt");
					buildLeaderboardPane(innerRoot,
							FileManager.FileReading.getLeaderboard("levels\\" + p.getCurrentLevel() + ".txt"));
					levelSelected = -1;
				});
				levelList.getChildren().add(button);
			}
			
			//Display a button for each available level to the player
			if (p.getMaxLevel() == 0) {
				Button button = new Button("1");
				button.setOnAction(e -> {
					int level = Integer.parseInt(button.getText());
					levelSelected = 1;
					selectedLevel.setText(LEVEL + level);
					buildLeaderboardPane(innerRoot,
							FileManager.FileReading.getLeaderboard("levels\\" + level + ".txt"));
				});
				levelList.getChildren().add(button);
			} else {
				for (int i = 1; i < p.getMaxLevel() + 1; i++) {
					Button button = new Button(Integer.toString(i));

					button.setOnAction(e -> {
						int level = Integer.parseInt(button.getText());
						levelSelected = level;
						selectedLevel.setText(LEVEL + level);
						buildLeaderboardPane(innerRoot,
								FileManager.FileReading.getLeaderboard("levels\\" + level + ".txt"));
					});
					levelList.getChildren().add(button);
				}
			}

			innerRoot.setLeft(levelList);
			rootPane.setCenter(innerRoot);

		}
		
		/**
		 * Creating a new Profile
		 */
		private static void createProfile() {
			//Creating an inner GUI displayed within the root
			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);

			Label profileNameLabel = new Label("Enter a New Profile Name");
			TextField profileNameBox = new TextField();
			Button createProfileButton = new Button("Create");
			Label warning = new Label("Max of 15 characters");

			//Creating a profile and adding it to file when the create button is clicked
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
		
		/**
		 * Deleting a profile
		 */
		private static void deleteProfile() {
			//Creating an inner GUI displayed within the root
			VBox subRoot = new VBox();
			subRoot.setSpacing(10);
			subRoot.setPadding(PADDING);
			subRoot.setAlignment(javafx.geometry.Pos.CENTER);
			rootPane.setCenter(subRoot);

			Label profileNameLabel = new Label("Enter a Profile Name to Delete");
			TextField profileNameBox = new TextField();
			Button deleteProfileButton = new Button("Delete");

			//Deletes the profile if it exists
			deleteProfileButton.setOnAction(e -> {
				if (!delete(profileNameBox.getText())) {
					System.out.println("Delete Failed");

				} else {
					rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
				}
			});
			subRoot.getChildren().addAll(profileNameLabel, profileNameBox, deleteProfileButton);
		}
		
		/**
		 * Deletes the profile it is given from File
		 * @param profile The profile to be deleted
		 * @return boolean whether the profile selected exists or not
		 */
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

		/**
		 * Draws the list of profiles to the screen
		 */
		private static void drawProfileList() {
			players.clear();
			ArrayList<String> profiles = FileManager.FileReading.getAllProfiles();
			for (String s : profiles) {
				System.out.println(s);
				Player p = new Player(0, 0, 0);
				p.setName(s);
				Board b = new Board(new Drawable[10][10], new ArrayList<Movable>(), new ArrayList<Interactable>());
				FileManager.FileReading.readPlayerFile("profiles\\" + s + ".txt", p, b);
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

		/**
		 * Displays the leaderboard whenever a level is selected
		 * @param innerRoot The root pane displayed when a profile is selected
		 * @param leaderboard The leaderboard as an arrayList of Strings - contains profile name then their time
		 */
		private static void buildLeaderboardPane(BorderPane innerRoot, ArrayList<String> leaderboard) {
			VBox leaderBoardPane = new VBox();
			leaderBoardPane.setPadding(PADDING);
			Label leaderboardLabel = new Label("Leaderboard");
			leaderBoardPane.getChildren().add(leaderboardLabel);
			for (int i = 0; i < leaderboard.size(); i += 2) {
				HBox box = new HBox();
				Label nameLabel = new Label(leaderboard.get(i) + ": ");
				Label timeLabel = new Label(leaderboard.get(i + 1));
				box.getChildren().addAll(nameLabel, timeLabel);
				leaderBoardPane.getChildren().add(box);
			}
			innerRoot.setRight(leaderBoardPane);
		}
	}
}
