public class WZ_Spezial extends Werkzeug {
  // ATTRIBUTE //
  private int auswahl;
  
  
  // KONSTRUKTOR //
  public WZ_Spezial() {
    auswahl = 0;
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Spezial");
    
    Toolbox_Item item = new Toolbox_Item("Typ", 0, 0, spezialBezeichnungen.length - 1);
    for (int i = 0; i < spezialBezeichnungen.length; i++) {
      item.setAnzahlText(i, spezialBezeichnungen[i]);
    }
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Bahnlaenge", 1, 1, 10);
    dieToolbox.addItem(item);
  }
  
  
  // METHODEN //
  public void zeichnen() {
    
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
      dasLevel.liste_Spezial.add(new Spezial(auswahl, dieEingabe.cursorX, dieEingabe.cursorY, dieToolbox.getAnzahlVonItem(1)));
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
    toolboxKeyPressed(k);
    auswahl = dieToolbox.getAnzahlVonItem(0);
  }
  public void tasteLosgelassen(char k) {
    
  }
}
