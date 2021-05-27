public class LoopingStrecke {
  // ATTRIBUTE //
  public float x1, y1, x2, y2;
  public int id;
  public int typ; // 0 == normal; 1 == links; 2 == rechts
  
  
  // KONSTRUKTOR //
  public LoopingStrecke(float px1, float py1, float px2, float py2, int pID, int pTyp) {
    x1 = px1;
    y1 = py1;
    x2 = px2;
    y2 = py2;
    id = pID;
    typ = pTyp;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    if (typ == 0)
      stroke(0, 255, 0);
    else if (typ == 1)
      stroke(255, 150, 0);
    else
      stroke(25, 200, 100);
    
    strokeWeight(6);
    
    dieKamera.drawLine(x1, y1, x2, y2);
    
    
    float mx = (x2 - x1) / 2 + x1;
    float my = (y2 - y1) / 2 + y1;
    float txth = 0.3f;
    fill(0);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx, my, txth);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx - 0.02, my - 0.02, txth);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx + 0.02, my - 0.02, txth);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx - 0.02, my + 0.02, txth);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx + 0.02, my + 0.02, txth);
    fill(255);
    dieKamera.drawText("ID: " + id + "\n(" + typ + ")", mx, my, txth);
  }
}
