package Entities;

import app.CMath;
import app.Kamera;
import app.Kollision;
import app.RaycastHit;
import javafx.scene.image.Image;

public class Fledermaus extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/fledermaus.png");
	private int imageZaehler;
	private float animationTimer;
	
	private static final float SPEED = 6f;
	
	private float dirX, dirY;
	
	// KONSTRUKTOR //
	public Fledermaus(float px, float py) {
		super(px, py);
		dirX = 0;
		dirY = 0;
	}
	
	public static int ph = 0;
	
	
	// METHODEN
	@Override
	public void start() {
		
	}
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		if (animationTimer >= 0.05f) {
			animationTimer = 0;
			imageZaehler++;
			imageZaehler %= 5;
		}
	}
	
	@Override
	public void fixedUpdate(float delta) {
		float dist = CMath.distance(x, y, derSpieler.getX(), derSpieler.getY());
		if (dist < 10f && dist != 0) {
			float toPlayerX = (derSpieler.getX() - x) / dist;
			float toPlayerY = (derSpieler.getY() - y) / dist;
			
			dirX = CMath.move(dirX, toPlayerX, delta * 0.5f);
			dirY = CMath.move(dirY, toPlayerY, delta * 0.5f);
		} else {
			dirX = CMath.move(dirX, 0, delta * 0.5f);
			dirY = CMath.move(dirY, 0, delta * 0.5f);
		}
		
		RaycastHit rayH = Kollision.raycast(x, y, (dirX < 0 ? -1 : 1), 0, false);
		RaycastHit rayV = Kollision.raycast(x, y, 0, (dirY < 0 ? -1 : 1), false);
		
		if (rayH != null) {
			dirX *= -1;
			if (Math.abs(dirX) < 0.3f) {
				dirX = 0.3f * Math.signum(dirX);
			}
		}
		if (rayV != null) {
			dirY *= -1;
			if (Math.abs(dirY) < 0.3f) {
				dirY = 0.3f * Math.signum(dirY);
			}
		}
		
		
		x += SPEED * dirX * delta;
		y += SPEED * dirY * delta;
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, imageZaehler * 32, 0, 32, 32, x - 0.5f, y - 0.5f, 1f, 1f);
	}
}
