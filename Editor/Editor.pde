
// Wichtig: in Processing können alle Klassen auf diese Attribute zugreifen
// Die wichtigsten Sachen passieren in der Level-Klasse

public Kamera dieKamera;
public Level dasLevel;
public Eingabe dieEingabe;



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


public boolean istCursorBeiPunkt(float px, float py) {
  float dis = sqrt((px - dieEingabe.cursorX) * (px - dieEingabe.cursorX) + (py - dieEingabe.cursorY) * (py - dieEingabe.cursorY)) * dieKamera.pixelProEinheit;;
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
