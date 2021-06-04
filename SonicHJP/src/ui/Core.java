package ui;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Core {
	// ATTRIBUTE //
	private static Core core;
	
	private GraphicsContext gc;
	private double width, height;
	
	private ArrayList<Node> nodeList;
	
	
	// KONSTRUKTOR //
	public Core(GraphicsContext pGC, double pWidth, double pHeight) {
		gc = pGC;
		width = pWidth;
		height = pHeight;
		nodeList = new ArrayList<Node>();
	}
	
	
	// METHODEN //
	public static void init(GraphicsContext pGC, double pWidth, double pHeight) {
		core = new Core(pGC, pWidth, pHeight);
	}
	public static double getWidth() {
		return core.width;
	}
	public static double getHeight() {
		return core.height;
	}
	
	public static void draw() {
		Font ph = core.gc.getFont();
		
		for (int i = 0; i < core.nodeList.size(); i++) {
			core.nodeList.get(i).draw(core.gc);
		}
		
		core.gc.setFont(ph);
		core.gc.setTextAlign(TextAlignment.LEFT);
	}
	
	public static void append(Node pNode) {
		core.nodeList.add(pNode);
	}
}
