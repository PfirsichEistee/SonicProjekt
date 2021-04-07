public class WZ_Objekte extends Werkzeug {
  // ATTRIBUTE //
  private String[] spezialBezeichnungen;
  private int auswahl;
  
  
  // KONSTRUKTOR //
  public WZ_Objekte() {
    spezialBezeichnungen = new String [4];
    spezialBezeichnungen[0] = "Ringe";
    spezialBezeichnungen[1] = "Checkpoint";
    spezialBezeichnungen[2] = "Schild";
    spezialBezeichnungen[3] = "Powerup"; //Unsterblichkeit
    
    
    auswahl = 0;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(0);
    fill(0);
    dieKamera.drawText(spezialBezeichnungen[auswahl], dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    
    textSize(16);
    text("Werkzeug: Objekte\nItem wechseln mit 'X'\nLinks-Click: Platzieren\nRechts-Click: Loeschen", 20, height - 100);
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
      dasLevel.liste_Objekte.add(new Objekt(auswahl, spezialBezeichnungen[auswahl], dieEingabe.cursorX, dieEingabe.cursorY));
    } else if (button == 1) {
      // LOESCHEN
      if (dasLevel.liste_Objekte.size() > 0) {
        int naechsterIndex = 0;
        Objekt nObjekte = dasLevel.liste_Objekte.get(0);
        
        for (int i = 1; i < dasLevel.liste_Objekte.size(); i++) {
          Objekt ph = dasLevel.liste_Objekte.get(i);
          
          if (dist(dieEingabe.cursorX, dieEingabe.cursorY, ph.x, ph.y) < dist(dieEingabe.cursorX, dieEingabe.cursorY, nObjekte.x, nObjekte.y)) {
            naechsterIndex = i;
            nObjekte = ph;
          }
        }
        
        if (istCursorNahePunkt(nObjekte.x, nObjekte.y)) {
          dasLevel.liste_Objekte.remove(naechsterIndex);
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


// Wenn du ein neues Objekt erzeugen willst:
//    liste_Objekte.add( new Objekt( ART?, POS_X?, POS_Y? ) );
