package sound;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * this class handle all the sound effect cause by the game while in game
 */
public class Sound implements Runnable {
	private static Sound instance = new Sound();
	private short act;
	private Queue<Short> action;
	private boolean playing;
	private Map<Short, File> soundTable;
	private List<File> soundEffectList;

	private Sound() {
		this.soundEffectList = new ArrayList<>();
		this.soundEffectList.add(new File("res/soundEffect/walk.wav"));
		this.soundEffectList.add(new File("res/soundEffect/box.wav"));
		this.soundEffectList.add(new File("res/soundEffect/ball.wav"));
		this.soundEffectList.add(new File("res/soundEffect/lego.wav"));

		this.soundTable = new HashMap<>();
		this.soundTable.put((short) 3, this.soundEffectList.get(1));
		this.soundTable.put((short) 1, this.soundEffectList.get(1));
		this.soundTable.put((short) 4, this.soundEffectList.get(0));
		this.soundTable.put((short) 5, this.soundEffectList.get(0));
		this.soundTable.put((short) 6, this.soundEffectList.get(0));
		this.soundTable.put((short) 7, this.soundEffectList.get(0));
		this.soundTable.put((short) 8, this.soundEffectList.get(0));
		this.soundTable.put((short) 11, this.soundEffectList.get(2));

		this.action = new LinkedList<>();
	}

	public static Sound getInstance() {
		if (instance == null) {
			instance = new Sound();
		}
		return instance;
	}

	/**
	 * adding action into the queue, so the thread will handle the sound effect
	 * for every movement
	 * 
	 * @param act
	 */
	public void addQueue(Short act) {
		if (this.action.size() == 0 && !this.playing)
			this.action.add(act);
	}

	/**
	 * method that play the sound effect of the given action
	 * 
	 * @param id
	 */
	private void playSound(short id) {
		try {
			File soundTrack = this.soundTable.get(id);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundTrack));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			System.out.print("");
			if (!this.action.isEmpty()) {
				this.playing = true;
				act = this.action.poll();
				this.action.clear();
				this.playSound(act);
				this.playing = false;
			}
		}

	}
}
