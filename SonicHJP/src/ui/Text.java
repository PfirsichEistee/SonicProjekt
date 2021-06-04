package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Text extends Node {
	// ATTRIBUTE //
	private static final int BORDER = 2;
	
	private Font font;
	private TextAlignment align;
	private Color color;
	private String txt;
	
	
	// KONSTRUKTOR //
	public Text(String pTxt, double px, double py, double pw, double ph, double fontSize) {
		super(px, py, pw, ph);
		txt = pTxt;
		font = Font.loadFont("file:files/fonts/sonic_advance_2.ttf", fontSize);
		align = TextAlignment.LEFT;
		color = Color.WHITE;
	}
	
	
	// METHODEN //
	public void setText(String pTxt) {
		txt = pTxt;
	}
	public void setColor(Color pColor) {
		color = pColor;
	}
	public void setTextAlignment(TextAlignment pAlign) {
		align = pAlign;
	}


	@Override
	public void draw(GraphicsContext gc) {
		gc.setFont(font);
		gc.setTextAlign(align);
		
		gc.setFill(Color.BLACK);
		gc.fillText(txt, x - BORDER, y - BORDER, w);
		gc.fillText(txt, x + BORDER, y - BORDER, w);
		gc.fillText(txt, x - BORDER, y + BORDER, w);
		gc.fillText(txt, x + BORDER, y + BORDER, w);
		
		gc.setFill(color);
		gc.fillText(txt, x, y, w);
	}
}
