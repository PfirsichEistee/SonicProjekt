package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Rect extends Node {
	// ATTRIBUTE //
	private Color color;
	
	
	// KONSTRUKTOR //
	public Rect(double px, double py, double pw, double ph, Color pColor) {
		super(px, py, pw, ph);
		color = pColor;
	}
	
	
	// METHODEN //
	public void setColor(Color pColor) {
		color = pColor;
	}


	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x, y, w, h);
	}
}
