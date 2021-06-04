package ui;

import javafx.scene.canvas.GraphicsContext;

public abstract class Node {
	// ATTRIBUTE //
	protected double x, y, w, h;
	
	
	// KONSTRUKTOR //
	public Node(double px, double py, double pw, double ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
	}
	
	
	// METHODEN //
	public abstract void draw(GraphicsContext gc);
}
