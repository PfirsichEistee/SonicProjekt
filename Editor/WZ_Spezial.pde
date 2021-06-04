public class WZ_Spezial extends Werkzeug {
  // ATTRIBUTE //
  private int auswahl;
  private float cursX, cursY;
  
  
  // KONSTRUKTOR //
  public WZ_Spezial() {
    auswahl = 0;
    cursX = 0;
    cursY = 0;
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Spezial");
    
    Toolbox_Item item = new Toolbox_Item("Typ", 0, 0, spezialBezeichnungen.length - 1);
    for (int i = 0; i < spezialBezeichnungen.length; i++) {
      item.setAnzahlText(i, spezialBezeichnungen[i]);
    }
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Bahnlaenge (opt)", 1, 1, 99);
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Grid-Snap", 0, 0, 1);
    item.setAnzahlText(0, "[_]");
    item.setAnzahlText(1, "[x]");
    dieToolbox.addItem(item);
  }
  
  
  // METHODEN //
  public void zeichnen() {
    if (dieToolbox.getAnzahlVonItem(2) == 0) {
      cursX = dieEingabe.cursorX;
      cursY = dieEingabe.cursorY;
    } else {
      cursX = floor(dieEingabe.cursorX * 2) / 2.0;
      cursY = floor(dieEingabe.cursorY * 2) / 2.0;
    }
    
    if (auswahl == 2) {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      
      dieKamera.drawCircle(cursX, cursY, 3);
    } else if (auswahl == 4) {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      dieKamera.drawRect(cursX, cursY + 0.5f, 1, 0.5f);
    } else if (auswahl == 5) {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      dieKamera.drawRect(cursX, cursY + 1, 4, 1);
    } else if (auswahl == 6) {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      dieKamera.drawRect(cursX, cursY + 4, 8, 4);
    } else if (auswahl == 7) {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      dieKamera.drawRect(cursX, cursY + 1, dieToolbox.getAnzahlVonItem(1), 1);
    } else {
      fill(255, 0, 0, 100);
      stroke(0, 0, 0, 0);
      dieKamera.drawRect(cursX, cursY + 1, 1, 1);
    }
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
      dasLevel.liste_Spezial.add(new Spezial(auswahl, cursX, cursY, dieToolbox.getAnzahlVonItem(1)));
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
