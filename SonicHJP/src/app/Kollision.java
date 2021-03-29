package app;

import java.util.ArrayList;

public class Kollision {
	// ATTRIBUTE //
	private static ArrayList<Kollision> altKollisionenListe; // Hier kommen Kollisionen rein, die keinem Chunk zugeordnet wurden
	private static ArrayList<Kollision>[][] kollisionenListe;
	private static Spielwelt dieSpielwelt;
	
	public float x1, y1;
	public float x2, y2;
	
	
	// KONSTRUKTOR //
	public Kollision(float px1, float py1, float px2, float py2) {
		x1 = px1;
		y1 = py1;
		x2 = px2;
		y2 = py2;
		
		int cx = (int)Math.floor(px1 / 10f);
		int cy = (int)Math.floor(py1 / 10f);
		if (cx >= 0 && cx < kollisionenListe.length && cy >= 0 && cy < kollisionenListe[0].length) {
			if (kollisionenListe[cx][cy] == null) {
				kollisionenListe[cx][cy] = new ArrayList<Kollision>();
			}
			
			kollisionenListe[cx][cy].add(this);
		} else {
			System.out.println("[!!!] Kollision wurde ausserhalb der Chunk-Range platziert!\n\t(" + x1 + "|" + y1 + ") (" + x2 + "|" + y2 + ")");
			altKollisionenListe.add(this);
		}
	}
	
	
	
	// METHODEN //
	@SuppressWarnings("unchecked")
	public static void init(int chunkW, int chunkY, Spielwelt pSpielwelt) {
		altKollisionenListe = new ArrayList<Kollision>();
		kollisionenListe = new ArrayList[chunkW][chunkY];
		dieSpielwelt = pSpielwelt;
	}
	
	
	// MAIN RAYCAST
	public static RaycastHit raycast(float startX, float startY, float dirX, float dirY)  {
		if (dirX == 0 || dirY == 0)
			return raycastSimple(startX, startY, dirX, dirY);
		
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
		
		RaycastHit rayhit = null;
		
		
		while (true) {
			// Check chunk
			//System.out.println("Current chunk: " + chunkX + "; " + chunkY);
			if (chunkX >= 0 && chunkX < kollisionenListe.length && chunkY >= 0 && chunkY < kollisionenListe[0].length && kollisionenListe[chunkX][chunkY] != null) {
				for (int i = 0; i < kollisionenListe[chunkX][chunkY].size(); i++) {
					Kollision col = kollisionenListe[chunkX][chunkY].get(i);
					
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
						normalY = 0f;
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
		
		
		
		if (rayhit == null)
			return raycastOutOfMap(startX, startY, dirX, dirY);
		
		return rayhit;
	}
	
	
	// VEREINFACHTES RAYCAST (muesste schneller sein idk)
	private static RaycastHit raycastSimple(float startX, float startY, float dirX, float dirY)  {
		int chunkX = (int)Math.floor(startX / 10);
		int chunkY = (int)Math.floor(startY / 10);
		int lastChunkX = chunkX, lastChunkY = chunkY;
		
		RaycastHit rayhit = null;
		
		if (dirX != 0) // Horizontal
			lastChunkX = (int)Math.floor((startX + dirX) / 10);
		else // Vertical
			lastChunkY = (int)Math.floor((startY + dirY) / 10);
		
		
		while (true) {
			if (chunkX >= 0 && chunkX < kollisionenListe.length && chunkY >= 0 && chunkY < kollisionenListe[0].length && kollisionenListe[chunkX][chunkY] != null) {
				for (int i = 0; i < kollisionenListe[chunkX][chunkY].size(); i++) {
					Kollision col = kollisionenListe[chunkX][chunkY].get(i);
					
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
								// Vertical
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
		
		
		
		if (rayhit == null)
			return raycastOutOfMap(startX, startY, dirX, dirY);
		
		return rayhit;
	}
	
	
	// LANGSAMES RAYCAST (vermeide es, Kollisionen ausserhalb der Chunk-Range zu platzieren aight)
	private static RaycastHit raycastOutOfMap(float startX, float startY, float dirX, float dirY) {
		return null;
	}
	
	
	public boolean isInBoundingRect(float px, float py) {
		if (px >= x1 && px <= x2 || px >= x2 && px <= x1) {
			if (py >= y1 && py <= y2 || py >= y2 && py <= y1) {
				return true;
			}
		}
		
		return false;
	}
}
