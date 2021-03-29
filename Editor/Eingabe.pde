/* Wichtig in dieser Klasse:

Attribute: cursorX, cursorY    -> gibt die Mausposition in Einheiten

Methoden: istTasteGedrueckt    -> wenn eine Taste gerade gedrückt gehalten wird, gibt diese Methode "true" zurück

*/


public class Eingabe {
  // ATTRIBUTE //
  private ArrayList<Character> gedrueckteTasten;
  
  public float cursorX, cursorY; // dasselbe wie mouseX/Y, aber mit Einheit anstelle von Pixel
  
  
  // KONSTRUKTOR //
  public Eingabe() {
    gedrueckteTasten = new ArrayList<Character>();
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
}
