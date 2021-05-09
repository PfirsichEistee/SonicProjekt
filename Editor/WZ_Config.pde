class WZ_Config extends Werkzeug {
  
  
  // KONSTRUKTOR //
  public WZ_Config() {
    
    // TOOLBOX
    dieToolbox = new Toolbox("Level-Konfiguration");
    
    Toolbox_Item item = new Toolbox_Item("Map ID", 0, 0, 99);
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Pixel pro Einheit", dasLevel.levelPixelProEinheit, 8, 256);
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
    
  }
  public void tasteGedrueckt(char k) {
    toolboxKeyPressed(k);
    dasLevel.levelMapID = dieToolbox.getAnzahlVonItem(0);
    dasLevel.levelPixelProEinheit = dieToolbox.getAnzahlVonItem(1);
  }
  public void tasteLosgelassen(char k) {
    
  }
}
