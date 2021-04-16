class WZ_Gegner extends Werkzeug{
  private int auswahl;
  private int richtung;
  
  
  // KONSTRUKTOR //
  public WZ_Gegner() {
    
    
    auswahl = 0;
  }
  
  public void zeichnen() {
    stroke(0);
    fill(0);
    dieKamera.drawText(objektBezeichnungen[auswahl], dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    
    dieKamera.drawText("Richtung: "+ richtung, dieEingabe.cursorX, dieEingabe.cursorY + 0.9f, 0.4f);
    
    textSize(16);
    text("Werkzeug: Gegner\nItem wechseln mit 'X'\nPfeiltasten: Auswahl Richtung \nLinks-Click: Platzieren\nRechts-Click: Loeschen", 20, height - 120);
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
      dasLevel.liste_Gegner.add(new Gegner(auswahl, dieEingabe.cursorX, dieEingabe.cursorY, richtung));
    } else if (button == 1) {
      // LOESCHEN
      if (dasLevel.liste_Gegner.size() > 0) {
        int naechsterIndex = 0;
        Gegner nGegner = dasLevel.liste_Gegner.get(0);
        
        for (int i = 1; i < dasLevel.liste_Gegner.size(); i++) {
          Gegner ph = dasLevel.liste_Gegner.get(i);
          
          if (dist(dieEingabe.cursorX, dieEingabe.cursorY, ph.x, ph.y) < dist(dieEingabe.cursorX, dieEingabe.cursorY, nGegner.x, nGegner.y)) {
            naechsterIndex = i;
            nGegner = ph;
          }
        }
        
        if (istCursorNahePunkt(nGegner.x, nGegner.y)) {
          dasLevel.liste_Gegner.remove(naechsterIndex);
        }
      }
    }
  }
  public void tasteGedrueckt(char k) {
    if (k == 'X') {
      auswahl++;
      if (auswahl >= objektBezeichnungen.length)
      auswahl = 0;
    }
    if(keyCode == 37){
      richtung = 0;
    }
    else if(keyCode == 39){
      richtung = 1;
    }
  }
  public void tasteLosgelassen(char k) {
    
  }
}
