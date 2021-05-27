public class LoopingBox {
  // ATTRIBUTE //
  public float x, y, w, h;
  public int id;
  public int typ; // 3 == trigger links; 4 == trigger rechts; 5 == trigger oben
  
  
  // KONSTRUKTOR //
  public LoopingBox(float px, float py, float pw, float ph, int pID, int pTyp) {
    x = px;
    y = py;
    w = pw;
    h = ph;
    id = pID;
    typ = pTyp;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    strokeWeight(2);
    fill(255, 75, 75, 125);
    dieKamera.drawRect(x, y + h, w, h);
    
    float mx = x + w / 2;
    float my = y + h / 2;
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
