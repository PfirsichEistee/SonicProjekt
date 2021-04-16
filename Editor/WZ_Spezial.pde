public class WZ_Spezial extends Werkzeug {
  // ATTRIBUTE //
  private int auswahl;
  
  
  // KONSTRUKTOR //
  public WZ_Spezial() {
    auswahl = 0;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(0);
    fill(0);
    dieKamera.drawText(spezialBezeichnungen[auswahl], dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    
    textSize(16);
    text("Werkzeug: Spezial\nSpezial Item wechseln mit 'X'\nLinks-Click: Platzieren\nRechts-Click: Loeschen", 20, height - 100);
  }
  public void cursorMoved(float deltaX, float deltaY) {
    
  }
  public void cursorPressed(int button) {
    
  }
  public void cursorReleased(int button) {
    
  }
  public void cursorClicked(int button) {
    if (button == 0) {
      // PLATZIEREN
      dasLevel.liste_Spezial.add(new Spezial(auswahl, dieEingabe.cursorX, dieEingabe.cursorY));
    } else if (button == 1) {
      // LOESCHEN
      if (dasLevel.liste_Spezial.size() > 0) {
        int naechsterIndex = 0;
        Spezial nSpezial = dasLevel.liste_Spezial.get(0);
        
        for (int i = 1; i < dasLevel.liste_Spezial.size(); i++) {
          Spezial ph = dasLevel.liste_Spezial.get(i);
          
          if (dist(dieEingabe.cursorX, dieEingabe.cursorY, ph.x, ph.y) < dist(dieEingabe.cursorX, dieEingabe.cursorY, nSpezial.x, nSpezial.y)) {
            naechsterIndex = i;
            nSpezial = ph;
          }
        }
        
        if (istCursorNahePunkt(nSpezial.x, nSpezial.y)) {
          dasLevel.liste_Spezial.remove(naechsterIndex);
        }
      }
    }
  }
  public void tasteGedrueckt(char k) {
    if (k == 'X') {
      auswahl++;
      if (auswahl >= spezialBezeichnungen.length)
        auswahl = 0;
    }
  }
  public void tasteLosgelassen(char k) {
    
  }
}
