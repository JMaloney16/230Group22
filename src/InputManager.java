import javafx.scene.input.KeyEvent;


/**
 * InputManager.java
 * Handles inputs from the user
 * @author Sam Forster
 * @version 1.0
 */
public class InputManager {
	
	///////////////////////////////////////////
	
	
	public void processKeyEvent(KeyEvent event, Integer key) {
		System.out.println("test");
		switch(event.getCode()) {
			case RIGHT:
				//Right key pressed
				event.consume();
				key = new Integer(1);
			
			case LEFT:
				//Left key pressed
				event.consume();
				key = new Integer(3);
				
			case UP:
				//Up key pressed
				event.consume();
				key = new Integer(0);
				
			case DOWN:
				//Down key pressed
				event.consume();
				key = new Integer(2);
				
			default:
				//Do nothing
				System.out.println("No Keystroke Made!");
				key = new Integer(-1);
		}
	}
	
	
}
