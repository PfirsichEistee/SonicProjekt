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
        dieKamera.drawRect(x, y, 0.6f, -0.6f);
        break;
      case (1):
        fill(225, 120, 100, 155);
        break;
      case (2):
        fill(205, 140, 100, 155);
        break;
      case (3):
        fill(255, 100, 100, 155);
        dieKamera.drawRect(x, y, 0.6f, -0.6f);
        break;
      case (4):
        fill(255, 100, 100, 155);
        dieKamera.drawRect(x, y + 0.5, 1, 0.5f);
        break;
      case (5):
        fill(255, 100, 100, 155);
        dieKamera.drawRect(x, y + 1, 4, 1);
        break;
      case (6):
        fill(255, 100, 100, 155);
        dieKamera.drawRect(x, y + 4, 8, 4);
        break;
      case (7):
        fill(100, 100, 255, 155);
        dieKamera.drawRect(x, y + 1, opt, 1);
        break;
      case (8):
        fill(255, 50, 50, 155);
        dieKamera.drawRect(x, y + 1, 1, 1);
        stroke(200, 255, 0);
        switch (opt) {
          case(1):
            dieKamera.drawLine(x + 0.5, y + 0.5, x + 0.5, y + 1);
            break;
          case(2):
            dieKamera.drawLine(x + 0.5, y + 0.5, x + 1, y + 0.5);
            break;
          case(3):
            dieKamera.drawLine(x + 0.5, y + 0.5, x + 0.5, y);
            break;
          case(4):
            dieKamera.drawLine(x + 0.5, y + 0.5, x, y + 0.5);
            break;
          default:
            opt = 1;
            break;
        }
        break;
    }
    
    dieKamera.drawCircle(x, y, 0.1f);
    fill(255, 0, 0);
    dieKamera.drawText(spezialBezeichnungen[id] + " (" + opt + ")", x - 0.5f, y + 0.3f, 0.2f);
  }
}
