package app;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Particle {
	// ATTRIBUTE //
	private static Image[] dieImages = {
			new Image("file:files/textures/misc/explosion.png"), // 5x Bilder
			new Image("file:files/textures/misc/sparkle.png"), // 3x Bilder
	};
	public static ArrayList<Particle> particleList = new ArrayList<Particle>();
	
	private int id;
	private int imageZaehler;
	private float x, y;
	private float size;
	
	
	// KONSTRUKTOR //
	public Particle(int pID, float px, float py, float pSize) {
		id = pID;
		x = px;
		y = py;
		size = pSize;
		imageZaehler = 0;
		
		particleList.add(this);
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		switch (id) {
			case (0):
				dieKamera.drawImageSection(dieImages[id], imageZaehler * 32, 0, 32, 32, x - (size / 2), y - (size / 2), size, size);
				break;
			case (1):
				dieKamera.drawImageSection(dieImages[id], imageZaehler * 22, 0, 22, 22, x - (size / 2), y - (size / 2), size, size);
				break;
		}
	}
	
	public void update() {
		imageZaehler++;
		
		switch (id) {
			case (0):
				if (imageZaehler == 5)
					kill();
				break;
			case (1):
				if (imageZaehler == 3)
					kill();
				break;
		}
	}
	
	private void kill() {
		for (int i = 0; i < particleList.size(); i++) {
			if (particleList.get(i) == this) {
				particleList.remove(i);
				break;
			}
		}
	}
}
