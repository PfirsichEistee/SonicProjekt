class Wasserfall {
  float x, y;
  int laenge, hoehe;
  
  // KONSTRUKTOR //
  public Wasserfall(float px, float py, int pLaenge, int pHoehe) {
    x = px;
    y = py;
    laenge = pLaenge;
    hoehe = pHoehe;
  }
  
  // METHODEN //
  public void zeichnen() {
    stroke(0, 0, 255);
    strokeWeight(3);
    
    fill(255, 100, 255, 155);
    
    
    dieKamera.drawRect(x, y, laenge * 2, -hoehe * 2);
  } 
}
