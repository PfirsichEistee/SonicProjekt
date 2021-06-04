package Entities;

import app.CMath;
import app.Kamera;
import app.Kollision;
import app.RaycastHit;
import javafx.scene.image.Image;

public class Kaefer extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/kaefer.png");
	private int imageZaehler;
	private int animationsZaehler;
	private float animationTimer;
	
	private static final float SPEED = 2.25f;
	
	private int direction;
	private float minX, maxX;
	
	private final int[][] anim = {
			{ 0, 1 }, //Bewegen
	};
	
	// KONSTRUKTOR //
	public Kaefer(float px, float py) {
		super(px, py);
		direction = 1;
		minX = x - 1;
		maxX = x + 1;
	}
	
	public static int ph = 0;
	
	
	// METHODEN
	@Override
	public void start() {
		if (x < 50)
			ph = 1;
		
		// Check left
		RaycastHit hit;
		float phY = y;
		for (float xx = (x - 0.15f); xx > (x - 25); xx -= 0.15f) {
			hit = Kollision.raycast(xx, phY + 1, 0, -25, true);
			if (hit != null) {
				if (hit.hitY > (phY - 0.75f) && CMath.angleBetweenDirs(0, 1, hit.normalX, hit.normalY) < 35) {
					phY = hit.hitY;
					minX = hit.hitX;
				} else {
					break;
				}
			}
		}
		
		// Check right
		phY = y;
		for (float xx = (x + 0.15f); xx < (x + 25); xx += 0.15f) {
			hit = Kollision.raycast(xx, phY + 1, 0, -25, true);
			if (hit != null) {
				if (hit.hitY > (phY - 0.75f) && CMath.angleBetweenDirs(0, 1, hit.normalX, hit.normalY) < 20) {
					phY = hit.hitY;
					maxX = hit.hitX;
				} else {
					break;
				}
			}
		}
	}
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		imageZaehler = anim[0][animationsZaehler];
		
		if (animationTimer >= 0.15f) {
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
		if (CMath.distance(x, y, derSpieler.getX(), derSpieler.getY()) < 15f) {
			if (direction == 1 && derSpieler.getX() < (x - 1f) && derSpieler.getX() > minX)
				direction = -1;
			else if (direction == -1 && derSpieler.getX() > (x + 1f) && derSpieler.getX() < maxX)
				direction = 1;
		}
		
		if (direction == -1 && x < minX)
			direction = 1;
		else if (direction == 1 && x > maxX)
			direction = -1;
		
		x += SPEED * direction * delta;
		
		RaycastHit hit = Kollision.raycast(x, y + 1, 0, -25, true);
		if (hit != null)
			y = hit.hitY + 0.335f;
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, imageZaehler * 39, 0, 39, 30, x + 0.5f * direction, y - 0.35f, -direction, 1f);
	}
}
