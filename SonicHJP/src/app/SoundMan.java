package app;


import javafx.scene.media.AudioClip;

public class SoundMan {
	private static AudioClip[] clips = {
			new AudioClip("file:files/sounds/ringcollect.mp3"),
			new AudioClip("file:files/sounds/jump.mp3"),
			new AudioClip("file:files/sounds/spin.mp3"),
			new AudioClip("file:files/sounds/ringloss.mp3"),
			new AudioClip("file:files/sounds/shieldloss.mp3"),
			new AudioClip("file:files/sounds/boom.mp3"),
			new AudioClip("file:files/sounds/gate.mp3"),
			new AudioClip("file:files/sounds/dash.mp3"),
			new AudioClip("file:files/sounds/goal.mp3"),
	};
	private static AudioClip[] music = {
			new AudioClip("file:files/sounds/map_01.mp3"),
			new AudioClip("file:files/sounds/map_02.mp3"),
			new AudioClip("file:files/sounds/invincible.mp3"),
	};
	
	private static Kamera dieKamera;
	
	private static int mapID = 0;
	
	
	public static void init(Kamera pKamera, int pMapID) {
		dieKamera = pKamera;
		mapID = pMapID;
		setMusicSpeed(false);
	}
	
	public static void playClip(int id) {
		clips[id].play(0.5);
	}
	public static void playClip(int id, double volume) {
		clips[id].play(volume);
	}
	public static void playClipAt(int id, float px, float py) {
		float dist = (10f - CMath.clamp(CMath.distance(dieKamera.getX(), dieKamera.getY(), px, py), 0, 10)) / 10f;
		clips[id].play(dist);
	}
	
	public static void playMusic() {
		playMusic(mapID);
	}
	public static void playMusic(int id) {
		stopMusic();

		music[id].setPriority(Integer.MAX_VALUE);
		music[id].play(0.7);
	}
	public static void stopMusic() {
		for (int i = 0; i < music.length; i++)
			music[i].stop();
	}
	public static void setMusicSpeed(boolean state) {
		stopMusic();
		
		double rate = (state ? 1.25 : 1);
		
		for (int i = 0; i < music.length; i++)
			music[i].setRate(rate);
		
		playMusic();
	}
}





