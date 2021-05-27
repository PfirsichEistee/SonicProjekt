public class Strecke {
  // ATTRIBUTE //
  public float x1, y1, x2, y2;
  public int typ; // 0 == normal; 1 == platform
  
  
  // KONSTRUKTOR //
  public Strecke(float px1, float py1, float px2, float py2, int pTyp) {
    x1 = px1;
    y1 = py1;
    x2 = px2;
    y2 = py2;
    typ = pTyp;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    if (typ == 0)
      stroke(75, 75, 255);
    else
      stroke(255, 200, 75);
    
    strokeWeight(6);
    
    dieKamera.drawLine(x1, y1, x2, y2);
  }
}
