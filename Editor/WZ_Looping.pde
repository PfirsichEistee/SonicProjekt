class WZ_Looping extends Werkzeug{
  int operation;
  
  
  // KONSTRUKTOR //
  public WZ_Looping() {
    operation = 0;
    
    // TOOLBOX
    dieToolbox = new Toolbox("Werkzeug: Looping");
    
    Toolbox_Item item = new Toolbox_Item("ID", 0, 0, 99);
    dieToolbox.addItem(item);
    
    item = new Toolbox_Item("Operation", 0, 0, 5);
    item.setAnzahlText(0, "Kol. Links");
    item.setAnzahlText(1, "Kol. Rechts");
    item.setAnzahlText(2, "Kol. Statisch");
    item.setAnzahlText(3, "Trigger Links");
    item.setAnzahlText(4, "Trigger Rechts");
    item.setAnzahlText(5, "Trigger Oben");
    dieToolbox.addItem(item);
  }
  
  public void zeichnen() {
    strokeWeight(2);
    float txtHeight = (1 / dieKamera.pixelProEinheit) * 25;
    
    fill(200, 25, 25);
    dieKamera.drawText("Ein Click konvertiert eine\nbestehende Strecke zu einer\n\"Looping\"-Strecke. Die jeweiligen\nLoopings werden durch ihre\nID's differenziert.", dieKamera.x, dieKamera.y - dieKamera.getHeight() + txtHeight * 7, txtHeight);
    
    
    fill(255, 75, 75, 125);
    switch (operation) {
      case(3):
      case(4):
        dieKamera.drawRect(dieEingabe.cursorX, dieEingabe.cursorY + 8, 2, 8);
        break;
      case(5):
        dieKamera.drawRect(dieEingabe.cursorX, dieEingabe.cursorY + 2, 3, 2);
        break;
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
      // SETZEN/PLATZIEREN
      if (operation > 2) {
        switch (operation) {
          case(3):
          case(4):
            dasLevel.liste_LoopingBox.add(new LoopingBox(dieEingabe.cursorX, dieEingabe.cursorY, 2, 8, dieToolbox.getAnzahlVonItem(0), operation));
            break;
          case(5):
            dasLevel.liste_LoopingBox.add(new LoopingBox(dieEingabe.cursorX, dieEingabe.cursorY, 3, 2, dieToolbox.getAnzahlVonItem(0), operation));
            break;
        }
        
        
        return;
      }
      
      
      if (operation == 2) {
        // Statische Kollisionen waren eine sehr blöde Idee. Ich hab jetzt aber gar kein Bock die wieder wegzumachen bruh
        return;
      }
      
      
      int nearest = -1;
      float nearestDist = 99999999;
      for (int i = 0; i < dasLevel.liste_Strecken.size(); i++) {
        float ph = getNaechsteDistanzZuStrecke(i, dieEingabe.cursorX, dieEingabe.cursorY, true);
        
        if (ph < nearestDist) {
          nearestDist = ph;
          nearest = i;
        }
      }
      
      if (nearest != -1 && nearestDist < 0.5f) {
        Strecke str = dasLevel.liste_Strecken.get(nearest);
        
        dasLevel.liste_LoopingStrecken.add(new LoopingStrecke(str.x1, str.y1, str.x2, str.y2, dieToolbox.getAnzahlVonItem(0), operation));
        
        dasLevel.liste_Strecken.remove(nearest);
      }
    } else if (button == 1) {
      // LOESCHEN
      int nearest = -1;
      float nearestDist = 99999999;
      for (int i = 0; i < dasLevel.liste_LoopingStrecken.size(); i++) {
        float ph = getNaechsteDistanzZuStrecke(i, dieEingabe.cursorX, dieEingabe.cursorY, false);
        
        if (ph < nearestDist) {
          nearestDist = ph;
          nearest = i;
        }
      }
      
      if (nearest != -1 && nearestDist < 0.5f) {
        dasLevel.liste_LoopingStrecken.remove(nearest);
      }
    }
  }
  public void tasteGedrueckt(char k) {
    toolboxKeyPressed(k);
    operation = dieToolbox.getAnzahlVonItem(1);
  }
  public void tasteLosgelassen(char k) {
    
  }
  
  
  private float getNaechsteDistanzZuStrecke(int i, float px, float py, boolean standardStrecken) {
    float x1 = -99, y1 = -99, x2 = -99, y2 = -99;
    if (standardStrecken) {
      x1 = dasLevel.liste_Strecken.get(i).x1;
      y1 = dasLevel.liste_Strecken.get(i).y1;
      x2 = dasLevel.liste_Strecken.get(i).x2;
      y2 = dasLevel.liste_Strecken.get(i).y2;
    } else {
      x1 = dasLevel.liste_LoopingStrecken.get(i).x1;
      y1 = dasLevel.liste_LoopingStrecken.get(i).y1;
      x2 = dasLevel.liste_LoopingStrecken.get(i).x2;
      y2 = dasLevel.liste_LoopingStrecken.get(i).y2;
    }
    
    if (x1 == x2) x2 += 0.0001f; // vertikal
    if (y1 == y2) y2 += 0.0001f; // horizontal
    
    float m = (y2 - y1) / (x2 - x1);
    float b = y1 - (m * x1);
    
    
    float pointM = -1 / m;
    float pointB = py - (pointM * px);
    
    
    float cutX = (b - pointB) / (pointM - m);
    float cutY = m * cutX + b;
    
    
    if (cutX < x1 && cutX < x2 || cutX > x2 && cutX > x1) {
      float deltaX1 = px - x1;
      float deltaY1 = py - y1;
      float deltaX2 = px - x2;
      float deltaY2 = py - y2;
      
      if (sqrt(deltaX1 * deltaX1 + deltaY1 * deltaY1) < sqrt(deltaX2 * deltaX2 + deltaY2 * deltaY2))
        return sqrt(deltaX1 * deltaX1 + deltaY1 * deltaY1);
      else
        return sqrt(deltaX2 * deltaX2 + deltaY2 * deltaY2);
    }
    
    
    float deltaX = px - cutX;
    float deltaY = py - cutY;
    
    return sqrt(deltaX * deltaX + deltaY * deltaY);
  }
}
