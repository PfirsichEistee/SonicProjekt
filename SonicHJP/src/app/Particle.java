package app;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Particle {
	// ATTRIBUTE //
	private static Image[] dieImages = {
			new Image("file:files/textures/misc/explosion.png"), // 5x Bilder
			new Image("file:files/textures/misc/sparkle.png"), // 3x Bilder
			new Image("file:files/textures/items/rings.png"), // 4x4 Bilder
	};
	public static ArrayList<Particle> particleList = new ArrayList<Particle>();
	
	private int id;
	private int imageZaehler;
	private float x, y;
	private float size;
	
	// Ring-Attribute (NUR fuer ID=2)
	private float vx, vy;
	private float lifetime;
	
	
	// KONSTRUKTOR //
	public Particle(int pID, float px, float py, float pSize) {
		id = pID;
		x = px;
		y = py;
		size = pSize;
		imageZaehler = 0;
		
		if (id == 2) {
			vx = (float)(Math.random() * 2 - 1) * 3;
			vy = (float)(Math.random() + 1) * 1.5f;
			lifetime = 0;
		}
		
		particleList.add(this);
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		switch (id) {
			case(0):
				dieKamera.drawImageSection(dieImages[id], imageZaehler * 32, 0, 32, 32, x - (size / 2), y - (size / 2), size, size);
				break;
			case(1):
				dieKamera.drawImageSection(dieImages[id], imageZaehler * 22, 0, 22, 22, x - (size / 2), y - (size / 2), size, size);
				break;
			case(2):
				dieKamera.drawImageSection(dieImages[id], (imageZaehler % 4) * 64, (imageZaehler / 4) * 64, 64, 64, x - (size / 2), y - (size / 2), size, size);
				break;
		}
	}
	
	public void update() { // Delta is 0.05s
		imageZaehler++;
		
		switch (id) {
			case(0):
				if (imageZaehler == 5)
					kill();
				break;
			case(1):
				if (imageZaehler == 3)
					kill();
				break;
			case(2):
				lifetime += 0.05f;
				imageZaehler %= 16;
				if (lifetime >= 3f)
					kill();
				break;
		}
	}
	public void update(float delta) {
		if (id != 2) return;
		
		vx = CMath.move(vx, 0, delta * 10);
		vy = CMath.move(vy, -6, delta * 10 * (vy < 0 ? 2 : 1));
		x += vx * delta * 10;
		y += vy * delta * 10;
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
