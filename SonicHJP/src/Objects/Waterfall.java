package Objects;

import app.Kamera;
import javafx.scene.image.Image;

public class Waterfall {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/misc/waterfall.png");
	public static int imageZaehler; // 0 <= imageZaehler < 2
	
	private int length;
	private int height;
	private float x, y;
	
	
	// KONSTRUKTOR //
	public Waterfall(float px, float py, int pLength, int pHeight) {
		x = px;
		y = py;
		length = pLength;
		height = pHeight;
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		for (int drwX = 0; drwX < length; drwX++) {
			for (int drwY = 0; drwY < height; drwY++) {
				drawChunk(dieKamera, x + drwX * 2, y + drwY * 2, imageZaehler == 0);
			}
			
			dieKamera.drawImageSection(dasImage, 0, 0, 64, 5, x + drwX * 2, y + height * 2 - 0.25f, 2, 0.25f);
		}
	}
	
	private void drawChunk(Kamera dieKamera, float px, float py, boolean first) {
		if (first) {
			dieKamera.drawImageSection(dasImage, 0, 5, 64, 64, px, py, 2, 2);
		} else {
			dieKamera.drawImageSection(dasImage, 0, 5 + 16, 64, 48, px, py + 0.5f, 2, 1.5f);
			dieKamera.drawImageSection(dasImage, 0, 5, 64, 16, px, py, 2, 0.5f);
		}
	}
}
