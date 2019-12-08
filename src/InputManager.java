import javafx.scene.input.KeyEvent;

/**
 * InputManager.java Handles inputs from the user
 * 
 * @author Sam Forster
 * @version 1.0
 */
public class InputManager {
	public static void processKeyEvent(KeyEvent event, GameManager gm) {
		switch (event.getCode()) {
		case RIGHT:
			event.consume();
			gm.setKey(1);
			break;

		case LEFT:
			event.consume();
			gm.setKey(3);
			break;

		case UP:
			event.consume();
			gm.setKey(0);
			break;

		case DOWN:
			event.consume();
			gm.setKey(2);
			break;

		default:
			// Do nothing
			event.consume();
			System.out.println("No Keystroke Made!");
			break;
		}
	}
}
