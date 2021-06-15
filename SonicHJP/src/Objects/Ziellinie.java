package Objects;

import Entities.Sonic;
import app.CMath;
import app.Daten;
import app.GUI_Launcher;
import app.Kamera;
import app.SoundMan;
import app.Spielwelt;
import app.Start;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ui.Core;
import ui.Rect;

public class Ziellinie extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/misc/sign.png");
	private int imageZaehler;
	private boolean aktiv;
	private Spielwelt spielwelt;
	
	private Rect uiRect;
	private float timer;
	
	
	// KONSTRUKTOR //
	public Ziellinie(float px, float py) {
		super(px, py, 1f, 1f);
		imageZaehler = 0;
		aktiv = false;
		timer = 0;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, 48 * imageZaehler, 0, 48, 48, x, y, w, h);
		
		if (aktiv) {
			timer += 0.05f;
			timer = (timer > 1 ? 1 : timer);
			uiRect.setColor(new Color(0, 0, 0, timer));
			
			if (timer >= 1) {
				Sonic sonic = spielwelt.getSpieler();
				
				int newScore = sonic.getRingCount() * 10 + sonic.getScore() + (int)CMath.clamp(240 - sonic.getTime(), 0, 120) * 100;
				
				Daten daten = GUI_Launcher.getDaten();
				int[] stats = daten.getStats(spielwelt.getMapID());
				if (stats[2] < newScore) {
					daten.setStats(spielwelt.getMapID(), sonic.getTime(), sonic.getRingCount(), newScore);
					
					System.out.println("Neuer Highscore!");
				}
				
				Start.quit();
			}
		}
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		if (!aktiv) {
			aktiv = true;
			SoundMan.playClip(8);
			SoundMan.stopMusic();
			spielwelt.setFixedUpdateDeltaMult(0);
			uiRect = new Rect(0, 0, Core.getWidth(), Core.getHeight(), new Color(0, 0, 0, 0));
			Core.append(uiRect);
		}
	}
	
	
	public void incImage() {
		if (aktiv)
			imageZaehler = (imageZaehler + 1) % 4;
	}
	
	
	public void setSpielwelt(Spielwelt p) {
		spielwelt = p;
	}
}
