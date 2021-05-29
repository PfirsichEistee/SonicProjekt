public class Platform {
  // ATTRIBUTE //
  public float x, y;
  public int id;
  public float targetX, targetY;
  
  
  // KONSTRUKTOR //
  public Platform(int pID, float px, float py, float deltaX, float deltaY) {
    x = px;
    y = py;
    id = pID;
    targetX = px + deltaX;
    targetY = py + deltaY;
  }
  
  
  // METHODEN //
  public void zeichnen() {
    stroke(0);
    strokeWeight(2);
    fill(255, 100, 255);
    
    float ph = sin(millis() * 0.005f) * 0.5f + 0.5f;
    
    float posX = (targetX - x) * ph + x;
    float posY = (targetY - y) * ph + y;
    
    float pw = (id == 0 ? 2 : 1.5f);
    
    dieKamera.drawRect(posX, posY - 0.1, pw, 0.1f);
    
    
    float mx = x;
    float my = y;
    float txth = 0.3f;
    fill(0);
    dieKamera.drawText("ID: " + id, mx, my, txth);
    dieKamera.drawText("ID: " + id, mx - 0.02, my - 0.02, txth);
    dieKamera.drawText("ID: " + id, mx + 0.02, my - 0.02, txth);
    dieKamera.drawText("ID: " + id, mx - 0.02, my + 0.02, txth);
    dieKamera.drawText("ID: " + id, mx + 0.02, my + 0.02, txth);
    fill(255);
    dieKamera.drawText("ID: " + id, mx, my, txth);
  }
}
