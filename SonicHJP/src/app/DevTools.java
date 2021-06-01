package app;

import Entities.Sonic;
import javafx.scene.paint.Color;

public class DevTools {
	// ATTRIBUTE //
	private boolean active = false;
	private float camTimer = 0;
	private float camX = 0, camY = 0;
	public float deltaMult = 1;
	
	private Spielwelt spielwelt;
	private Sonic sonic;
	private Kamera kamera;
	
	
	// KONSTRUKTOR //
	public DevTools(Spielwelt pSpielwelt) {
		spielwelt = pSpielwelt;
		sonic = spielwelt.getSpieler();
		kamera = spielwelt.getKamera();
	}
	
	
	// METHODEN //
	public void update(float delta) {
		if (!active) {
			if (Eingabe.isKeyDown("A") && Eingabe.isKeyDown("B") && Eingabe.isKeyDown("C")) {
				active = true;
				System.out.println("-> DevTools aktiv");
			}
			
			return;
		}
		
		if (sonic.getKnockback())
			sonic.debugHeal();
		sonic.setSpeedBonus();
		
		float inputX = (Eingabe.isKeyDown("LEFT") ? -1 : 0);
		inputX = (Eingabe.isKeyDown("RIGHT") ? (inputX + 1) : inputX);
		float inputY = (Eingabe.isKeyDown("DOWN") ? -1 : 0);
		inputY = (Eingabe.isKeyDown("UP") ? (inputY + 1) : inputY);
		
		if (inputX != 0 || inputY != 0) {
			if (camTimer <= 0) {
				camX = kamera.getX();
				camY = kamera.getY();
			}
			camTimer = 3;
		}
		
		if (camTimer > 0) {
			camTimer -= delta;
			
			camX += inputX * delta * 15;
			camY += inputY * delta * 15;
			
			kamera.setX(camX);
			kamera.setY(camY);
		}
		
		if (Eingabe.isKeyDown("O"))
			kamera.setPixelProEinheit(kamera.getPixelProEinheit() + 0.4f);
		else if (Eingabe.isKeyDown("L"))
			kamera.setPixelProEinheit(kamera.getPixelProEinheit() - 0.4f);
		
		if (Eingabe.isKeyDown("SHIFT"))
			deltaMult = 0.25f;
		else
			deltaMult = 1;
	}
	public void draw(float delta) {
		if (!active) return;
		
		Kollision.drawDebug(kamera);
		sonic.debugDraw();
		
		kamera.setLineWidth(0.1f);
		kamera.setFarbe(Color.YELLOW);
		kamera.drawLine(sonic.getX(), sonic.getY(), sonic.getX() + sonic.getSpeedX() * 0.3f, sonic.getY() + sonic.getSpeedY() * 0.3f);
		
		float x = kamera.getX() - (kamera.getWidth() / 2);
		float y = kamera.getY() - (kamera.getHeight() / 2);
		
		String txt = (int)(1 / delta) + " FPS\n";
		txt += "Pfeiltasten: Kamera bewegen\n";
		txt += "Rechtsklick: Teleportieren\n";
		txt += "O / L: Zoomen\n";
		txt += "SHIFT (halten): Zeitlupe\n";
		
		kamera.setFarbe(Color.BLACK);
		for (double i = 0; i < Math.PI * 2; i += (Math.PI / 4)) {
			float phX = (float)Math.sin(i) * 0.05f;
			float phY = (float)Math.cos(i) * 0.05f;
			kamera.drawText(txt, x + phX, y + (12 / kamera.getPixelProEinheit()) * 7 + phY);
		}
		kamera.setFarbe(Color.WHITE);
		kamera.drawText(txt, x, y + (12 / kamera.getPixelProEinheit()) * 7);
	}
}
