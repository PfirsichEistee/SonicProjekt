class Objekt{
  String text;
  int id;
  float x;
  float y;
  
  // KONSTRUKTOR //
  public Objekt(int pID, String pText, float px, float py) {
    id = pID;
    text = pText;
    x = px;
    y = py;

  }
  
  // METHODEN //
  public void zeichnen() {
    stroke(0, 0, 255);
    switch (id) {
      case (0):
        fill(100, 100, 255, 155);
        break;
      case (1):
        fill(120, 100, 225, 155);
        break;
      case (2):
        fill(140, 100, 205, 155);
        break;
      case (3):
        fill(160, 100, 185, 155);
        break;
    }
    dieKamera.drawCircle(x, y, 0.1f);
    fill(0, 0, 255);
    dieKamera.drawText(text, x - 0.5f, y + 0.3f, 0.2f);
  } 
}
