class Kamera{
  float x;
  float y;
  float px;
  
  public Kamera(){
    x = 0;
    y = 0;
    px = 50;
  }
  
  public float UmwandlungX(float pEinheit){
    float loesung = pEinheit * px;
    loesung = loesung - x * px;
    return loesung;
  }
  
   public float UmwandlungY(float pEinheit){
    float loesung = pEinheit * px;
    loesung = loesung + y * px;
    return loesung;
  }
  
  public void Cam(float cx,float cy,float cw,float ch){
    rect(UmwandlungX(cx) ,UmwandlungY(cy), cw * px, ch * px);
  }
  
  public void move(){
    if(keyPressed == true){
      if(x > 0 && key == 'a'){
        x = x - 10;
      }
      else if(y < 0 && key == 's'){
        y = y + 10;
      }
      else if(key == 'd'){
        x = x + 10;
      }
      else if(key == 'w'){
        y = y - 10;
      }
    }
  }
}
