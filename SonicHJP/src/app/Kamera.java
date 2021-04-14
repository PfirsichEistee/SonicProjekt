package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Kamera {
	// ATTRIBUTE //
	private static GraphicsContext gc;
	
	private static float screenWidth;
	private static float screenHeight;
	
	private float x, y;
	private float pixelProEinheit;
	
	
	// KONSTRUKTOR //
	public Kamera(float px, float py, float pPixelProEinheit) {
		x = px;
		y = py;
		pixelProEinheit = pPixelProEinheit;
	}
	
	
	// METHODEN //
	public static void init(GraphicsContext pGC, float pScreenWidth, float pScreenHeight) {
		gc = pGC;
		screenWidth = pScreenWidth;
		screenHeight = pScreenHeight;
	}
	
	public float pixelZuEinheitX(float pixel) {
		return ((pixel - (screenWidth / 2)) / pixelProEinheit) + x;
	}
	public float pixelZuEinheitY(float pixel) {
		return (-(pixel - (screenHeight / 2)) / pixelProEinheit) + y;
	}
	
	public float einheitZuPixelX(float einheit) {
		float loesung = einheit * pixelProEinheit;
		loesung = loesung - x * pixelProEinheit;
		loesung = loesung + (screenWidth / 2);
		return loesung;
	}
	public float einheitZuPixelY(float einheit) {
		float loesung = einheit * pixelProEinheit;
		loesung = -loesung + y * pixelProEinheit;
		loesung = loesung + (screenHeight / 2);
		return loesung;
	}
	
	
	// Zeichen-Methoden
	public void drawImage(Image pImage, float px, float py, float pw, float ph, float pRotation) {
		gc.save();
		
		pw *= pixelProEinheit;
		ph *= pixelProEinheit;
		
		gc.translate(einheitZuPixelX(px) + (pw / 2), einheitZuPixelY(py) + (ph / 2));
		gc.rotate(pRotation * (180.0 / Math.PI));
		gc.drawImage(pImage, -pw / 2, -ph / 2, pw, ph);
		
		gc.restore();
	}
	
	public void drawImageSection(Image pImage, int imgX, int imgY, int imgW, int imgH, float px, float py, float pw, float ph, float pRotation) {
		
	}
	
	public void drawLine(float x1, float y1, float x2, float y2) {
		gc.strokeLine(einheitZuPixelX(x1), einheitZuPixelY(y1), einheitZuPixelX(x2), einheitZuPixelY(y2));
	}
	
	public void drawText(String pText, float px, float py) {
		// px|py == Ecke unten links
		gc.fillText(pText, einheitZuPixelX(px), einheitZuPixelY(py));
	}
	
	public void drawRect(float px, float py, float pw, float ph) {
		gc.fillRect(einheitZuPixelX(px), einheitZuPixelY(py), pw * pixelProEinheit, -ph * pixelProEinheit);
	}
	
	public void drawOval(float px, float py, float pw, float ph) {
		gc.fillOval(einheitZuPixelX(px), einheitZuPixelY(py + ph), pw * pixelProEinheit, ph * pixelProEinheit);
	}
	
	
	// Getter & Setter
	public void setFarbe(Color pFarbe) {
		gc.setStroke(pFarbe);
		gc.setFill(pFarbe);
	}
	
	public void setLineWidth(float pWidth) {
		gc.setLineWidth(pWidth * pixelProEinheit);
	}
	
	public float getX() {
		return x;
	}
	public void setX(float px) {
		x = px;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float py) {
		y = py;
	}
	
	public float getPixelProEinheit() {
		return pixelProEinheit;
	}
	
	public float getWidth() {
		return screenWidth / pixelProEinheit;
	}
	public float getHeight() {
		return screenHeight / pixelProEinheit;
	}
}
