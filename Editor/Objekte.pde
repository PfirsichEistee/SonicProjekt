class Objekte{
  String text;
  int id;
  float x;
  float y;
  
  // KONSTRUKTOR //
  public Objekte(int pID, String pText, float px, float py) {
    id = pID;
    text = pText;
    x = px;
    y = py;
    
    //x und y werden gerundet
    
    x = x - x%1;
    y = y - y%1;

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
