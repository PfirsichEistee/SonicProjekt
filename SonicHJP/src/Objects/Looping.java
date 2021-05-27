package Objects;

import app.Kollision;

public class Looping {
	// ATTRIBUTE //
	private Kollision[] kollisionenLinks;
	private Kollision[] kollisionenRechts;
	private boolean leftActive;
	
	
	// KONSTRUKTOR //
	public Looping(Kollision[] pKollisionenLinks, Kollision[] pKollisionenRechts) {
		kollisionenLinks = pKollisionenLinks;
		kollisionenRechts = pKollisionenRechts;
		
		
		leftActive = false;
		
		for (int i = 0; i < kollisionenLinks.length; i++) {
			Kollision.altRemove(kollisionenLinks[i]);
		}
	}
	
	
	// METHODEN //
	public void enterLeft() {
		if (leftActive) {
			leftActive = false;
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altRemove(kollisionenLinks[i]);
			}
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altAdd(kollisionenRechts[i]);
			}
		}
	}
	public void enterRight() {
		if (!leftActive) {
			leftActive = true;
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altRemove(kollisionenRechts[i]);
			}
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altAdd(kollisionenLinks[i]);
			}
		}
	}
	public void flip() {
		if (leftActive) {
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altRemove(kollisionenLinks[i]);
			}
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altAdd(kollisionenRechts[i]);
			}
		} else {
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altRemove(kollisionenRechts[i]);
			}
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altAdd(kollisionenLinks[i]);
			}
		}
		
		leftActive = !leftActive;
	}
}
