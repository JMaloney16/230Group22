

public class SoundTest {

	/* 
	 * Updated sound class. 28/11/19 
	 * Hopefully it should work now. 
	 * Managed to test it below and each method seems to work.
	 * Haven't tested stopSound() and resumeSound() as i haven't got a 
	 * UI to test it with and i'm not sure how to implement it into the 
	 * JavaFX we have already. It currently has a dialogue box that pops up
	 * to show that it works when tested. That can be removed or commented out
	 * once implemented.
	 * 
	 * The file extension for the sound file may vary from machine to machine.
	 */
	
	public SoundTest() {
		Sounds test = new Sounds();
		
		String soundplay = "../CS230Group22/CS230Group22/230Group22/assets/Sounds/Death_001.wav";
		
		//single play sound
		//test.playSound(soundplay);
		//play loop
		//test.playLoop(soundplay);
	}
	
	public static void main(String[] args) {
		
		new SoundTest();

	}

}
