class Kamera{
  // ATTRIBUTE
  public float x;
  public float y;
  public float pixelProEinheit;
  
  
  // KONSTRUKTOR
  public Kamera(){
    pixelProEinheit = 50;
    x = -(width / pixelProEinheit) / 2;
    y = (height / pixelProEinheit) / 2;
  }
  
  
  // METHODEN
  // Umwandlungen
  public float einheitZuPixelX(float pEinheit){
    float loesung = pEinheit * pixelProEinheit;
    loesung = loesung - x * pixelProEinheit;
    return loesung;
  }
  
   public float einheitZuPixelY(float pEinheit){
    float loesung = -pEinheit * pixelProEinheit;
    loesung = loesung + y * pixelProEinheit;
    return loesung;
  }
  
  public float pixelZuEinheitX(float pixel) {
    return (pixel / pixelProEinheit) + x;
  }
  
  public float pixelZuEinheitY(float pixel) {
    return (-pixel / pixelProEinheit) + y;
  }
  
  // Zeichnen
  public void drawRect(float cx,float cy,float cw,float ch){
    rect(einheitZuPixelX(cx), einheitZuPixelY(cy), cw * pixelProEinheit, ch * pixelProEinheit);
  }
  
  public void drawLine(float x1, float y1, float x2, float y2) {
    line(einheitZuPixelX(x1), einheitZuPixelY(y1), einheitZuPixelX(x2), einheitZuPixelY(y2));
  }
  
  public void drawText(String txt, float px, float py, float size) {
    textSize(size * pixelProEinheit);
    text(txt, einheitZuPixelX(px), einheitZuPixelY(py));
  }
  
  public void drawCircle(float px, float py, float pr) {
    ellipse(einheitZuPixelX(px), einheitZuPixelY(py), pr * 2 * pixelProEinheit, pr * 2 * pixelProEinheit);
  }
  
  // Sonstige
  public void zeichneHintergrund() {
    // GITTER ZEICHNEN
    
    stroke(0);
    strokeWeight(0.5f);
    
    // Vertikale Linien zeichnen
    for (int phX = floor(x); phX <= (floor(x) + ceil(width / pixelProEinheit)); phX++) {
      if (phX == 0)
        continue;
      
      drawLine(phX, y, phX, y - (height / pixelProEinheit));
    }
    
    // Horizontale Linien zeichnen
    for (int phY = floor(y); phY >= (floor(y) - ceil(height / pixelProEinheit)); phY--) {
      if (phY == 0)
        continue;
      
      drawLine(x, phY, x + (width / pixelProEinheit), phY);
    }
    
    // Koordinatenachsen zeichnen
    stroke(255, 0, 0);
    strokeWeight(2);
    drawLine(x, 0, x + (width / pixelProEinheit), 0);
    drawLine(0, y, 0, y - (height / pixelProEinheit));
  }
  
  
  public void move(float dx, float dy){
    if (dieEingabe.istMausButtonGedrueckt(2) == true) {
      x -= dx;
      y -= dy;
      
      dieEingabe.cursorX = pixelZuEinheitX(mouseX);
      dieEingabe.cursorY = pixelZuEinheitY(mouseY);
    }
  }
  
  
  public void zoom(float delta) {
    pixelProEinheit -= delta * 3;
    
    if (pixelProEinheit < 10) pixelProEinheit = 10;
    else if (pixelProEinheit > 160) pixelProEinheit = 160;
  }
  
  
  // Getter & setter
  public float getWidth() {
    return (width / pixelProEinheit);
  }
  public float getHeight() {
    return (height / pixelProEinheit);
  }
}
