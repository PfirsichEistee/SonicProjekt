class WZ_Wasserfall extends Werkzeug {
  // KONSTRUKTOR //
  public WZ_Wasserfall() {
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Wasserfall");
    
    Toolbox_Item item = new Toolbox_Item("Laenge", 1, 1, 10);
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Hoehe", 1, 1, 25);
    dieToolbox.addItem(item);
  }
  
  public void zeichnen() {
    stroke(0, 0, 255);
    strokeWeight(1);
    fill(100, 255, 100, 155);
    
    dieKamera.drawRect(dieEingabe.cursorX, dieEingabe.cursorY, dieToolbox.getAnzahlVonItem(0) * 2, -dieToolbox.getAnzahlVonItem(1) * 2);
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
      dasLevel.liste_Wasserfall.add(new Wasserfall(dieEingabe.cursorX, dieEingabe.cursorY, dieToolbox.getAnzahlVonItem(0), dieToolbox.getAnzahlVonItem(1)));
    } else if (button == 1) {
      // LOESCHEN
      for (int i = 0; i < dasLevel.liste_Wasserfall.size(); i++) {
        Wasserfall ph = dasLevel.liste_Wasserfall.get(i);
        
        if (dist(dieEingabe.cursorX, dieEingabe.cursorY, ph.x, ph.y) < 2) {
          dasLevel.liste_Wasserfall.remove(i);
          break;
        }
      }
    }
  }
  public void tasteGedrueckt(char k) {
    toolboxKeyPressed(k);
    //auswahl = dieToolbox.getAnzahlVonItem(0);
    //richtung = dieToolbox.getAnzahlVonItem(1);
  }
  public void tasteLosgelassen(char k) {
    
  }
}
