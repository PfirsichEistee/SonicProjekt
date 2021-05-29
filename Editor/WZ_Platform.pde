class WZ_Platform extends Werkzeug{
  
  
  // KONSTRUKTOR //
  public WZ_Platform() {
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Platform");
    
    Toolbox_Item item = new Toolbox_Item("ID", 0, 0, 1);
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("X-Target", 0, -100, 100);
    dieToolbox.addItem(item);
    item = new Toolbox_Item("Y-Target", 0, -100, 100);
    dieToolbox.addItem(item);
  }
  
  public void zeichnen() {
    stroke(0);
    strokeWeight(2);
    fill(255, 100, 255);
    
    float ph = sin(millis() * 0.005f) * 0.5f + 0.5f;
    
    float posX = dieEingabe.cursorX + (dieToolbox.getAnzahlVonItem(1) / 2f) * ph;
    float posY = dieEingabe.cursorY + (dieToolbox.getAnzahlVonItem(2) / 2f) * ph;
    
    float pw = (dieToolbox.getAnzahlVonItem(0) == 0 ? 2 : 1.5f);
    
    dieKamera.drawRect(posX, posY - 0.1, pw, 0.1f);
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
      Platform p = new Platform(dieToolbox.getAnzahlVonItem(0), dieEingabe.cursorX, dieEingabe.cursorY, dieToolbox.getAnzahlVonItem(1) / 2f, dieToolbox.getAnzahlVonItem(2) / 2f);
      
      dasLevel.liste_Platformen.add(p);
    } else if (button == 1) {
      // LOESCHEN
      for (int i = 0; i < dasLevel.liste_Platformen.size(); i++) {
        Platform p = dasLevel.liste_Platformen.get(i);
        
        if (dist(p.x, p.y, dieEingabe.cursorX, dieEingabe.cursorY) < 1f) {
          dasLevel.liste_Platformen.remove(i);
          break;
        }
      }
    }
  }
  public void tasteGedrueckt(char k) {
    toolboxKeyPressed(k);
  }
  public void tasteLosgelassen(char k) {
    
  }
}
