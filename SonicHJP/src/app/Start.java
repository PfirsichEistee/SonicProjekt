package app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Start extends Application {
	private int width = 800;
	private int height = 600;
	private float timePerFixedUpdate = 1f / 60f;
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	private LevelLeser derLevelLeser;
	private Spielwelt dieSpielwelt;
	private Daten[] dieDaten;
	
	
	
	public static void main(String args[]) {
		System.out.println("#############################################");
		System.out.println("# Sonic the Hedgehog Halbjahresprojekt 2021 #");
		System.out.println("#############################################");
		
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create Window
		Group root = new Group();
		canvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		Scene scene = new Scene((Parent)root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sonic the Hedgehog HJP");
		//primaryStage.setWidth(width);
		//primaryStage.setHeight(height);
		primaryStage.setResizable(false);
		
		primaryStage.show();
		
		
		// Initialize static main stuff
		Kamera.init(gc, width, height);
		Eingabe.init(scene);
		Kollision.init(90, 10);
		
		
		// Load game
		LevelLeser leser = new LevelLeser("files/maps/level.txt");
		dieSpielwelt = leser.erzeugeSpielwelt();
		//dieSpielwelt = new Spielwelt(new Image("file:files/textures/maps/map_01.png"), 32, 30, 16f, null, null, null, null);
		
		
		// Game loop
		runGame();
	}
	
	
	private void runGame() {
		// START
		dieSpielwelt.start();
		
		
		// LOOPS
		AnimationTimer animTimer = new AnimationTimer() {
			private long lastNanoSecs = -1;
			private float fixedUpdateTimer;
			
			@Override
			public void handle(long currentNanoSecs) {
				if (lastNanoSecs == -1) {
					lastNanoSecs = currentNanoSecs;
					fixedUpdateTimer = 0;
					return;
				}
				gc.clearRect(0, 0, width, height);
				
				
				float delta = (float)(currentNanoSecs - lastNanoSecs) / 1000000000f;
				lastNanoSecs = currentNanoSecs;
				
				dieSpielwelt.update(delta);
				
				fixedUpdateTimer += delta;
				while (fixedUpdateTimer >= timePerFixedUpdate) {
					fixedUpdateTimer -= timePerFixedUpdate;
					
					dieSpielwelt.fixedUpdate(timePerFixedUpdate);
				}
				
				dieSpielwelt.draw();
			}
		};
		animTimer.start();
	}
}








