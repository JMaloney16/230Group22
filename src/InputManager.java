import javafx.scene.input.KeyEvent;


/**
 * InputManager.java
 * Handles inputs from the user
 * @author Sam Forster
 * @version 1.0
 */
public class InputManager {
	
	///////////////////////////////////////////
	
	
	public String processKeyEvent(KeyEvent event) {
		switch(event.getCode()) {
			case RIGHT:
				//Right key pressed
				event.consume();
				return "r";
			
			case LEFT:
				//Left key pressed
				event.consume();
				return "l";
				
			case UP:
				//Up key pressed
				event.consume();
				return "u";
				
			case DOWN:
				//Down key pressed
				event.consume();
				return "d";
				
			default:
				//Do nothing
				System.out.println("No Keystroke Made!");
				return "";
		}
	}
	
	
}
