public class Level {
  // ATTRIBUTE //
  int musik;
  int hintergrund;
  
  private Werkzeug dasWerkzeug;
  
  // Platzierte Dinge
  public ArrayList<Spezial> liste_Spezial;
  private Sonic derSonic;
  private Gegner derGegner;
  private Strecken dieStrecke;
  private Objekte dasObjekt;
  
  
  // KONSTRUKTOR //
  public Level() {
    liste_Spezial = new ArrayList<Spezial>();
    derSonic = new Sonic();
    derGegner = new Gegner();
    dieStrecke = new Strecken();
    dasObjekt = new Objekte();
    dasWerkzeug = new WZ_Spezial();
  }
  
  
  // METHODEN //
  public void zeichnen() {
    // Spezial-Liste zeichnen
    stroke(0, 0, 0, 155);
    fill(75, 75, 255, 155);
    for (int i = 0; i < liste_Spezial.size(); i++) {
      liste_Spezial.get(i).zeichnen();
    }
    
    
    // Werkzeug
    dasWerkzeug.zeichnen();
  }
  
  
  public void cursorMoved(float deltaX, float deltaY) {
    
  }
  public void cursorPressed(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    
  }
  public void cursorReleased(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    
  }
  public void cursorClicked(int button) {
    // button == 0 -> LINKS; button == 1 -> RECHTS; button == 2 -> MITTE
    dasWerkzeug.cursorClicked(button);
  }
  
  private void inport(){
    
  }
  
  private void export(){
    
  }
}
