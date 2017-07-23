package sound;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * a class the control the background music
 */
public class BackgroundMusic {

	private Clip clip;

	public BackgroundMusic() {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("./res/soundEffect/mappy.wav")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method the play the background music
	 */
	public void playMusic() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue((float) -25.0);
		clip.start();
	}

	/**
	 * method that stop the playing music
	 */
	public void stopMusic() {
		clip.stop();
	}

}
