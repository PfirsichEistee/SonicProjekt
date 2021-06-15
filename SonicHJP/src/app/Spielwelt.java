package app;

import java.util.ArrayList;

import Entities.Gegner;
import Entities.Kaefer;
import Entities.Fisch;
import Entities.Fledermaus;
import Entities.Biene;
import Entities.Affe;
import Entities.Sonic;
import Objects.DekoObjekt;
import Objects.Item;
import Objects.MovingPlatform;
import Objects.Objekt;
import Objects.Projektil;
import Objects.Ring;
import Objects.SS_Looping;
import Objects.SS_SBahn;
import Objects.SpezialStrecke;
import Objects.Stachelblock;
import Objects.Waterfall;
import Objects.Ziellinie;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Spielwelt {
	// ATTRIBUTE //
	private Image dasLevelImage;
	private int levelPixelProEinheit;
	private int imgWidth, imgHeight;
	private int mapID;
	
	private Kamera dieKamera;
	private Sonic derSpieler;
	private SpezialStrecke[] dieSpezialStrecken;
	private ArrayList<Objekt> dieObjekte;
	private ArrayList<Gegner> dieGegner;
	private ArrayList<MovingPlatform> diePlatformen;
	private ArrayList<DekoObjekt> dieDekos;
	private ArrayList<Waterfall> dieWasserfaelle;
	private ArrayList<Projektil> dieProjektile;
	
	private Background derHintergrund;
	
	private float animTimer;
	private int animSlow;
	
	private float fixedUpdateDeltaMult;
	
	// DEBUG
	private float debugDelta;
	
	
	// KONSTRUKTOR //
	public Spielwelt(int pMapID, Image pLevelImage, int pLevelPixelProEinheit, float pSpielerX, float pSpielerY) {
		dasLevelImage = pLevelImage;
		levelPixelProEinheit = pLevelPixelProEinheit;
		imgWidth = (int)dasLevelImage.getWidth();
		imgHeight = (int)dasLevelImage.getHeight();
		mapID = pMapID;
		
		dieKamera = new Kamera(pSpielerX, pSpielerY, 48);
		derSpieler = new Sonic(pSpielerX, pSpielerY, dieKamera);
		derHintergrund = new Background(dieKamera);
		
		dieProjektile = new ArrayList<Projektil>();
		
		
		animTimer = 0;
		animSlow = 0;
		
		fixedUpdateDeltaMult = 1;
		
		// Klassenattribute
		Gegner.setSpielwelt(this);
	}
	
	
	// METHODEN //
	public void start() {
		for (Gegner g : dieGegner)
			g.start();
		
		SoundMan.playMusic();
		
		debugStart();
	}
	
	
	public void update(float delta) {
		debugDelta = delta;
		
		derSpieler.update(delta);
		
		for (Gegner g : dieGegner)
			g.update(delta);
		
		for (Projektil p : dieProjektile)
			p.update(delta);
		
		for (Particle p : Particle.particleList)
			p.update(delta);
		
		
		animTimer += delta;
		if (animTimer >= 0.05f) {
			animTimer = 0;
			animSlow++;
			
			if (animSlow == 4) {
				animSlow = 0;
				
				Waterfall.imageZaehler++;
				Waterfall.imageZaehler %= 2;
				
				Item.imageZaehler++;
				Item.imageZaehler %= 4;
				
				DekoObjekt.imageZaehler++;
				DekoObjekt.imageZaehler %= 4;
				
				Projektil.imageZaehler++;
				Projektil.imageZaehler %= 2;
			}
			
			
			Ring.imageZaehler++;
			Ring.imageZaehler %= 16;
			
			
			if (fixedUpdateDeltaMult == 0) { // Wenn Ziellinie erreicht wurde ist dieser Wert = 0
				for (Objekt obj : dieObjekte) {
					if (obj.getClass() == Ziellinie.class) {
						((Ziellinie)obj).incImage();
					}
				}
			}
			
			
			for (int i = (Particle.particleList.size() - 1); i >= 0; i--) {
				Particle.particleList.get(i).update();
			}
		}
	}
	
	
	public void fixedUpdate(float delta) {
		delta *= fixedUpdateDeltaMult;
		
		for (MovingPlatform mp : diePlatformen) {
			mp.fixedUpdate(delta);
		}
		
		derSpieler.fixedUpdate(delta);
		
		for (Gegner g : dieGegner) {
			if (dieKamera.isInView(g.getX(), g.getY())) {
				g.fixedUpdate(delta);
			}
		}
		
		
		if (!derSpieler.getKnockback()) {
			TriggerBox.update(derSpieler.getX(), derSpieler.getY());
			
			
			// Try entering spezial track
			if (!derSpieler.isPhysicsLocked() && derSpieler.getGrounded() && Math.abs(derSpieler.getSpeedX()) > 10) {
				for(int i = 0; i < dieSpezialStrecken.length; i++) {
					boolean Abfrage = dieSpezialStrecken[i].isPointInRange(derSpieler.getX(), derSpieler.getY());
					if (Abfrage == true) {
						SpezialStrecke ss = dieSpezialStrecken[i];
						
						if (ss.getClass().isAssignableFrom(SS_SBahn.class)) {
							if (CMath.distance(ss.getX(), 0, derSpieler.getX(), 0) <= 1) {
								// Enter from left
								if (derSpieler.getSpeedX() < 0) continue;
								
								derSpieler.setSpezialProzent(0);
								derSpieler.setSpezialZiel(1);
							} else {
								// Enter from right
								if (derSpieler.getSpeedX() > 0) continue;
								
								derSpieler.setSpezialProzent(1);
								derSpieler.setSpezialZiel(0);
							}
						} else if (ss.getClass().isAssignableFrom(SS_Looping.class)) {
							continue;
						}
						
						
		
						derSpieler.lockPhysics();
						derSpieler.setSpezialStrecke(ss);
					}
				}
			}
			
			// Objekte
			for (int i = (dieObjekte.size() - 1); i >= 0; i--) {
				Objekt obj = dieObjekte.get(i);
				if (obj.isPointInside(derSpieler.getX(), derSpieler.getY())) {
					if (!obj.playerWasInside) {
						obj.playerWasInside = true;
						
						if (obj.getClass() == Ring.class) {
							dieObjekte.remove(i);
							derSpieler.addPoints(10);
						} else {
							if (derSpieler.getKnockback()) continue;
							if (obj.getClass() == Item.class && ((Item)obj).isDead()) continue;
							
							derSpieler.addPoints(100);
							SoundMan.playClip(5);
						}
						
						obj.onPlayerCollide(derSpieler);
					}
				} else {
					obj.playerWasInside = false;
				}
			}
			
			// Check intersections between player and enemies
			for (int i = (dieGegner.size() - 1); i >= 0; i--) {
				if (dieGegner.get(i).isTouchingPlayer()) {
					Gegner g = dieGegner.get(i);
					if (derSpieler.isDeadly()) {
						new Particle(0, g.getX(), g.getY(), 1.5f);
						derSpieler.setForce(derSpieler.getSpeedX(), CMath.min(derSpieler.getSpeedY(), 0) + 6f);
						
						dieGegner.remove(i);
						derSpieler.addPoints(250);
						SoundMan.playClip(5);
					} else {
						float ph = (derSpieler.getX() - g.getX());
						if (ph == 0) ph = -0.1f;
						derSpieler.hit(ph * 5f, 6f);
					}
				}
			}
			
			// Check projectiles
			for (int i = (dieProjektile.size() - 1); i >= 0; i--) {
				if (dieProjektile.get(i).isNextTo(derSpieler.getX(), derSpieler.getY())) {
					if (derSpieler.isVulnerableToProjectiles()) {
						derSpieler.hit(derSpieler.getSpeedX() * 0.75f, 6f);
					}
					
					if (dieProjektile.get(i).timer > 2f) {
						dieProjektile.remove(i);
					}
				}
			}
		}
		
		
		// Clamp camera
		dieKamera.setX(CMath.clamp(dieKamera.getX(), dieKamera.getWidth() * 0.5f, (imgWidth / levelPixelProEinheit) - dieKamera.getWidth() * 0.5f));
		dieKamera.setY(CMath.clamp(dieKamera.getY(), dieKamera.getHeight() * 0.5f, (imgHeight / levelPixelProEinheit) - dieKamera.getHeight() * 0.5f));
	}
	
	
	public void draw() {
		derHintergrund.draw();
		
		int cnkX = (int)Math.floor((dieKamera.getX() - dieKamera.getWidth() * 0.5f) / 10f);
		int cnkY = (int)Math.floor((dieKamera.getY() - dieKamera.getHeight() * 0.5f) / 10f);
		for (int xx = cnkX; xx < (int)Math.ceil((dieKamera.getX() + dieKamera.getWidth() * 0.5f) / 10f); xx++) {
			for (int yy = cnkY; yy < (int)Math.ceil((dieKamera.getY() + dieKamera.getHeight() * 0.5f) / 10f); yy++) {
				if (xx >= 0 && (xx * 10 * levelPixelProEinheit) < imgWidth && yy >= 0 && (yy * 10 * levelPixelProEinheit) < imgHeight) {
					dieKamera.drawImageSection(dasLevelImage, xx * 10 * levelPixelProEinheit, imgHeight - (yy + 1) * 10 * levelPixelProEinheit,
						levelPixelProEinheit * 10, levelPixelProEinheit * 10, xx * 10, yy * 10, 10, 10);
				}
			}
		}
		
		for (int i = 0; i < dieSpezialStrecken.length; i++) {
			dieSpezialStrecken[i].draw(dieKamera);
		}
		
		for (MovingPlatform mp : diePlatformen) {
			mp.draw(dieKamera);
		}
		
		for (int i = 0; i < dieWasserfaelle.size(); i++) {
			dieWasserfaelle.get(i).draw(dieKamera);
		}
		
		for (int i = 0; i < dieDekos.size(); i++) {
			dieDekos.get(i).draw(dieKamera);
		}
		
		for (int i = 0; i < dieObjekte.size(); i++) {
			dieObjekte.get(i).draw(dieKamera);
		}
		
		for (Gegner g : dieGegner) {
			g.draw(dieKamera);
		}
		
		derSpieler.draw();
		
		for (int i = 0; i < dieDekos.size(); i++) {
			dieDekos.get(i).lateDraw(dieKamera);
		}
		
		for (Projektil p : dieProjektile) {
			p.draw(dieKamera);
		}
		
		if (dieSpezialStrecken != null) {
			for (int i = 0; i < dieSpezialStrecken.length; i++) {
				dieSpezialStrecken[i].lateDraw(dieKamera);
			}
		}
		

		for (int i = 0; i < Particle.particleList.size(); i++) {
			Particle.particleList.get(i).draw(dieKamera);
		}
		
		
		//debugDraw();
	}
	
	
	// Beeinflussen der Spielwelt
	public void addProjectile(Projektil p) {
		dieProjektile.add(p);
	}
	
	
	// Etc
	public Sonic getSpieler() {
		return derSpieler;
	}
	public Kamera getKamera() {
		return dieKamera;
	}
	public int getMapID() {
		return mapID;
	}
	
	// Setter
	public void setPlatformen(ArrayList<MovingPlatform> pDiePlatformen) {
		diePlatformen = pDiePlatformen;
	}
	public void setObjekte(ArrayList<Objekt> p) {
		dieObjekte = p;
	}
	public void setGegner(ArrayList<Gegner> p) {
		dieGegner = p;
	}
	public void setDekos(ArrayList<DekoObjekt> p) {
		dieDekos = p;
	}
	public void setWasserfaelle(ArrayList<Waterfall> p) {
		dieWasserfaelle = p;
	}
	public void setSpezialStrecken(SpezialStrecke[] p) {
		dieSpezialStrecken = p;
	}
	
	public void setFixedUpdateDeltaMult(float m) {
		fixedUpdateDeltaMult = m;
	}
	
	
	
	// DEBUG
	private void debugStart() {
		
	}
	
	private void debugDraw() {
		// grid
		int ph = (int)Math.floor(dieKamera.getX() - (dieKamera.getWidth() / 2));
		for (int x = ph; x <= (ph + (int)Math.ceil(dieKamera.getWidth())); x++) {
			if (x == 0) {
				dieKamera.setFarbe(Color.RED);
				dieKamera.setLineWidth(0.03f);
			} else {
				dieKamera.setFarbe(Color.BLACK);
				dieKamera.setLineWidth(0.01f);
			}
			
			dieKamera.drawLine(x, dieKamera.getY() - (dieKamera.getHeight() / 2), x, dieKamera.getY() + (dieKamera.getHeight() / 2));
		}
		
		ph = (int)Math.floor(dieKamera.getY() - (dieKamera.getHeight() / 2));
		for (int y = ph; y <= (ph + (int)Math.ceil(dieKamera.getHeight())); y++) {
			if (y == 0) {
				dieKamera.setFarbe(Color.RED);
				dieKamera.setLineWidth(0.03f);
			} else {
				dieKamera.setFarbe(Color.BLACK);
				dieKamera.setLineWidth(0.01f);
			}
			
			dieKamera.drawLine(dieKamera.getX() - (dieKamera.getWidth() / 2), y, dieKamera.getX() + (dieKamera.getWidth() / 2), y);
		}
		
		
		for (int xx = 0; xx < 25; xx++) {
			int clr = xx % 2;
			for (int yy = 0; yy < 5; yy++) {
				clr = (clr + yy) % 2;
				
				if (clr == 0) dieKamera.setFarbe(new Color(1, 0, 0, 0.1f));
				else dieKamera.setFarbe(new Color(1, 1, 0, 0.1f));
				
				dieKamera.drawRect(xx * 10, yy * 10, 10, 10);
			}
		}
		
		
		dieKamera.setLineWidth(0.02f);
		
		
		Kollision.drawDebug(dieKamera);
		
		
		dieKamera.setFarbe(Color.DARKRED);
		float phfps = (float)Math.floor((1f / debugDelta) * 10) / 10;
		dieKamera.drawText(phfps + " FPS", dieKamera.getX() - (dieKamera.getWidth() / 2) + 0.15f, dieKamera.getY() + (dieKamera.getHeight() / 2) - 0.5f);
		float phx = (float)Math.floor(dieKamera.pixelZuEinheitX(Eingabe.mouseX) * 10) / 10;
		float phy = (float)Math.floor(dieKamera.pixelZuEinheitY(Eingabe.mouseY) * 10) / 10;
		dieKamera.drawText("cursor: " + phx + " | " + phy, dieKamera.getX() - (dieKamera.getWidth() / 2) + 0.15f, dieKamera.getY() + (dieKamera.getHeight() / 2) - 1f);
		
		
		/*dieKamera.drawLine(dieKamera.getX(), dieKamera.getY(), phx, phy);
		dieKamera.drawLine(phx, phy, phx + 1, phy);
		RaycastHit hit = Kollision.raycast(phx, phy, 1, 0, true);
		
		if (hit != null) {
			dieKamera.drawOval(hit.hitX - 0.2f, hit.hitY - 0.2f, 0.4f, 0.4f);
			dieKamera.drawLine(hit.hitX, hit.hitY, hit.hitX + hit.normalX, hit.hitY + hit.normalY);
		}*/
	}
	
	public void setdieProjektile(float px, float py, float pSpeedX, float pSpeedY) {
		dieProjektile.add(new Projektil(px, py, pSpeedX, pSpeedY));
	}
}
