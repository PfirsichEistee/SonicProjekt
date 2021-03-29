
// Wichtig: in Processing können alle Klassen auf diese Attribute zugreifen

public Kamera dieKamera;
public Level dasLevel;
public Eingabe dieEingabe;



void setup(){
  size(1000, 800);
  dieKamera = new Kamera();
  dasLevel = new Level();
  dieEingabe = new Eingabe();
}


void draw(){
  background(255);
  dieKamera.zeichneHintergrund();
  
  
  stroke(0, 255, 75);
  dieKamera.drawLine(0.5, -1.22, 5, 2);
  
  
  dieKamera.move();
}


void keyPressed() {
  dieEingabe.keyPressed();
}


void keyReleased() {
  dieEingabe.keyReleased();
}
