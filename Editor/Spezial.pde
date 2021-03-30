public class Spezial {
  // ATTRIBUTE //
  public int id;
  public float x, y;
  
  
  // KONSTRUKTOR //
  public Spezial(int pID, float px, float py) {
    id = pID;
    x = px;
    y = py;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(255, 0, 0);
    switch (id) {
      case (0):
        fill(255, 100, 100, 155);
        break;
      case (1):
        fill(225, 120, 100, 155);
        break;
      case (2):
        fill(205, 140, 100, 155);
        break;
    }
    
    dieKamera.drawCircle(x, y, 0.2f);
  }
}
