package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		Scanner scanner;
		try {
			scanner = new Scanner(dieDatei);
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(" ");
				
				if (split[0].equals("OBJ")) { // OBJ X Y ID
					
				} else if (split[0].equals("STK")) { // STK X1 Y1 X2 Y2 TYP
					boolean platform = (strToInt(split[5]) == 1 ? true : false);
					new Kollision(strToFloat(split[1]), strToFloat(split[2]), strToFloat(split[3]), strToFloat(split[4]), platform);
				} else if (split[0].equals("SPC")) { // SPC X Y ID
					
				} else if (split[0].equals("GEG")) { // GEG X Y ID RICHTUNG
					
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Kollisionen konnten nicht erzeugt werden!");
		}
		
		return null;
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





