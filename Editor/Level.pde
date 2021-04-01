public class Level {
  // ATTRIBUTE //
  int musik;
  int hintergrund;
  
  public Werkzeug dasWerkzeug;
  private int auswahlWerkzeug;
  
  // Platzierte Dinge
  public ArrayList<Float> liste_Strecken;
  public ArrayList<Spezial> liste_Spezial;
  private Sonic derSonic;
  private Gegner derGegner;
  private Strecken dieStrecke;
  private Objekte dasObjekt;
  
  
  // KONSTRUKTOR //
  public Level() {
    liste_Strecken = new ArrayList<Float>();
    liste_Spezial = new ArrayList<Spezial>();
    derSonic = new Sonic();
    derGegner = new Gegner();
    dieStrecke = new Strecken();
    dasObjekt = new Objekte();
    
    
    auswahlWerkzeug = 0;
    werkzeugWechseln(0);
  }
  
  
  // METHODEN //
  public void zeichnen() {
    // Spezial-Liste zeichnen
    stroke(0, 0, 0, 155);
    fill(75, 75, 255, 155);
    for (int i = 0; i < liste_Spezial.size(); i++) {
      liste_Spezial.get(i).zeichnen();
    }
    
    // Strecken-Liste zeichnen
    stroke(75, 75, 255);
    strokeWeight(6);
    for (int i = 0; i < liste_Strecken.size(); i += 4) {
      dieKamera.drawLine(liste_Strecken.get(i), liste_Strecken.get(i + 1), liste_Strecken.get(i + 2), liste_Strecken.get(i + 3));
    }
    
    
    // Werkzeug
    dasWerkzeug.zeichnen();
  }
  
  
  public void cursorMoved(float deltaX, float deltaY) {
    dasWerkzeug.cursorMoved(deltaX, deltaY);
  }
  public void cursorPressed(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    dasWerkzeug.cursorPressed(button);
  }
  public void cursorReleased(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    dasWerkzeug.cursorReleased(button);
  }
  public void cursorClicked(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    dasWerkzeug.cursorClicked(button);
  }

  public void tasteGedrueckt(char k) {
    dasWerkzeug.tasteGedrueckt(k);
    
    if (k == 'A')
      werkzeugWechseln(-1);
    else if (k == 'D')
      werkzeugWechseln(1);
  }
  public void tasteLosgelassen(char k) {
    dasWerkzeug.tasteLosgelassen(k);
  }
  
  
  private void werkzeugWechseln(int richtung) {
    auswahlWerkzeug += richtung;
    if (auswahlWerkzeug >= 4)
      auswahlWerkzeug = 0;
    else if (auswahlWerkzeug < 0)
      auswahlWerkzeug = 3;
    
    println("Werkzeug gewechselt: " + auswahlWerkzeug);
    
    
    switch (auswahlWerkzeug) {
      case(0):
        dasWerkzeug = new WZ_Objekte();
        break;
      case(1):
        dasWerkzeug = new WZ_Strecken();
        break;
      case(2):
        dasWerkzeug = new WZ_Gegner();
        break;
      case(3):
        dasWerkzeug = new WZ_Spezial();
        break;
    }
  }
  
  
  
  private void inport(){
    
  }
  
  private void export(){
    
  }
}
