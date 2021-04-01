
// Wichtig: in Processing können alle Klassen auf diese Attribute zugreifen
// Die wichtigsten Sachen passieren in der Level-Klasse

public Kamera dieKamera;
public Level dasLevel;
public Eingabe dieEingabe;


private boolean zeigeHilfe = false;



void setup(){
  size(1000, 800);
  dieEingabe = new Eingabe();
  dieKamera = new Kamera();
  dasLevel = new Level();
}


void draw(){
  background(255);
  
  // Sonstiges
  dieEingabe.updateCursor();
  
  
  // BEWEGEN
  //dieKamera.move();
  
  
  // ZEICHNEN (immer am Ende zeichnen!)
  dieKamera.zeichneHintergrund();
  
  dasLevel.zeichnen();
  
  // Editor infos zeichnen
  textSize(16);
  fill(0);
  stroke(0);
  text("Druecke 'H' fuer Hilfe", 20, 36);
  
  if (zeigeHilfe == true) {
    String txt = "Kamera Position: " + (floor(dieKamera.x * 10f) / 10f) + " | " + (floor(dieKamera.y * 10f) / 10f) + "    (Bildschirm-Ecke oben-links)\n";
    txt += "Maus Position: " + (floor(dieEingabe.cursorX * 10f) / 10f) + " | " + (floor(dieEingabe.cursorY * 10f) / 10f) + "\n";
    txt += "Werkzeug mit A/D wechseln\n";
    txt += "Kamera mit mittlerer Maustaste bewegen\n";
    txt += "Mit Mausrad zoomen\n";
    txt += "Map anzeigen mit 'M'\n";
    txt += "\nSpeichern mit ' 9 '\nLaden mit ' 0 '\n";
    text(txt, 20, 56);
  }
}



public void cursorMoved(float deltaX, float deltaY) {
  dieKamera.move(deltaX, deltaY);
  dasLevel.cursorMoved(deltaX, deltaY);
}
public void cursorPressed(int button) {
  // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
  dasLevel.cursorPressed(button);
}
public void cursorReleased(int button) {
  // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
  dasLevel.cursorReleased(button);
}
public void cursorClicked(int button) {
  // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
  dasLevel.cursorClicked(button);
}

public void tasteGedrueckt(char k) {
  dasLevel.tasteGedrueckt(k);
  
  if (k == 'H') {
    zeigeHilfe = !zeigeHilfe;
  }
}
public void tasteLosgelassen(char k) {
  dasLevel.tasteLosgelassen(k);
}


public boolean istCursorNahePunkt(float px, float py) {
  return istPktANahePktB(dieEingabe.cursorX, dieEingabe.cursorY, px, py);
}
public boolean istPktANahePktB(float xa, float ya, float xb, float yb) {
  float dis = sqrt((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya)) * dieKamera.pixelProEinheit;;
  if (dis < 30)
    return true;
  
  return false;
}


// Default Methoden
void keyPressed() {
  dieEingabe.keyPressed();
}
void keyReleased() {
  dieEingabe.keyReleased();
}
void mousePressed() {
  dieEingabe.mousePressed();
}
void mouseReleased() {
  dieEingabe.mouseReleased();
}
void mouseWheel(MouseEvent event) {
  dieKamera.zoom(event.getCount());
}
