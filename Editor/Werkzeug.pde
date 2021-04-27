public abstract class Werkzeug{
  // ATTRIBUTE //
  protected Toolbox dieToolbox;
  
  
  // KONSTRUKTOR //
  // ...
  
  
  // METHODEN //
  public abstract void zeichnen();
  public abstract void cursorMoved(float deltaX, float deltaY);
  public abstract void cursorPressed(int button);
  public abstract void cursorReleased(int button);
  public abstract void cursorClicked(int button);
  public abstract void tasteGedrueckt(char k);
  public abstract void tasteLosgelassen(char k);
  
  
  protected void toolboxKeyPressed(char k) {
    switch (k) {
      case ('J'): // LEFT
        dieToolbox.links();
        break;
      case ('L'): // RIGHT
        dieToolbox.rechts();
        break;
      case ('I'): // UP
        dieToolbox.hoch();
        break;
      case ('K'): // DOWN
        dieToolbox.runter();
        break;
    }
  }
  
  
  public Toolbox getToolbox() {
    return dieToolbox;
  }
}
