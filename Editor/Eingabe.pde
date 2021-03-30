/* Wichtig in dieser Klasse:

Attribute: cursorX, cursorY    -> gibt die Mausposition in Einheiten

Methoden: istTasteGedrueckt    -> wenn eine Taste gerade gedrückt gehalten wird, gibt diese Methode "true" zurück

*/


public class Eingabe {
  // ATTRIBUTE //
  private ArrayList<Character> gedrueckteTasten;
  private boolean[] gedrueckteMausButtons;
  private int[] mausButtonClickZeit;
  
  public float cursorX, cursorY; // dasselbe wie mouseX/Y, aber mit Einheit anstelle von Pixel
  
  
  // KONSTRUKTOR //
  public Eingabe() {
    gedrueckteTasten = new ArrayList<Character>();
    
    gedrueckteMausButtons = new boolean[3];
    gedrueckteMausButtons[0] = false; // links
    gedrueckteMausButtons[1] = false; // rechts
    gedrueckteMausButtons[2] = false; // mitte
    
    mausButtonClickZeit = new int[3];
    mausButtonClickZeit[0] = 0;
    mausButtonClickZeit[1] = 0;
    mausButtonClickZeit[2] = 0;
    
    cursorX = 0;
    cursorY = 0;
  }
  
  
  // METHODEN //
  public boolean istTasteGedrueckt(Character k) {
    k = Character.toUpperCase(k);
    
    for (int i = 0; i < gedrueckteTasten.size(); i++) {
      if (gedrueckteTasten.get(i).equals(k)) {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean istMausButtonGedrueckt(int button) {
    return gedrueckteMausButtons[button];
  }
  
  
  // Die unteren Methoden sind nur fuer diese Klasse wichtig
  public void keyPressed() {
    Character k = key;
    k = Character.toUpperCase(k);
    
    for (int i = 0; i < gedrueckteTasten.size(); i++) {
      if (gedrueckteTasten.get(i).equals(k)) {
        return;
      }
    }
    
    gedrueckteTasten.add(k);
  }
  
  public void keyReleased() {
    Character k = key;
    k = Character.toUpperCase(k);
    
    for (int i = 0; i < gedrueckteTasten.size(); i++) {
      if (gedrueckteTasten.get(i).equals(k)) {
        gedrueckteTasten.remove(i);
        return;
      }
    }
  }
  
  public void mousePressed() {
    if (mouseButton == LEFT) {
      gedrueckteMausButtons[0] = true;
      mausButtonClickZeit[0] = millis();
    } else if (mouseButton == RIGHT) {
      gedrueckteMausButtons[1] = true;
      mausButtonClickZeit[1] = millis();
    } else {
      gedrueckteMausButtons[2] = true;
      mausButtonClickZeit[2] = millis();
    }
  }
  
  public void mouseReleased() {
    if (mouseButton == LEFT) {
      gedrueckteMausButtons[0] = false;
      if ((millis() - mausButtonClickZeit[0]) < 100) cursorClicked(0);
    } else if (mouseButton == RIGHT) {
      gedrueckteMausButtons[1] = false;
      if ((millis() - mausButtonClickZeit[1]) < 100) cursorClicked(1);
    } else {
      gedrueckteMausButtons[2] = false;
      if ((millis() - mausButtonClickZeit[2]) < 100) cursorClicked(2);
    }
  }
  
  public void updateCursor() {
    float newX = dieKamera.pixelZuEinheitX(mouseX);
    float newY = dieKamera.pixelZuEinheitY(mouseY);
    
    if (floor(newX * 100) != floor(cursorX * 100) || floor(newY * 100) != floor(cursorY * 100)) {
      float dx = (newX - cursorX);
      float dy = (newY - cursorY);
      cursorX = newX;
      cursorY = newY;
      cursorMoved(dx, dy);
    }
  }
}
