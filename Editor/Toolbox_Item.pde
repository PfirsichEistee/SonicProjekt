public class Toolbox_Item {
  // ATTRIBUTE //
  public String titel;
  public int anzahl;
  public int min, max;
  private String[] anzahlText;
  
  
  // KONSTRUKTOR //
  public Toolbox_Item(String pTitel, int pAnzahl, int pMin, int pMax) {
    titel = pTitel;
    anzahl = pAnzahl;
    min = pMin;
    max = pMax;
    anzahlText = new String[max - min + 1];
    
    for (int i = 0; i <= (max - min); i++) {
      anzahlText[i] = "" + (i + min);
    }
  }
  
  
  // METHODEN //
  public String getAnzahlText() {
    return anzahlText[anzahl - min];
  }
  
  public void setAnzahlText(int pAnzahl, String pStr) {
    anzahlText[pAnzahl - min] = pStr;
  }
}
