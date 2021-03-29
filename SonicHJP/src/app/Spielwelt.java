package app;

import java.util.ArrayList;

import Entities.Gegner;
import Entities.Sonic;
import Objects.Objekt;
import Objects.Projektil;
import Objects.Ring;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Spielwelt {
	// ATTRIBUTE //
	private Image dasLevelImage;
	
	private Kamera dieKamera;
	private Sonic derSpieler;
	private ArrayList<Objekt> dieObjekte;
	private ArrayList<Ring> dieRinge;
	private ArrayList<Gegner> dieGegner;
	private ArrayList<Projektil> dieProjektile;
	
	
	// KONSTRUKTOR //
	public Spielwelt(Image pLevelImage, float pSpielerX, float pSpielerY, ArrayList<Objekt> pDieObjekte, ArrayList<Ring> pDieRinge, ArrayList<Gegner> pDieGegner, ArrayList<Projektil> pDieProjektile) {
		dasLevelImage = pLevelImage;
		dieKamera = new Kamera(pSpielerX, pSpielerY, 48);
		derSpieler = new Sonic(pSpielerX, pSpielerY, dieKamera);
		
		dieObjekte = pDieObjekte;
		dieRinge = pDieRinge;
		dieGegner = pDieGegner;
		dieProjektile = pDieProjektile;
		
		Kollision.init(10, 5, this);
		
		new Kollision(1, 1, 7, 3);
		new Kollision(-1, 1, 7, 3);
	}
	
	
	// METHODEN //
	public void start() {
		debugStart();
	}
	
	
	public void update(float delta) {
		debugUpdate();
		debugDraw(delta);
	}
	
	
	public void fixedUpdate(float delta) {
		
	}
	
	
	public void draw() {
		dieKamera.drawLine(5, 1, 16, -11);
	}
	
	
	public Sonic getSpieler() {
		return derSpieler;
	}
	
	
	
	// DEBUG
	private void debugStart() {
		dieKamera.setLineWidth(0.05f);
		Kollision.raycast(5, 1, 11, -12);
	}
	
	private void debugDraw(float delta) {
		// grid
		int ph = (int)Math.floor(dieKamera.getX());
		for (int x = ph; x <= (ph + 20); x++) {
			if (x == 0) {
				dieKamera.setFarbe(Color.RED);
			} else {
				dieKamera.setFarbe(Color.BLACK);
			}
			
			dieKamera.drawLine(x, dieKamera.getY(), x, dieKamera.getY() - 15);
		}
		
		ph = (int)Math.floor(dieKamera.getY());
		for (int y = ph; y >= (ph - 15); y--) {
			if (y == 0) {
				dieKamera.setFarbe(Color.RED);
			} else {
				dieKamera.setFarbe(Color.BLACK);
			}
			
			dieKamera.drawLine(dieKamera.getX(), y, dieKamera.getX() + 20, y);
		}
		
		
		// kollision test
		dieKamera.setFarbe(Color.GREEN);
		dieKamera.drawLine(1, 1, 7, 3);
		dieKamera.drawLine(dieKamera.getX() + 6, dieKamera.getY() - 5, dieKamera.pixelZuEinheitX(Eingabe.mouseX), dieKamera.pixelZuEinheitY(Eingabe.mouseY));
		RaycastHit hit = Kollision.raycast(dieKamera.getX() + 6, dieKamera.getY() - 5,
				dieKamera.pixelZuEinheitX(Eingabe.mouseX) - (dieKamera.getX() + 6),
				dieKamera.pixelZuEinheitY(Eingabe.mouseY) - (dieKamera.getY() - 5));
		if (hit != null) {
			dieKamera.setFarbe(Color.RED);
			dieKamera.drawRect(hit.hitX - 0.1f, hit.hitY + 0.1f, 0.2f, 0.2f);
			dieKamera.drawLine(hit.hitX, hit.hitY, hit.hitX + hit.normalX, hit.hitY + hit.normalY);
		}
		

		
		dieKamera.setFarbe(Color.DARKRED);
		float phfps = (float)Math.floor((1f / delta) * 10) / 10;
		dieKamera.drawText(phfps + " FPS", dieKamera.getX() + 0.15f, dieKamera.getY() - 0.5f);
		float phx = (float)Math.floor(dieKamera.pixelZuEinheitX(Eingabe.mouseX) * 10) / 10;
		float phy = (float)Math.floor(dieKamera.pixelZuEinheitY(Eingabe.mouseY) * 10) / 10;
		dieKamera.drawText("cursor: " + phx + " | " + phy, dieKamera.getX() + 0.15f, dieKamera.getY() - 1f);
		
	}
	
	private void debugUpdate() {
		if (Eingabe.isKeyDown("A")) {
			dieKamera.setX(dieKamera.getX() - 0.1f);
		} else if (Eingabe.isKeyDown("D")) {
			dieKamera.setX(dieKamera.getX() + 0.1f);
		}
		if (Eingabe.isKeyDown("S")) {
			dieKamera.setY(dieKamera.getY() - 0.1f);
		} else if (Eingabe.isKeyDown("W")) {
			dieKamera.setY(dieKamera.getY() + 0.1f);
		}
	}
}
