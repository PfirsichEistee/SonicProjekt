public abstract class Werkzeug{
  // ATTRIBUTE //
  // ...
  
  
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
}
