package Objects;

import app.Kamera;
import javafx.scene.image.Image;

public class DekoObjekt {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/misc/decorative.png");
	public static int imageZaehler; // 0 <= imageZaehler < 4
	
	private int id;
	private float x, y;
	
	
	// KONSTRUKTOR //
	public DekoObjekt(int pID, float px, float py) {
		id = pID;
		x = px;
		y = py;
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		switch (id) {
		case (0): // flower A
			dieKamera.drawImageSection(dasImage, (imageZaehler % 2) * 16, 0, 16, 16, x, y, 0.6f, 0.6f);
			break;
		case (1): // flower B
			dieKamera.drawImageSection(dasImage, (imageZaehler % 2) * 16, 16, 16, 16, x, y, 0.6f, 0.6f);
			break;
		case (2): // flower C
			dieKamera.drawImageSection(dasImage, (imageZaehler % 2) * 16, 32, 16, 16, x, y, 0.6f, 0.6f);
			break;
		case (3): // flower D
			dieKamera.drawImageSection(dasImage, (imageZaehler % 2) * 16, 48, 16, 16, x, y, 0.6f, 0.6f);
			break;
		case (4): // cave light
			dieKamera.drawImageSection(dasImage, imageZaehler * 16, 64, 16, 16, x, y, 0.6f, 0.6f);
			break;
		}
	}
}
