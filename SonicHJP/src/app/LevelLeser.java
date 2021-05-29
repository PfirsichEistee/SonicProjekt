package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.Affe;
import Entities.Biene;
import Entities.Fisch;
import Entities.Gegner;
import Entities.Kaefer;
import Entities.Sonic;
import Objects.DekoObjekt;
import Objects.Item;
import Objects.Looping;
import Objects.MovingPlatform;
import Objects.Objekt;
import Objects.Ring;
import Objects.SS_Looping;
import Objects.SS_SBahn;
import Objects.SpezialStrecke;
import Objects.Waterfall;
import javafx.scene.image.Image;

public class LevelLeser {
	// ATTRIBUTE //
	private File dieDatei;
	
	
	// KONSTRUKTOR //
	public LevelLeser(String dateiPfad) throws Exception {
		dieDatei = new File(dateiPfad);
		
		if (!dieDatei.exists()) throw new Exception("Die angegebene Datei existiert nicht!");
	}
	
	
	// METHODEN //
	public Spielwelt erzeugeSpielwelt() {
		int mapID = 0;
		int pixelProEinheit = 32;
		float sonicX = 0, sonicY = 0;
		ArrayList<SpezialStrecke> spezStrecken = new ArrayList<SpezialStrecke>();
		ArrayList<Objekt> objekte = new ArrayList<Objekt>();
		ArrayList<Gegner> gegner = new ArrayList<Gegner>();
		ArrayList<DekoObjekt> dekos = new ArrayList<DekoObjekt>();
		ArrayList<Waterfall> wasserfaelle = new ArrayList<Waterfall>();
		ArrayList<LoopPlaceholder> loops = new ArrayList<LoopPlaceholder>();
		ArrayList<DeadzonePlaceholder> deadzones = new ArrayList<DeadzonePlaceholder>();
		ArrayList<MovingPlatform> platformen = new ArrayList<MovingPlatform>();
		
		// Optionale Texturen
		Image emeraldLooping = new Image("file:files/textures/loops/emerald.png");
		
		
		Scanner scanner;
		try {
			scanner = new Scanner(dieDatei);
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(" ");

				if (split[0].equals("CNF")) { // CNF MAP-ID PIXEL-PRO-EINHEIT SONICX SONICY
					mapID = strToInt(split[1]);
					pixelProEinheit = strToInt(split[2]);
					sonicX = strToFloat(split[3]);
					sonicY = strToFloat(split[4]);
				} else if (split[0].equals("OBJ")) { // OBJ X Y ID
					switch (strToInt(split[3])) {
						case (0):
							objekte.add(new Ring(strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (2):
						case (3):
						case (4):
						case (5):
						case (6):
						case (7):
							objekte.add(new Item(strToInt(split[3]) - 2, strToFloat(split[1]), strToFloat(split[2])));
							break;
					}
				} else if (split[0].equals("STK")) { // STK X1 Y1 X2 Y2 TYP
					boolean platform = (strToInt(split[5]) == 1 ? true : false);
					new Kollision(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[3]), strToFloat(split[4]), platform, false);
				} else if (split[0].equals("SPC")) { // SPC X Y ID OPT
					switch (strToInt(split[3])) {
						case (0): // Blume
							dekos.add(new DekoObjekt((int)Math.floor(Math.random() * 4), strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (1): // Schlangenbahn
							spezStrecken.add(new SS_SBahn(strToFloat(split[1]), strToFloat(split[2]) - 2.3f, strToInt(split[4])));
							break;
						case (2): // Looping
							//spezStrecken.add(new SS_Looping(strToFloat(split[1]), strToFloat(split[2])));
							dekos.add(new DekoObjekt(strToFloat(split[1]) - 1, strToFloat(split[2]) + 3.25f, 5, 8, emeraldLooping, true));
							break;
						case (3): // Wandlicht
							dekos.add(new DekoObjekt(4, strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (4): // Lava Oberflaeche
							dekos.add(new DekoObjekt(5, strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (5): // Lava Pool
							dekos.add(new DekoObjekt(6, strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (6): // Lava Pool (Gross)
							dekos.add(new DekoObjekt(7, strToFloat(split[1]), strToFloat(split[2])));
							break;
						case (7): // Deadzone
							deadzones.add(new DeadzonePlaceholder(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[4]), 1));
							break;
					}
				} else if (split[0].equals("GEG")) { // GEG X Y ID RICHTUNG
					switch (strToInt(split[3])) {
						case(0): // Biene
							gegner.add(new Biene(strToFloat(split[1]), strToFloat(split[2])));
							break;
						case(1): // Fisch
							gegner.add(new Fisch(strToFloat(split[1]), strToFloat(split[2])));
							break;
						case(2): // Affe
							gegner.add(new Affe(strToFloat(split[1]), strToFloat(split[2])));
							break;
						case(3): // Kaefer
							gegner.add(new Kaefer(strToFloat(split[1]), strToFloat(split[2])));
							break;
					}
				} else if (split[0].equals("WTR")) { // WTR X Y LAENGE HOEHE
					wasserfaelle.add(new Waterfall(strToFloat(split[1]), strToFloat(split[2]), strToInt(split[3]), strToInt(split[4])));
				} else if (split[0].equals("LPS")) { // LPS X1 Y1 X2 Y2 ID TYP
					LoopPlaceholder lp = null;
					for (LoopPlaceholder phLP : loops) {
						if (phLP.id == strToInt(split[5])) {
							lp = phLP;
							break;
						}
					}
					if (lp == null) {
						lp = new LoopPlaceholder(strToInt(split[5]));
						loops.add(lp);
					}
					
					switch (strToInt(split[6])) {
						case(0):
							lp.leftCollision.add(new Kollision(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[3]), strToFloat(split[4]), false, true));
							break;
						case(1):
							lp.rightCollision.add(new Kollision(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[3]), strToFloat(split[4]), false, true));
							break;
						case(2):
							// Not using static collisions anymore
							break;
					}
				} else if (split[0].equals("LPB")) { // LPB X Y W H ID TYP
					LoopPlaceholder lp = null;
					for (LoopPlaceholder phLP : loops) {
						if (phLP.id == strToInt(split[5])) {
							lp = phLP;
							break;
						}
					}
					if (lp == null) {
						lp = new LoopPlaceholder(strToInt(split[5]));
						loops.add(lp);
					}
					
					switch (strToInt(split[6])) {
						case(3): // left trigger box
							for (int i = 0; i < 4; i++)
								lp.triggerBoxValues[i] = strToFloat(split[i + 1]);
							break;
						case(4): // right trigger box
							for (int i = 0; i < 4; i++)
								lp.triggerBoxValues[i + 4] = strToFloat(split[i + 1]);
							break;
						case(5): // upper trigger box
							for (int i = 0; i < 4; i++)
								lp.triggerBoxValues[i + 8] = strToFloat(split[i + 1]);
							break;
					}
				} else if (split[0].equals("MOV")) { // MOV ID X Y TAR-X TAR-Y
					switch (strToInt(split[1])) {
						case(0):
							platformen.add( new MovingPlatform(0, strToFloat(split[2]), strToFloat(split[3]), 2, 4.25f, strToFloat(split[4]), strToFloat(split[5])) );
							break;
						case(1):
							platformen.add( new MovingPlatform(1, strToFloat(split[2]), strToFloat(split[3]), 1.5f, 0.5f, strToFloat(split[4]), strToFloat(split[5])) );
							break;
					}
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Lesen der Level-Datei!");
		}
		
		// Spezial Strecken
		SpezialStrecke[] finalSpezialStrecken = new SpezialStrecke[spezStrecken.size()];
		for (int i = 0; i < finalSpezialStrecken.length; i++)
			finalSpezialStrecken[i] = spezStrecken.get(i);
		
		// Loopings
		Looping[] finalLooping = new Looping[loops.size()]; // TODO
		for (int i = 0; i < loops.size(); i++) {
			LoopPlaceholder lp = loops.get(i);
			finalLooping[i] = new Looping(lp.getLeftCollision(), lp.getRightCollision());
			new TriggerBox(lp.triggerBoxValues[0], lp.triggerBoxValues[1], lp.triggerBoxValues[2], lp.triggerBoxValues[3], finalLooping[i], "enterLeft");
			new TriggerBox(lp.triggerBoxValues[4], lp.triggerBoxValues[5], lp.triggerBoxValues[6], lp.triggerBoxValues[7], finalLooping[i], "enterRight");
			new TriggerBox(lp.triggerBoxValues[8], lp.triggerBoxValues[9], lp.triggerBoxValues[10], lp.triggerBoxValues[11], finalLooping[i], "flip");
		}
		
		
		
		Spielwelt dieSpielwelt = new Spielwelt(new Image("file:files/textures/maps/map_0" + (mapID + 1) + ".png"), pixelProEinheit, sonicX, sonicY, finalSpezialStrecken, objekte, gegner, dekos, wasserfaelle);
		dieSpielwelt.setPlatformen(platformen);
		
		Sonic derSpieler = dieSpielwelt.getSpieler();
		for (DeadzonePlaceholder dz : deadzones) {
			dz.makeTriggerBox(derSpieler);
		}
		
		
		return dieSpielwelt;
	}
	
	
	private int strToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	private float strToFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}


class LoopPlaceholder {
	public int id;
	public ArrayList<Kollision> leftCollision;
	public ArrayList<Kollision> rightCollision;
	public float[] triggerBoxValues;
	
	public LoopPlaceholder(int pID) {
		id = pID;
		leftCollision = new ArrayList<Kollision>();
		rightCollision = new ArrayList<Kollision>();
		triggerBoxValues = new float[4 * 3];
		for (int i = 0; i < triggerBoxValues.length; i++)
			triggerBoxValues[i] = 0;
	}
	
	public Kollision[] getLeftCollision() {
		Kollision[] ph = new Kollision[leftCollision.size()];
		
		for (int i = 0; i < ph.length; i++)
			ph[i] = leftCollision.get(i);
		
		return ph;
	}
	public Kollision[] getRightCollision() {
		Kollision[] ph = new Kollision[rightCollision.size()];
		
		for (int i = 0; i < ph.length; i++)
			ph[i] = rightCollision.get(i);
		
		return ph;
	}
}

class DeadzonePlaceholder {
	public float x, y, w, h;
	
	public DeadzonePlaceholder(float px, float py, float pw, float ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
	}
	
	public void makeTriggerBox(Sonic derSpieler) {
		new TriggerBox(x, y, w, h, derSpieler, "hit");
	}
}




