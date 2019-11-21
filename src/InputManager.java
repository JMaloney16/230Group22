import javafx.scene.input.KeyEvent;


/**
 * InputManager.java
 * Handles inputs from the user
 * @author Sam Forster
 * @version 1.0
 */
public class InputManager {
	public char processKeyEvent(KeyEvent event) {
		switch(event.getCode()) {
		
		case RIGHT:
			event.consume();
			return 'r';
		case LEFT:
			event.consume();
			return 'l';
		case UP:
			event.consume();
			return 'u';
		case DOWN: 
			event.consume();
			return 'd';
		default:
			return 'n';
		}
	}
}
