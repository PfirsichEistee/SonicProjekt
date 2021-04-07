class Gegner{  
  int id;
  String text;
  float x, y;
  int richtung;
  
  // KONSTRUKTOR //
  public Gegner(int pID, String pText, float px, float py, int prichtung) {
    id = pID;
    text = pText;
    x = px;
    y = py;
    richtung = prichtung;
  }
  
  // METHODEN //
  public void zeichnen() {
    stroke(0, 255, 0);
    switch (id) {
      case (0):
        fill(100, 255, 100, 155);
        break;
      case (1):
        fill(120, 255, 100, 155);
        break;
      case (2):
        fill(140, 255, 100, 155);
        break;
    }
    
    dieKamera.drawCircle(x, y, 0.1f);
    if(richtung == 0){
      dieKamera.drawLine(x,y,x-0.5,y);
    }
    else if(richtung == 1){
      dieKamera.drawLine(x,y,x+0.5,y);
    }
    fill(0, 255, 0);
    dieKamera.drawText(text, x - 0.5f, y + 0.3f, 0.2f);
  }
}
