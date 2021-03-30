public class WZ_Spezial extends Werkzeug {
  // ATTRIBUTE //
  private String[] spezialBezeichnungen;
  private int auswahl;
  
  
  // KONSTRUKTOR //
  public WZ_Spezial() {
    spezialBezeichnungen = new String[3];
    spezialBezeichnungen[0] = "Blume";
    spezialBezeichnungen[1] = "Schlangenbahn";
    spezialBezeichnungen[2] = "Looping";
    
    auswahl = 0;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(0);
    fill(0);
    dieKamera.drawText(spezialBezeichnungen[auswahl], dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    
    
    fill(75, 75, 255, 155);
    stroke(0, 0, 0, 0);
    if (!istCursorBeiPunkt(0, 0)) {
      dieKamera.drawCircle(dieEingabe.cursorX, dieEingabe.cursorY, 0.2f);
    } else {
      dieKamera.drawCircle(0, 0, 0.2f);
    }
  }
  public void cursorMoved(float deltaX, float deltaY) {
    
  }
  public void cursorPressed(int button) {
    
  }
  public void cursorReleased(int button) {
    
  }
  public void cursorClicked(int button) {
    dasLevel.liste_Spezial.add(new Spezial(0, dieEingabe.cursorX, dieEingabe.cursorY));
  }
}
