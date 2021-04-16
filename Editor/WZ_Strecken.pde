class WZ_Strecken extends Werkzeug {
  // ATTRIBUTE //
  private int selectedIndex, selectedPoint; // selectedPoint == 0 or 1
  
  private Float newX1, newY1, newX2, newY2;
  
  private boolean platzieren;
  private boolean snap;
  private int kollisionTyp;
  
  
  // KONSTRUKTOR //
  public WZ_Strecken() {
    selectedIndex = -1;
    platzieren = true;
    snap = false;
    kollisionTyp = 0;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(255, 75, 75);
    strokeWeight(6);
    if (newX1 != null)
      dieKamera.drawLine(newX1, newY1, newX2, newY2);
    
    
    fill(0);
    
    if (platzieren)
      dieKamera.drawText("Platzieren", dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    else
      dieKamera.drawText("Bearbeiten", dieEingabe.cursorX, dieEingabe.cursorY + 0.5f, 0.4f);
    
    dieKamera.drawText("Snap: " + snap, dieEingabe.cursorX, dieEingabe.cursorY + 0.9f, 0.4f);
    dieKamera.drawText("Kollision: " + ((kollisionTyp == 0) ? "Normal" : "Platform"), dieEingabe.cursorX, dieEingabe.cursorY + 1.3f, 0.4f);
    
    textSize(16);
    text("Werkzeug: Strecken\nSnap wechseln mit 'X'\nBau-Modus wechseln mit 'C'\nCollision aendern mit 'V'\nLinks-Click (halten): Platzieren\nRechts-Click: Loeschen", 20, height - 140);
  }
  public void cursorMoved(float deltaX, float deltaY) {
    if (dieEingabe.istMausButtonGedrueckt(0)) {
      float targetX = dieEingabe.cursorX;
      float targetY = dieEingabe.cursorY;
      
      if (snap) {
        for (int i = 0; i < dasLevel.liste_Strecken.size(); i++) {
          if (i != selectedIndex) {
            float px = dasLevel.liste_Strecken.get(i).x1;
            float py = dasLevel.liste_Strecken.get(i).y1;
            
            for (int k = 0; k < 2; k++) {
              if ((dist(targetX, targetY, px, py) * dieKamera.pixelProEinheit) < 30) {
                targetX = px;
                targetY = py;
                break;
              }
              
              px = dasLevel.liste_Strecken.get(i).x2;
              py = dasLevel.liste_Strecken.get(i).y2;
            }
          }
        }
      }
      
      
      if (selectedIndex == -1) {
        newX2 = targetX;
        newY2 = targetY;
      } else {
        if (selectedPoint == 0) {
          dasLevel.liste_Strecken.get(selectedIndex).x1 = targetX;
          dasLevel.liste_Strecken.get(selectedIndex).y1 = targetY;
        } else {
          dasLevel.liste_Strecken.get(selectedIndex).x2 = targetX;
          dasLevel.liste_Strecken.get(selectedIndex).y2 = targetY;
        }
      }
    }
  }
  public void cursorPressed(int button) {
    if (button == 0) {
      if (platzieren) {
        newX1 = dieEingabe.cursorX;
        newY1 = dieEingabe.cursorY;
        
        newX2 = dieEingabe.cursorX;
        newY2 = dieEingabe.cursorY;
        
        selectedIndex = -1;
      } else {
        selectedIndex = -1;
        float selX = -1;
        float selY = -1;
        for (int i = 0; i < dasLevel.liste_Strecken.size(); i++) {
          float x1 = dasLevel.liste_Strecken.get(i).x1;
          float y1 = dasLevel.liste_Strecken.get(i).y1;
          float x2 = dasLevel.liste_Strecken.get(i).x2;
          float y2 = dasLevel.liste_Strecken.get(i).y2;
          selectedPoint = 0;
          
          if (dist(dieEingabe.cursorX, dieEingabe.cursorY, x2, y2) < dist(dieEingabe.cursorX, dieEingabe.cursorY, x1, y1)) {
            x1 = x2;
            y1 = y2;
            selectedPoint = 1;
          }
          
          if ((dist(dieEingabe.cursorX, dieEingabe.cursorY, x1, y1) * dieKamera.pixelProEinheit) <= 30) {
            if (selectedIndex == -1 || dist(dieEingabe.cursorX, dieEingabe.cursorY, x1, y1) < dist(dieEingabe.cursorX, dieEingabe.cursorY, selX, selY)) {
              selectedIndex = i;
              selX = x1;
              selY = y1;
            }
          }
        }
      }
    } else if (button == 1) {
      int i = getNaechsteStreckeZu(dieEingabe.cursorX, dieEingabe.cursorY);
      
      
      if (i != -1) {
        float dis = getNaechsteDistanzZuStrecke(i, dieEingabe.cursorX, dieEingabe.cursorY);
        
        if ((dis * dieKamera.pixelProEinheit) < 60) {
          dasLevel.liste_Strecken.remove(i);
        }
      }
    }
  }
  public void cursorReleased(int button) {
    if (button == 0) {
      if (selectedIndex == -1 && newX1 != null) {
        dasLevel.liste_Strecken.add(new Strecke(newX1, newY1, newX2, newY2, kollisionTyp));
      }
      
      newX1 = null;
      newY1 = null;
      
      newX2 = null;
      newY2 = null;
      
      selectedIndex = -1;
    }
  }
  public void cursorClicked(int button) {
    
  }
  public void tasteGedrueckt(char k) {
    if (k == 'C') {
      platzieren = !platzieren;
    } else if (k == 'X') {
      snap = !snap;
    } else if (k == 'V') {
      kollisionTyp = 1 - kollisionTyp;
    }
  }
  public void tasteLosgelassen(char k) {
    
  }
  
  
  private int getNaechsteStreckeZu(float px, float py) {
    int naechsterIndex = -1;
    float naechsteDistanz = 999999999;
    
    for (int i = 0; i < dasLevel.liste_Strecken.size(); i++) {
      float x1 = dasLevel.liste_Strecken.get(i).x1;
      float y1 = dasLevel.liste_Strecken.get(i).y1;
      float x2 = dasLevel.liste_Strecken.get(i).x2;
      float y2 = dasLevel.liste_Strecken.get(i).y2;
      
      if (x1 == x2) x2 += 0.0001f; // vertikal
      if (y1 == y2) y2 += 0.0001f; // horizontal
      
      float m = (y2 - y1) / (x2 - x1);
      float b = y1 - (m * x1);
      
      
      float pointM = -1 / m;
      float pointB = py - (pointM * px);
      
      
      // m * cutX + b = pointM * cutX + pointB
      // -> m * cutX + b - pointB = pointM * cutX
      // -> b - pointB = pointM * cutX - m * cutX
      // -> b - pointB = cutX * (pointM - m)
      // -> (b - pointB) / (pointM - m) == cutX
      float cutX = (b - pointB) / (pointM - m);
      float cutY = m * cutX + b;
      
      if (cutX >= x1 && cutX <= x2 || cutX >= x2 && cutX <= x1) {
        if (cutY >= y1 && cutY <= y2 || cutY >= y2 && cutY <= y1) {
          float deltaX = px - cutX;
          float deltaY = py - cutY;
          
          if (sqrt(deltaX * deltaX + deltaY * deltaY) < naechsteDistanz) {
            naechsterIndex = i;
            naechsteDistanz = sqrt(deltaX * deltaX + deltaY * deltaY);
          }
        }
      }
    }
    
    return naechsterIndex;
  }
  
  
  private float getNaechsteDistanzZuStrecke(int i, float px, float py) {
    float x1 = dasLevel.liste_Strecken.get(i).x1;
    float y1 = dasLevel.liste_Strecken.get(i).y1;
    float x2 = dasLevel.liste_Strecken.get(i).x2;
    float y2 = dasLevel.liste_Strecken.get(i).y2;
    
    if (x1 == x2) x2 += 0.0001f; // vertikal
    if (y1 == y2) y2 += 0.0001f; // horizontal
    
    float m = (y2 - y1) / (x2 - x1);
    float b = y1 - (m * x1);
    
    
    float pointM = -1 / m;
    float pointB = py - (pointM * px);
    
    
    float cutX = (b - pointB) / (pointM - m);
    float cutY = m * cutX + b;
    
    float deltaX = px - cutX;
    float deltaY = py - cutY;
    
    return sqrt(deltaX * deltaX + deltaY * deltaY);
  }
}
