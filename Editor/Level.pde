class Level{
  int musik;
  int hintergrund;
  
  Sonic derSonic;
  Gegner derGegner;
  Strecken dieStrecke;
  Objekte dasObjekt;
  Werkzeug dasWerkzeug;
  
  public Level(){
    derSonic = new Sonic();
    derGegner = new Gegner();
    dieStrecke = new Strecken();
    dasObjekt = new Objekte();
    dasWerkzeug = new Werkzeug();
  }
  
  private void inport(){
    
  }
  
  private void export(){
    
  }
}
