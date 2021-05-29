package Objects;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DekoObjekt {
	// ATTRIBUTE //
	private static Image imgDefault = new Image("file:files/textures/misc/decorative.png");
	private static Image imgLava = new Image("file:files/textures/misc/lava.png");
	public static int imageZaehler; // 0 <= imageZaehler < 4
	
	public int id;
	private float x, y;
	private boolean late;
	
	private Image dasStaticImage; 
	private float w, h;
	
	
	// KONSTRUKTOR //
	public DekoObjekt(int pID, float px, float py) {
		id = pID;
		x = px;
		y = py;
		w = 0.6f;
		h = 0.6f;
		dasStaticImage = null;
		late = false;
		
		if (id > 4) {
			late = true;
			switch (id) {
				case(5):
					w = 1;
					h = 0.5f;
					break;
				case(6):
					w = 4;
					h = 1;
					break;
				case(7):
					w = 8;
					h = 4;
					break;
			}
		}
	}
	public DekoObjekt(float px, float py, float pw, float ph, Image pDasStaticImage, boolean pLate) {
		id = 0;
		x = px;
		y = py;
		w = pw;
		h = ph;
		dasStaticImage = pDasStaticImage;
		late = pLate;
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		if (late || !dieKamera.isInView(x, y, w, h)) return;
		
		if (dasStaticImage != null) {
			dieKamera.drawImage(dasStaticImage, x, y, w, h, 0);
			return;
		}
		
		switch (id) {
			case (0): // flower A
				dieKamera.drawImageSection(imgDefault, (imageZaehler % 2) * 16, 0, 16, 16, x, y, 0.6f, 0.6f);
				break;
			case (1): // flower B
				dieKamera.drawImageSection(imgDefault, (imageZaehler % 2) * 16, 16, 16, 16, x, y, 0.6f, 0.6f);
				break;
			case (2): // flower C
				dieKamera.drawImageSection(imgDefault, (imageZaehler % 2) * 16, 32, 16, 16, x, y, 0.6f, 0.6f);
				break;
			case (3): // flower D
				dieKamera.drawImageSection(imgDefault, (imageZaehler % 2) * 16, 48, 16, 16, x, y, 0.6f, 0.6f);
				break;
			case (4): // cave light
				dieKamera.drawImageSection(imgDefault, imageZaehler * 16, 64, 16, 16, x, y, 0.6f, 0.6f);
				break;
		}
	}
	
	
	public void lateDraw(Kamera dieKamera) {
		if (!late || !dieKamera.isInView(x, y, w, h)) return;
		
		if (dasStaticImage != null) {
			dieKamera.drawImage(dasStaticImage, x, y, w, h, 0);
			return;
		}
		
		switch (id) {
			case(5): // lava surface
				dieKamera.drawImageSection(imgLava, 128, imageZaehler * 16, 32, 16, x, y, 1, 0.5f);
				break;
			case(6): // lava pool
				dieKamera.drawImageSection(imgLava, 0, imageZaehler * 32, 128, 32, x, y, 4, 1);
				break;
			case(7): // lava pool (big)
				for (int phX = 0; phX < 2; phX++) {
					for (int phY = 0; phY < 4; phY++) {
						dieKamera.drawImageSection(imgLava, 0, imageZaehler * 32, 128, 32, x + phX * 4, y + phY, 4, 1);
					}
				}
				break;
		}
	}
}
