package app;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Eingabe {
	// ATTRIBUTE //
	private static ArrayList<String> pressedKeys = new ArrayList<String>();
	public static float mouseX = 0f, mouseY = 0f;
	
	
	// KONSTRUKTOR //
	public static void init(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				onKeyDown(getText(event));
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				onKeyUp(getText(event));
			}
		});
		
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseX = (float)event.getX();
				mouseY = (float)event.getY();
			}
		});
	}
	
	
	// METHODEN //
	public static boolean isKeyDown(String key) {
		key = key.toUpperCase();
		
		for (int i = 0; i < pressedKeys.size(); i++) {
			if (pressedKeys.get(i).equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void onKeyDown(String key) {
		key = key.toUpperCase();
		
		for (int i = (pressedKeys.size() - 1); i >= 0; i--) {
			if (pressedKeys.get(i).equals(key)) {
				return;
			}
		}
		
		pressedKeys.add(key);
	}
	
	public static void onKeyUp(String key) {
		key = key.toUpperCase();
		
		for (int i = (pressedKeys.size() - 1); i >= 0; i--) {
			if (pressedKeys.get(i).equals(key)) {
				pressedKeys.remove(i);
				return;
			}
		}
	}
	
	
	private static String getText(KeyEvent e) {
		String txt = e.getText().toUpperCase();
		if (txt.length() == 0) {
			if (e.getCode() == KeyCode.LEFT)
				txt = "LEFT";
			else if (e.getCode() == KeyCode.RIGHT)
				txt = "RIGHT";
			else if (e.getCode() == KeyCode.UP)
				txt = "UP";
			else if (e.getCode() == KeyCode.DOWN)
				txt = "DOWN";
			else if (e.getCode() == KeyCode.SHIFT)
				txt = "SHIFT";
		}
		
		return txt;
	}
}
