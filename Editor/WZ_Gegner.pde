class WZ_Gegner extends Werkzeug{
  private int auswahl;
  private int richtung;
  
  
  // KONSTRUKTOR //
  public WZ_Gegner() {
    auswahl = 0;
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Gegner");
    
    Toolbox_Item item = new Toolbox_Item("Typ", 0, 0, gegnerBezeichnungen.length - 1);
    for (int i = 0; i < gegnerBezeichnungen.length; i++) {
      item.setAnzahlText(i, gegnerBezeichnungen[i]);
    }
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Richtung", 0, 0, 1);
    item.setAnzahlText(0, "Links");
    item.setAnzahlText(1, "Rechts");
    dieToolbox.addItem(item);
  }
  
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
    toolboxKeyPressed(k);
    auswahl = dieToolbox.getAnzahlVonItem(0);
    richtung = dieToolbox.getAnzahlVonItem(1);
  }
  public void tasteLosgelassen(char k) {
    
  }
}
