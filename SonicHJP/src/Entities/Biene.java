package Entities;

import Objects.Projektil;
import app.CMath;
import app.Kamera;
import app.Kollision;
import app.RaycastHit;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Biene extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/biene.png");
	private int imageZaehler;
	private int animationZustand;
	private float animationTimer;
	
	private static final float SPEED = 2f;
	
	private float minX, maxX;
	private int direction;
	private float shootFreeze;
	
	private final int[][] anim = {
			{ 0, 1 }, // Fliegen
			{ 2, 3, 4 } //Projektil schiessen
	};
	
	// KONSTRUKTOR //
	public Biene(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
		
		minX = x - 2;
		maxX = x + 2;
		direction = 1;
		shootFreeze = 0;
	}
	
	
	// METHODEN
	@Override
	public void start() {
		RaycastHit leftHit = Kollision.raycast(x, y, -100, 0, true);
		RaycastHit rightHit = Kollision.raycast(x, y, 100, 0, true);
		
		if (leftHit != null)
			minX = leftHit.hitX + 2;
		else
			minX = x - 25;
		
		if (rightHit != null)
			maxX = rightHit.hitX - 2;
		else
			maxX = x + 25;
	}
	@Override
	public void update(float delta) {
		animationTimer += delta;
		if (animationTimer >= 0.15f) {
			animationTimer = 0;
			imageZaehler++;
			
			if (animationZustand == 0) {
				imageZaehler %= 2;
			} else {
				if (imageZaehler == 3)
					imageZaehler = 1;
			}
		}
		
		if (shootFreeze > 0 && animationZustand != 1) {
			animationZustand = 1;
			imageZaehler = 0;
		} else if (shootFreeze <= 0 && animationZustand != 0) {
			animationZustand = 0;
			imageZaehler = 0;
		}
	}
	
	@Override
	public void fixedUpdate(float delta) {
		if (shootFreeze > 0) {
			shootFreeze -= delta;
			if (shootFreeze < 0) {
				dieSpielwelt.addProjectile(new Projektil(x, y - 0.25f, (direction == 0 ? 1 : direction) * 3f, -3f));
			}
		} else {
			shootFreeze -= delta;
			if (CMath.distance(x, y, derSpieler.getX(), derSpieler.getY()) < 15f) {
				if (direction == 1 && derSpieler.getX() < (x - 3f) && derSpieler.getX() > minX)
					direction = -1;
				else if (direction == -1 && derSpieler.getX() > (x + 3f) && derSpieler.getX() < maxX)
					direction = 1;
				
				if (CMath.distance(x, y, derSpieler.getX(), derSpieler.getY()) < 10f && shootFreeze < -2f)
					shootFreeze = 0.5f;
			}
			
			if (x < minX && direction == -1)
				direction = 1;
			else if (x > maxX && direction == 1)
				direction = -1;
			
			x += SPEED * direction * delta;
		}
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.setLineWidth(0.05f);
		
		dieKamera.drawImageSection(dasImage, anim[animationZustand][imageZaehler] * 48, 0, 48, 48, x - 0.8f + (direction == 1 ? 1.6f : 0), y - 0.8f, 1.6f * -direction, 1.6f);
	}
}
