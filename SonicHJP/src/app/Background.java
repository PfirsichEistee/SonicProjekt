package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Background {
	// ATTRIBUTE //
	private static Image imgBerge = new Image("file:files/textures/background/bg_hills.png");
	private static Image imgWald = new Image("file:files/textures/background/bg_forest.png");
	private static Image imgWasser = new Image("file:files/textures/background/bg_water.png");
	private static Image imgWolke = new Image("file:files/textures/background/bg_cloud.png");
	
	private GraphicsContext gc;
	private Kamera dieKamera;
	private float screenWidth, screenHeight;
	
	
	// KONSTRUKTOR //
	public Background(Kamera pKamera) {
		dieKamera = pKamera;
		gc = dieKamera.getGraphicsContext();
		
		screenWidth = dieKamera.getScreenWidth();
		screenHeight = dieKamera.getScreenHeight();
	}
	
	
	// METHODEN //
	public void draw() {
		gc.setFill(new Color(0.8, 0.8, 1, 1));
		gc.fillRect(0, 0, screenWidth, screenHeight);
		
		// Hills
		double ph = screenHeight * 0.8;
		double pw = (imgBerge.getWidth() / imgBerge.getHeight()) * ph;
		
		double px = (-dieKamera.getX() * 15) % pw;
		while (px > 0)
			px -= pw;
		
		while (px < screenWidth) {
			gc.drawImage(imgBerge, px, screenHeight - ph, pw, ph);
			px += pw;
		}
		
		
		// Forest
		ph = screenHeight * 0.4;
		pw = (imgWald.getWidth() / imgWald.getHeight()) * ph;
		
		px = (-dieKamera.getX() * 25) % pw;
		while (px > 0)
			px -= pw;
		
		while (px < screenWidth) {
			gc.drawImage(imgWald, px, screenHeight - ph, pw, ph);
			px += pw;
		}
		
		
		// Water
		ph = screenHeight * 0.1;
		pw = (imgWasser.getWidth() / imgWasser.getHeight()) * ph;
		
		px = (-dieKamera.getX() * 35) % pw;
		while (px > 0)
			px -= pw;
		
		while (px < screenWidth) {
			gc.drawImage(imgWasser, px, screenHeight - ph, pw, ph);
			px += pw;
		}
		
		
		// Clouds
		ph = screenHeight * 0.15;
		pw = (imgWolke.getWidth() / imgWolke.getHeight()) * ph;
		
		px = (-dieKamera.getX() * 10) % (pw * 3);
		while (px > 0)
			px -= pw;
		
		while (px < screenWidth) {
			gc.drawImage(imgWolke, px, 0, pw, ph);
			px += pw * 3;
		}
	}
}
