public class Spezial {
  // ATTRIBUTE //
  public int id;
  public float x, y;
  public int opt; // optional. Fuer die meisten Objekte unwichtig
  
  
  // KONSTRUKTOR //
  public Spezial(int pID, float px, float py) {
    id = pID;
    x = px;
    y = py;
    opt = 0;
  }
  public Spezial(int pID, float px, float py, int pOpt) {
    id = pID;
    x = px;
    y = py;
    opt = pOpt;
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
    
    dieKamera.drawCircle(x, y, 0.1f);
    fill(255, 0, 0);
    dieKamera.drawText(spezialBezeichnungen[id] + " (" + opt + ")", x - 0.5f, y + 0.3f, 0.2f);
  }
}
