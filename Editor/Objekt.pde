class Objekt{
  int id;
  float x;
  float y;
  
  // KONSTRUKTOR //
  public Objekt(int pID, float px, float py) {
    id = pID;
    x = px;
    y = py;

  }
  
  // METHODEN //
  public void zeichnen() {
    stroke(0, 0, 255);
    strokeWeight(1);
    
    fill(255, 0, 0, 155);
    
    
    if (id == 0) // Ring
      dieKamera.drawCircle(x, y, 0.35f);
    else if (id >= 2) // Items
      dieKamera.drawRect(x, y, 1, -1);
    else
      dieKamera.drawCircle(x, y, 0.1f);
    
    fill(0, 0, 255);
    dieKamera.drawText(objektBezeichnungen[id], x - 0.5f, y + 0.3f, 0.2f);
  } 
}
