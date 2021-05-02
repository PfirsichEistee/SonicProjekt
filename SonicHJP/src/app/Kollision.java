package app;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Kollision {
	// ATTRIBUTE //
	private static ArrayList<Kollision> altKollisionenListe; // Hier kommen Kollisionen rein, die keinem Chunk zugeordnet wurden
	private static ArrayList<Kollision>[][] kollisionenListe;
	
	public float x1, y1;
	public float x2, y2;
	public boolean platform;
	
	
	// KONSTRUKTOR //
	public Kollision(float px1, float py1, float px2, float py2, boolean pPlatform, boolean forceAlt) {
		x1 = px1;
		y1 = py1;
		x2 = px2;
		y2 = py2;
		platform = pPlatform;
		
		
		if (!forceAlt) {
			int cx = (int)Math.floor(px1 / 10f);
			int cy = (int)Math.floor(py1 / 10f);
			
			if (!(Math.floor(px1 / 10f) == Math.floor(px2 / 10f) && Math.floor(py1 / 10f) == Math.floor(py2 / 10f))) {
				// Split Collision
				
				if (py1 == py2) {
					// Horizontal
					if (px1 < px2) {
						x2 = (float)Math.floor(px1 / 10f) * 10 + 10;
						
						new Kollision(px2, py2, x2 - 0.0001f, py2, pPlatform, forceAlt);
					}
				} else if (px1 == px2) {
					// Vertical
					
				} else {
					// Free
					float m = (py2 - py1) / (px2 - px1);
					float b = py1 - m * px1;
					
					float testX = (cx + 1) * 10;
					if (px2 < px1) testX = cx * 10;
					
					
					if ((int)Math.floor((m * testX + b) / 10) == cy) {
						// y doesnt change
						
						if (testX == (cx * 10)) {
							new Kollision(x2, y2, testX - 0.0001f, m * testX + b, pPlatform, forceAlt);
						} else {
							new Kollision(x2, y2, testX, m * testX + b, pPlatform, forceAlt);
						}
						
						x2 = testX;
						y2 = m * testX + b;
					} else {
						float testY = (cy + 1) * 10;
						if (py2 < py1) testY = cy * 10;
						
						if ((int)Math.floor(((testY - b) / m) / 10) == cx) {
							// x doesnt change
							
							if (testY == (cy * 10)) {
								new Kollision(x2, y2, (testY - b) / m, testY - 0.0001f, pPlatform, forceAlt);
							} else {
								new Kollision(x2, y2, (testY - b) / m, testY, pPlatform, forceAlt);
							}
							
							x2 = (testY - b) / m;
							y2 = testY;
						} else {
							// both change?
							altKollisionenListe.add(this);
							return;
						}
					}
				}
			}
			
			if (!forceAlt && cx >= 0 && cx < kollisionenListe.length && cy >= 0 && cy < kollisionenListe[0].length) {
				if (kollisionenListe[cx][cy] == null) {
					kollisionenListe[cx][cy] = new ArrayList<Kollision>();
				}
				
				kollisionenListe[cx][cy].add(this);
			} else {
				//System.out.println("[!!!] Kollision wurde ausserhalb der Chunk-Range platziert!\n\t(" + x1 + "|" + y1 + ") (" + x2 + "|" + y2 + ")");
				altKollisionenListe.add(this);
			}
		}
	}
	
	
	
	// METHODEN //
	@SuppressWarnings("unchecked")
	public static void init(int chunkW, int chunkH) {
		altKollisionenListe = new ArrayList<Kollision>();
		kollisionenListe = new ArrayList[chunkW][chunkH];
	}
	
	
	// MAIN RAYCAST
	public static RaycastHit raycast(float startX, float startY, float dirX, float dirY, boolean checkPlatform)  {
		dirX = (Math.abs(dirX) < 0.0001f ? 0 : dirX);
		dirY = (Math.abs(dirY) < 0.0001f ? 0 : dirY);
		
		if (dirX == 0 || dirY == 0)
			return raycastSimple(startX, startY, dirX, dirY, checkPlatform);
		
		/*if (dirX == 0)
			dirX = 0.00001f;
		if (dirY == 0)
			dirY = 0.00001f;*/
		
		int chunkX = (int)Math.floor(startX / 10);
		int chunkY = (int)Math.floor(startY / 10);
		
		
		float curPointX = startX;
		float curPointY = startY;
		
		float maxDistance = CMath.distance(0, 0, dirX, dirY);
		
		
		float rayM = dirY / dirX;
		float rayB = (startY - rayM * startX);
		
		RaycastHit rayhit = raycastOutOfMap(startX, startY, dirX, dirY, checkPlatform);
		
		
		while (true) {
			// Check chunk
			//System.out.println("Current chunk: " + chunkX + "; " + chunkY);
			if (chunkX >= 0 && chunkX < kollisionenListe.length && chunkY >= 0 && chunkY < kollisionenListe[0].length && kollisionenListe[chunkX][chunkY] != null) {
				for (int i = 0; i < kollisionenListe[chunkX][chunkY].size(); i++) {
					Kollision col = kollisionenListe[chunkX][chunkY].get(i);
					if (!checkPlatform && col.platform)
						continue;
					
					// Get collision values
					float cutX = -999, cutY = -999;
					float normalX = -1, normalY = -1;
					
					if (col.x1 != col.x2 && col.y1 != col.y2) { // Line cuts line
						float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
						float colB = (col.y1 - colM * col.x1);
						
						if ((rayM - colM) != 0) { // Are lines NOT parallel to each other?
							cutX = (colB - rayB) / (rayM - colM);
							cutY = rayM * cutX + rayB;
							
							float ph = -1f / colM;
							normalX = 1f;
							normalY = normalX * ph;
							normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
							normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
						}
					} else if (col.x1 == col.x2) { // Line cuts vertical line
						cutX = col.x1;
						cutY = rayM * cutX + rayB;
						
						normalX = 1f;
						normalY = 0f;
					} else { // Line cuts horizontal line
						cutY = col.y1;
						cutX = (cutY - rayB) / rayM;
						
						normalX = 0f;
						normalY = 1f;
					}
					
					
					// Did collision occur?
					if (cutX != -999 || cutY != -999) {
						// Is collision within bounds?
						if (col.isInBoundingRect(cutX, cutY) && CMath.distance(startX, startY, cutX, cutY) <= CMath.distance(0, 0, dirX, dirY)) {
							// Did the ray shoot in the right direction?
							if (CMath.angleBetweenDirs(dirX, dirY, cutX - startX, cutY - startY) < 15f) {
								// Is it a new raycast or is it nearer?
								if (rayhit == null || CMath.distance(startX, startY, cutX, cutY) < rayhit.distance) {
									if (CMath.angleBetweenDirs(dirX, dirY, normalX, normalY) < 90) {
										normalX *= -1;
										normalY *= -1;
									}
									
									if (rayhit == null)
										rayhit = new RaycastHit(0, 0, 0, 0, 0);
									
									rayhit.hitX = cutX;
									rayhit.hitY = cutY;
									rayhit.normalX = normalX;
									rayhit.normalY = normalY;
									rayhit.distance = CMath.distance(startX, startY, cutX, cutY);
								}
							}
						}
					}
				}
			}
			
			
			// Get next chunk
			float m = dirY / dirX;
			float b = startY - m * startX;
			
			float phX = 0, phY = 0;
			
			if (dirX < 0)
				phX = ((float)Math.ceil(curPointX / 10) - 1) * 10;
			else
				phX = ((float)Math.floor(curPointX / 10) + 1) * 10;

			phY = m * phX + b;
			
			
			if (chunkY != (int)Math.floor(phY / 10)) {
				if (dirY < 0)
					phY = (float)Math.ceil((curPointY / 10) - 1) * 10;
				else
					phY = (float)Math.floor((curPointY / 10) + 1) * 10;
				
				phX = (phY - b) / m;
			}
			
			
			curPointX = phX;
			curPointY = phY;
			
			
			chunkX = (int)Math.floor(curPointX / 10);
			chunkY = (int)Math.floor(curPointY / 10);
			
			if (dirX < 0 && curPointX == Math.floor(curPointX)) {
				chunkX--;
			} else if (dirY < 0 && curPointY == Math.floor(curPointY)) {
				chunkY--;
			}
			
			
			// Is chunk too far away?
			if (CMath.distance(startX, startY, curPointX, curPointY) > maxDistance) {
				break;
			}
		}
		
		
		return rayhit;
	}
	
	
	// VEREINFACHTES RAYCAST (muesste schneller sein idk)
	private static RaycastHit raycastSimple(float startX, float startY, float dirX, float dirY, boolean checkPlatform)  {
		int chunkX = (int)Math.floor(startX / 10);
		int chunkY = (int)Math.floor(startY / 10);
		int lastChunkX = chunkX, lastChunkY = chunkY;
		
		RaycastHit rayhit = raycastOutOfMap(startX, startY, dirX, dirY, checkPlatform);
		
		if (dirX != 0) // Horizontal
			lastChunkX = (int)Math.floor((startX + dirX) / 10);
		else // Vertical
			lastChunkY = (int)Math.floor((startY + dirY) / 10);
		
		
		while (true) {
			if (chunkX >= 0 && chunkX < kollisionenListe.length && chunkY >= 0 && chunkY < kollisionenListe[0].length && kollisionenListe[chunkX][chunkY] != null) {
				for (int i = 0; i < kollisionenListe[chunkX][chunkY].size(); i++) {
					Kollision col = kollisionenListe[chunkX][chunkY].get(i);
					if (!checkPlatform && col.platform)
						continue;
					
					float cutX = -999, cutY = -999;
					float normalX = -1, normalY = -1;
					
					// Try colliding
					if (dirX != 0) {
						// Horizontal
						if (startY >= col.y1 && startY <= col.y2 || startY >= col.y2 && startY <= col.y1) {
							if (col.x1 != col.x2 && col.y1 != col.y2) {
								// Line
								float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
								float colB = (col.y1 - colM * col.x1);
								
								cutX = (startY - colB) / colM;
								cutY = startY;

								float ph = -1f / colM;
								normalX = 1f;
								normalY = normalX * ph;
								normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
								normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
							} else if (col.x1 == col.x2) {
								// Vertical
								cutX = col.x1;
								cutY = startY;
								
								normalX = 1;
								normalY = 0;
							}
						}
					} else {
						// Vertical
						if (startX >= col.x1 && startX <= col.x2 || startX >= col.x2 && startX <= col.x1) {
							if (col.x1 != col.x2 && col.y1 != col.y2) {
								// Line
								float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
								float colB = (col.y1 - colM * col.x1);
								
								cutX = startX;
								cutY = colM * cutX + colB;

								float ph = -1f / colM;
								normalX = 1f;
								normalY = normalX * ph;
								normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
								normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
							} else if (col.y1 == col.y2) {
								// Horzontal
								cutX = startX;
								cutY = col.y1;
								
								normalX = 0;
								normalY = 1;
							}
						}
					}
					
					// Did collision occur?
					if (cutX != -999 || cutY != -999) {
						// Is collision within bounds?
						if (CMath.distance(startX, startY, cutX, cutY) <= CMath.distance(0, 0, dirX, dirY)) {
							// Did the ray shoot in the right direction?
							if (CMath.angleBetweenDirs(dirX, dirY, cutX - startX, cutY - startY) < 15f) {
								// Is it a new raycast or is it nearer?
								if (rayhit == null || CMath.distance(startX, startY, cutX, cutY) < rayhit.distance) {
									if (CMath.angleBetweenDirs(dirX, dirY, normalX, normalY) < 90) {
										normalX *= -1;
										normalY *= -1;
									}
									
									if (rayhit == null)
										rayhit = new RaycastHit(0, 0, 0, 0, 0);
									
									rayhit.hitX = cutX;
									rayhit.hitY = cutY;
									rayhit.normalX = normalX;
									rayhit.normalY = normalY;
									rayhit.distance = CMath.distance(startX, startY, cutX, cutY);
								}
							}
						}
					}
				}
			}
			
			
			// Update chunk or exit loop
			if (chunkX == lastChunkX && chunkY == lastChunkY)
				break;
			else if (chunkX != lastChunkX) 
				chunkX += Math.signum(lastChunkX - chunkX);
			else if (chunkY != lastChunkY)
				chunkY += Math.signum(lastChunkY - chunkY);
		}
		
		
		
		return rayhit;
	}
	
	
	// LANGSAMES RAYCAST (vermeide es, Kollisionen ausserhalb der Chunk-Range zu platzieren aight)
	private static RaycastHit raycastOutOfMap(float startX, float startY, float dirX, float dirY, boolean checkPlatform) {
		/*if (dirX == 0)
			dirX = 0.01f;
		else if (dirY == 0)
			dirY = 0.01f;
		
		
		float rayM = dirY / dirX;
		float rayB = (startY - rayM * startX);*/
		
		
		RaycastHit rayhit = null;
		
		
		if (dirX != 0 && dirY != 0) {
			// --- Line ---
			
			float rayM = dirY / dirX;
			float rayB = (startY - rayM * startX);
			
			// Check each line in list
			for (int i = 0; i < altKollisionenListe.size(); i++) {
				Kollision col = altKollisionenListe.get(i); // Store collision
				if (!checkPlatform && col.platform) // Skip if you dont want platforms
					continue;
				
				// Prepare collision values
				float cutX = -999, cutY = -999;
				float normalX = -1, normalY = -1;
				
				
				// What am I colliding with?
				
				if (col.x1 != col.x2 && col.y1 != col.y2) {
					// --- Ray hits Line ---
					
					// y = mx + b
					float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
					float colB = (col.y1 - colM * col.x1);
					
					// Are lines NOT parallel to each other?
					if ((rayM - colM) != 0) {
						// Get hit-point
						cutX = (colB - rayB) / (rayM - colM);
						cutY = rayM * cutX + rayB;
						
						// Get normal
						float ph = -1f / colM;
						normalX = 1f;
						normalY = normalX * ph;
						// Normal must have a length of exactly 1
						normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
						normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
					}
				} else if (col.y1 == col.y2) {
					// --- Ray hits Horizontal ---
					
					cutY = col.y1;
					cutX = (cutY - rayB) / rayM;
					
					normalX = 0f;
					normalY = 1f;
				} else {
					// --- Ray hits Vertical ---
					
					cutX = col.x1;
					cutY = rayM * cutX + rayB;
					
					normalX = 1f;
					normalY = 0f;
				}
				

				rayhit = raycastUpdateRayhit(rayhit, col, cutX, cutY, normalX, normalY, startX, startY, dirX, dirY);
			}
		} else if (dirX != 0) {
			// --- Horizontal ---
			
			// Check each line in list
			for (int i = 0; i < altKollisionenListe.size(); i++) {
				Kollision col = altKollisionenListe.get(i); // Store collision
				if (!checkPlatform && col.platform) // Skip if you dont want platforms
					continue;
				
				// Prepare collision values
				float cutX = -999, cutY = -999;
				float normalX = -1, normalY = -1;
				
				
				// What am I colliding with?
				
				if (col.x1 != col.x2 && col.y1 != col.y2) {
					// --- Ray hits Line ---
					
					// y = mx + b
					float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
					float colB = (col.y1 - colM * col.x1);
					
					// Get hit-point
					cutY = startY;
					cutX = (cutY - colB) / colM;
					
					// Get normal
					float ph = -1f / colM;
					normalX = 1f;
					normalY = normalX * ph;
					// Normal must have a length of exactly 1
					normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
					normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
				} else if (col.x1 == col.x2) {
					// --- Ray hits Vertical ---
					
					// Get hit-point
					cutY = startY;
					cutX = col.x1;
					
					// Get normal
					normalX = 1f;
					normalY = 0f;
				}
				// NO ELSE: Horizontal cannot hit Horizontal!
				

				rayhit = raycastUpdateRayhit(rayhit, col, cutX, cutY, normalX, normalY, startX, startY, dirX, dirY);
			}
		} else {
			// --- Vertical ---
			
			// Check each line in list
			for (int i = 0; i < altKollisionenListe.size(); i++) {
				Kollision col = altKollisionenListe.get(i); // Store collision
				if (!checkPlatform && col.platform) // Skip if you dont want platforms
					continue;
				
				// Prepare collision values
				float cutX = -999, cutY = -999;
				float normalX = -1, normalY = -1;
				
				
				// What am I colliding with?
				
				if (col.x1 != col.x2 && col.y1 != col.y2) {
					// --- Ray hits Line ---
					
					// y = mx + b
					float colM = (col.y2 - col.y1) / (col.x2 - col.x1);
					float colB = (col.y1 - colM * col.x1);
					
					// Get hit-point
					cutX = startX;
					cutY = colM * cutX + colB;
					
					// Get normal
					float ph = -1f / colM;
					normalX = 1f;
					normalY = normalX * ph;
					// Normal must have a length of exactly 1
					normalX /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
					normalY /= (float)Math.sqrt(normalX * normalX + normalY * normalY);
				} else if (col.y1 == col.y2) {
					// --- Ray hits Horizontal ---
					
					// Get hit-point
					cutX = startX;
					cutY = col.y1;
					
					// Get normal
					normalX = 0f;
					normalY = 1f;
				}
				// NO ELSE: Vertical cannot hit Vertical!
				

				rayhit = raycastUpdateRayhit(rayhit, col, cutX, cutY, normalX, normalY, startX, startY, dirX, dirY);
			}
		}
		
		
		// Done
		return rayhit;
	}
	
	
	// Using this to shorten the code a bit
	private static RaycastHit raycastUpdateRayhit(RaycastHit rayhit, Kollision col, float cutX, float cutY, float normalX, float normalY, float rayStartX, float rayStartY, float rayDirX, float rayDirY) {
		// Did collision occur?
		if (cutX != -999 || cutY != -999) {
			// Is collision within bounds?
			if (col.isInBoundingRect(cutX, cutY) && CMath.distance(rayStartX, rayStartY, cutX, cutY) <= CMath.distance(0, 0, rayDirX, rayDirY)) {
				// Did the ray shoot in the right direction?
				if (CMath.angleBetweenDirs(rayDirX, rayDirY, cutX - rayStartX, cutY - rayStartY) < 15f) {
					// Is it a new raycast or is it nearer?
					if (rayhit == null || CMath.distance(rayStartX, rayStartY, cutX, cutY) < rayhit.distance) {
						if (CMath.angleBetweenDirs(rayDirX, rayDirY, normalX, normalY) < 90) {
							normalX *= -1;
							normalY *= -1;
						}
						
						if (rayhit == null)
							rayhit = new RaycastHit(0, 0, 0, 0, 0);
						
						rayhit.hitX = cutX;
						rayhit.hitY = cutY;
						rayhit.normalX = normalX;
						rayhit.normalY = normalY;
						rayhit.distance = CMath.distance(rayStartX, rayStartY, cutX, cutY);
					}
				}
			}
		}
		
		return rayhit;
	}
	
	
	private boolean isInBoundingRect(float px, float py) {
		if (px >= x1 && px <= x2 || px >= x2 && px <= x1) {
			if (py >= y1 && py <= y2 || py >= y2 && py <= y1) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public static int getWidth() {
		return kollisionenListe.length;
	}
	
	public static int getHeight() {
		return kollisionenListe[0].length;
	}
	
	
	
	
	// DEBUG
	public static void drawDebug(Kamera dieKamera) {
		dieKamera.setLineWidth(0.075f);
		dieKamera.setFarbe(Color.AQUA);
		
		for (int x = 0; x < kollisionenListe.length; x++) {
			for (int y = 0; y < kollisionenListe[0].length; y++) {
				if (kollisionenListe[x][y] != null) {
					for (int i = 0; i < kollisionenListe[x][y].size(); i++) {
						Kollision k = kollisionenListe[x][y].get(i);
						dieKamera.setFarbe(Color.AQUA);
						dieKamera.drawLine(k.x1, k.y1, k.x2, k.y2);
						dieKamera.setFarbe(Color.DARKBLUE);
						dieKamera.drawRect(k.x1 - 0.1f, k.y1 - 0.1f, 0.2f, 0.2f);
						dieKamera.drawRect(k.x2 - 0.1f, k.y2 - 0.1f, 0.2f, 0.2f);
					}
				}
			}
		}
		
		for (int i = 0; i < altKollisionenListe.size(); i++) {
			Kollision k = altKollisionenListe.get(i);
			dieKamera.setFarbe(Color.CRIMSON);
			dieKamera.drawLine(k.x1, k.y1, k.x2, k.y2);
			dieKamera.setFarbe(Color.DARKBLUE);
			dieKamera.drawRect(k.x1 - 0.1f, k.y1 - 0.1f, 0.2f, 0.2f);
			dieKamera.drawRect(k.x2 - 0.1f, k.y2 - 0.1f, 0.2f, 0.2f);
		}
	}
}
