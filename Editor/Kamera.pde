class Kamera{
  // ATTRIBUTE
  public float x;
  public float y;
  public float pixelProEinheit;
  
  
  // KONSTRUKTOR
  public Kamera(){
    x = 0;
    y = 0;
    pixelProEinheit = 50;
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
  
  // Sonstige
  public void zeichneHintergrund() {
    // GITTER ZEICHNEN
    
    stroke(0);
    strokeWeight(1);
    
    // Vertikale Linien zeichnen
    for (int phX = floor(x); phX <= (floor(x) + ceil(width / pixelProEinheit)); phX++) {
      if (phX == 0)
        continue;
      
      drawLine(phX, y, phX, y - (height / pixelProEinheit));
    }
    
    // Horizontale Linien zeichnen
    for (int phY = floor(y); phY >= -(floor(y) + ceil(height / pixelProEinheit)); phY--) {
      if (phY == 0)
        continue;
      
      drawLine(x, phY, x + (width / pixelProEinheit), phY);
    }
    
    // Koordinatenachsen zeichnen
    stroke(255, 0, 0);
    strokeWeight(2);
    drawLine(x, 0, x + (width / pixelProEinheit), 0);
    drawLine(0, y, 0, y - (height / pixelProEinheit));
    
    
    // INFOS ZEICHNEN
    fill(0);
    String txt = "Kamera Position: " + (floor(x * 10f) / 10f) + " | " + (floor(y * 10f) / 10f) + "    (Bildschirm-Ecke oben-links)\n";
    txt += "Maus Position: " + (floor(pixelZuEinheitX(mouseX) * 10f) / 10f) + " | " + (floor(pixelZuEinheitY(mouseY) * 10f) / 10f) + "\n";
    text(txt, einheitZuPixelX(x + 0.1f), einheitZuPixelY(y - 0.25f));
  }
  
  public void move(){
    if(dieEingabe.istTasteGedrueckt('a') == true){
      x = x - 0.1f;
    }
    if(dieEingabe.istTasteGedrueckt('s') == true){
      y = y - 0.1f;
    }
    if(dieEingabe.istTasteGedrueckt('d') == true){
      x = x + 0.1f;
    }
    if(dieEingabe.istTasteGedrueckt('w') == true){
      y = y + 0.1f;
    }
  }
}
