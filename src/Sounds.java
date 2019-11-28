import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

/**
 * Sounds.java
 * 
 * A class designed to hold the methods for calling sounds. 
 * Has the ability to start, stop, resume and play sounds on a loop.
 * 
 * @author Luke Francis
 * @version 0.1
 */
public class Sounds {

	// Used to record the frame position of the track.
	private long clipTimePosition;
	
	
	/**
	 * Method for playing the sound on a loop
	 * @param filepath,		Location of the sound file that is required.
	 */
	public static void playLoop(String filepath) {

		try {
			File musicpath = new File(filepath);
			
			if(musicpath.exists()) {
				AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicpath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioinput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				JOptionPane.showMessageDialog(null, "Press ok to stop playing");
			}
			else {
				System.out.println("can't find file");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method for playing a single sound.
	 * @param filepath,		Location of the sound file that is required.
	 */
	public static void playSound(String filepath) {
		
		try {
			File musicpath = new File(filepath);
			
			if(musicpath.exists()) {
				AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicpath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioinput);
				clip.start();
				
				JOptionPane.showMessageDialog(null, "Press ok to stop playing");
			}
			else {
				System.out.println("can't find file");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method for stopping sounds. ***Not sure if it works yet***
	 */
	public void stopSound() {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clipTimePosition = clip.getMicrosecondPosition();
		clip.stop();
	}

	/**
	 * Method for resuming sounds ***Not sure if it works yet***
	 */
	public void resumeSound() {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clip.setMicrosecondPosition(clipTimePosition);
		clip.start();
	}
}