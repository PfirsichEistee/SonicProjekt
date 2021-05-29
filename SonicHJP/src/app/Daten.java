package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Daten {
	// ATTRIBUTE //
	private static String path = System.getProperty("user.home") + "/Documents/SonicHJP/data.txt";
	
	private File file;
	
	
	// KONSTRUKTOR //
	public Daten() {
		file = new File(path);
		
		if (!file.exists()) {
			try {
				System.out.println("Neue Speicherdaten werden angelegt...");
				
				File ph = new File(System.getProperty("user.home") + "/Documents/SonicHJP");
				ph.mkdirs();
				
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// METHODEN //
	public int[] getStats(int levelID) {
		int[] stats = new int[3];
		stats[0] = 999999;
		stats[1] = 0;
		stats[2] = 0;
		
		try {
			Scanner reader = new Scanner(file);
			
			String line;
			while (reader.hasNextLine()) {
				line = reader.nextLine();
				
				String[] split = line.split(" ");
				
				if (split[0].equals("MapID") && split[1].equals(String.valueOf(levelID))) {
					for (int i = 0; i < 3; i++) {
						line = reader.nextLine();
						split = line.split(" ");
						
						stats[i] = Integer.parseInt(split[1]);
					}
					
					break;
				}
			}
			
			reader.close();
		} catch (Exception e) {
			System.out.println("Konnte Speicherdaten nicht lesen.");
			System.exit(0);
		}
		
		return stats;
	}
	
	public void setStats(int levelID, int zeit, int ringe, int score) {
		// Remove old values
		try {
			ArrayList<String> rows = new ArrayList<String>();
			Scanner reader = new Scanner(file);
			
			String line;
			while (reader.hasNextLine()) {
				line = reader.nextLine();
				
				if (line != null) {
					String[] split = line.split(" ");
					
					if (split[0].equals("MapID") && split[1].equals(String.valueOf(levelID))) {
						for (int i = 0; i < 4; i++) {
							if (reader.hasNextLine())
								line = reader.nextLine();
							else
								line = null;
						}
					}
					
					if (line != null)
						rows.add(line);
				}
			}
			
			reader.close();
			
			
			// Re-Add old values
			FileWriter writer = new FileWriter(file);
			
			for (int i = 0; i < rows.size(); i++)
				writer.write(rows.get(i) + "\n");
			
			writer.close();
		} catch (Exception e) {
			System.out.println("Konnte Speicherdaten nicht lesen.");
			System.exit(0);
		}
		
		
		// Add new values
		try {
			FileWriter writer = new FileWriter(file, true);
			
			writer.write("MapID " + levelID + "\n");
			writer.write("Zeit " + zeit + "\n");
			writer.write("Ringe " + ringe + "\n");
			writer.write("Score " + score + "\n");
			
			writer.close();
		} catch (IOException e) {
			System.out.println("Konnte nicht speichern.");
			System.exit(0);
		}
	}
}


