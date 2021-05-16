package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Objects.Objekt;
import Objects.Ring;
import Objects.SS_Looping;
import Objects.SS_SBahn;
import Objects.SpezialStrecke;
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
					}
				} else if (split[0].equals("STK")) { // STK X1 Y1 X2 Y2 TYP
					boolean platform = (strToInt(split[5]) == 1 ? true : false);
					new Kollision(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[3]), strToFloat(split[4]), platform, false);
				} else if (split[0].equals("SPC")) { // SPC X Y ID
					switch (strToInt(split[3])) {
						case (0): // Blume
							break;
						case (1): // Schlangenbahn
							spezStrecken.add(new SS_SBahn(strToFloat(split[1]), strToFloat(split[2]) - 2.3f, strToInt(split[4])));
							break;
						case (2): // Looping
							spezStrecken.add(new SS_Looping(strToFloat(split[1]), strToFloat(split[2])));
							break;
					}
				} else if (split[0].equals("GEG")) { // GEG X Y ID RICHTUNG
					
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Kollisionen konnten nicht erzeugt werden!");
		}
		
		SpezialStrecke[] ph = new SpezialStrecke[spezStrecken.size()];
		for (int i = 0; i < ph.length; i++)
			ph[i] = spezStrecken.get(i);
		
		return new Spielwelt(new Image("file:files/textures/maps/map_01.png"), pixelProEinheit, sonicX, sonicY, ph, objekte, null);
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





