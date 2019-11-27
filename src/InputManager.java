import javafx.scene.input.KeyEvent;


/**
 * InputManager.java
 * Handles inputs from the user
 * @author Sam Forster
 * @version 1.0
 */
public class InputManager {
	public static void processKeyEvent(KeyEvent event, Integer key) {
		switch(event.getCode()) {
			case RIGHT:
				//Right key pressed
				event.consume();
				key = new Integer(1);
				break;
			
			case LEFT:
				//Left key pressed
				event.consume();
				key = new Integer(3);
				break;
				
			case UP:
				//Up key pressed
				System.out.println("UP key pressed");
				event.consume();
				key = new Integer(0);
				break;
				
			case DOWN:
				//Down key pressed
				event.consume();
				key = new Integer(2);
				break;
				
			default:
				//Do nothing
				System.out.println("No Keystroke Made!");
				key = new Integer(-1);
				break;
		}
	}
}
