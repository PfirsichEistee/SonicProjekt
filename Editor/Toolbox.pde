public class Toolbox {
  // ATTRIBUTE //
  private String titel;
  private ArrayList<Toolbox_Item> liste;
  private int auswahl;
  
  
  // KONSTRUKTOR //
  public Toolbox(String pTitel) {
    titel = pTitel;
    liste = new ArrayList<Toolbox_Item>();
    auswahl = 0;
  }
  
  
  // METHODEN //
  public void zeichnen(int x, int y) {
    translate(x, y);
    
    strokeWeight(1);
    stroke(0);
    fill(0, 0, 0, 175);
    rect(0, 0, 300, 20 * 10);
    fill(0, 0, 0, 255);
    rect(0, 0, 300, 20);
    
    
    stroke(0, 0, 0, 0);
    fill(255, 0, 0, 100);
    rect(0, 20 * (auswahl + 1), 300, 20);
    
    
    textSize(20);
    fill(255);
    
    textAlign(LEFT);
    text(titel, 0, 20);
    
    
    for (int i = 0; i < liste.size(); i++) {
      textAlign(LEFT);
      text(liste.get(i).titel, 0, 20 * (i + 2));
      textAlign(RIGHT);
      text(liste.get(i).getAnzahlText(), 300, 20 * (i + 2));
    }
    textAlign(LEFT);
    
    
    
    translate(-x, -y);
  }
  
  
  public void addItem(Toolbox_Item pItem) {
    liste.add(pItem);
  }
  
  public void hoch() {
    auswahl--;
    if (auswahl < 0) auswahl = 0;
  }
  
  public void runter() {
    auswahl++;
    if (auswahl >= liste.size()) auswahl = liste.size() - 1;
  }
  
  public void links() {
    Toolbox_Item item = liste.get(auswahl);
    item.anzahl--;
    if (item.anzahl < item.min) item.anzahl = item.min;
  }
  
  public void rechts() {
    Toolbox_Item item = liste.get(auswahl);
    item.anzahl++;
    if (item.anzahl > item.max) item.anzahl = item.max;
  }
  
  public int getAnzahlVonItem(int id) {
    return liste.get(id).anzahl;
  }
  
  public int getAnzahlVonItem(String pTitel) {
    for (Toolbox_Item item : liste) {
      if (item.titel.equals(pTitel)) {
        return item.anzahl;
      }
    }
    
    return 0;
  }
}
