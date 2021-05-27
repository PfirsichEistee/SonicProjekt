public class WZ_Objekte extends Werkzeug {
  // ATTRIBUTE //
  private int auswahl;
  private boolean gerundet;
  private int lastMillis;
  
  
  // KONSTRUKTOR //
  public WZ_Objekte() {
    auswahl = 0;
    gerundet = false;
    lastMillis = 0;
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Objekte");
    
    Toolbox_Item item = new Toolbox_Item("Typ", 0, 0, objektBezeichnungen.length - 1);
    for (int i = 0; i < objektBezeichnungen.length; i++) {
      item.setAnzahlText(i, objektBezeichnungen[i]);
    }
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Gerundet", 0, 0, 1);
    item.setAnzahlText(0, "[_]");
    item.setAnzahlText(1, "[x]");
    dieToolbox.addItem(item);
  }
  
  
  // METHODEN //
  public void zeichnen() {
    
  }
  
  boolean lockMoveClick = false;
  float startClickX = -999;
  float startClickY = -999;
  public void cursorMoved(float deltaX, float deltaY) {
    if (lockMoveClick) {
      float cursX = dieEingabe.cursorX;
      float cursY = dieEingabe.cursorY;
      
      if (dist(startClickX, startClickY, cursX, cursY) > 0.4f) {
        lockMoveClick = false;
      }
      
      return;
    }
    
    if (dieEingabe.istMausButtonGedrueckt(0)) {
      if ((millis() - lastMillis) >= 100 || gerundet) {
        lastMillis = millis();
        cursorClicked(0);
        
        startClickX = dieEingabe.cursorX;
        startClickY = dieEingabe.cursorY;
        lockMoveClick = true;
      }
    } else if (dieEingabe.istMausButtonGedrueckt(1) && (millis() - lastMillis) >= 100) {
      lastMillis = millis();
      cursorClicked(1);
      
      startClickX = dieEingabe.cursorX;
      startClickY = dieEingabe.cursorY;
      lockMoveClick = true;
    }
  }
  public void cursorPressed(int button) {
    startClickX = dieEingabe.cursorX;
    startClickY = dieEingabe.cursorY;
    lockMoveClick = true;
  }
  public void cursorReleased(int button) {
    
  }
  public void cursorClicked(int button) {
    if (button == 0) {
      // PLATZIEREN
      float cursX = dieEingabe.cursorX;
      float cursY = dieEingabe.cursorY;
      if (gerundet) {
        cursX = round(cursX);
        cursY = round(cursY);
        
        for (Objekt o : dasLevel.liste_Objekte) {
          if (o.x == cursX && o.y == cursY) return;
        }
      }
      
      dasLevel.liste_Objekte.add(new Objekt(auswahl, cursX, cursY));
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
    toolboxKeyPressed(k);
    auswahl = dieToolbox.getAnzahlVonItem(0);
    gerundet = (dieToolbox.getAnzahlVonItem(1) == 0 ? false : true);
  }
  public void tasteLosgelassen(char k) {
    
  }
}


// Wenn du ein neues Objekt erzeugen willst:
//    liste_Objekte.add( new Objekt( ART?, POS_X?, POS_Y? ) );
