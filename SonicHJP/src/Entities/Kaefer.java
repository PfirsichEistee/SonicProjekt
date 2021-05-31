package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Kaefer extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/kaefer.png");
	private int imageZaehler;
	private int animationsZaehler;
	private float animationTimer;
	
	private final int[][] anim = {
			{ 0, 1 }, //Bewegen
	};
	
	// KONSTRUKTOR //
	public Kaefer(float px, float py) {
		super(px, py);
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		imageZaehler = anim[0][animationsZaehler];
		
		if (animationTimer >= 0.05f) {
			animationTimer = 0;
			if(animationsZaehler == 1) {
				animationsZaehler = 0;
			}
			else {
				animationsZaehler ++;
			}
		}
	}
	
	@Override
	public void fixedUpdate(float delta) {
		x -= 2 * delta;
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, imageZaehler * 39, 0, 39, 30, x - 0.5f, y - 0.355f, 1f, 0.76f);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
