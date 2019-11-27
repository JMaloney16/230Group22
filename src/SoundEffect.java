import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * SoundEffect.java 
 * A class designed to hold the methods for calling sounds. 
 * Has the ability to start, stop and play sounds on a loop.
 * 
 * 
 * @version 0.1
 * @author Luke Francis
 *
 */
public class SoundEffect {
	
	Clip clip;
	
	/**
	 * Method used for setting the sound file that is required.
	 * 
	 * @param soundFileName, 	The name of the sound file that is required.
	 */
	public void setFile(String soundFileName) {
		
		try {
			File file = new File(soundFileName);
			
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
			
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * Method used for playing a single sound. Sets the sounds frame position to 0
	 * to play from the start.
	 */
	public void playSound() {
		//clip.setFramePosition(0);
		clip.start();
	}
	
	/**
	 * Method used for playing a sound on a loop. Mostly used for background music.
	 */
	public void playLoop() {
//		clip.setFramePosition(0);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	/**
	 * Method used to stop the sound.
	 */
	public void stopSound() {
		clip.stop();
	}
}
