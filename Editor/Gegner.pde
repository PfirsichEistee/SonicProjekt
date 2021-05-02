class Gegner{  
  int id;
  float x, y;
  int richtung;
  
  // KONSTRUKTOR //
  public Gegner(int pID, float px, float py, int prichtung) {
    id = pID;
    x = px;
    y = py;
    richtung = prichtung;
  }
  
  // METHODEN //
  public void zeichnen() {
    stroke(0, 150, 0);
    fill(0, 150, 0);
    
    dieKamera.drawCircle(x, y, 0.35f);
    if(richtung == 0){
      dieKamera.drawLine(x,y,x-0.7,y);
    }
    else if(richtung == 1){
      dieKamera.drawLine(x,y,x+0.7,y);
    }
    
    dieKamera.drawText(gegnerBezeichnungen[id], x - 0.5f, y + 0.55f, 0.3f);
  }
}
