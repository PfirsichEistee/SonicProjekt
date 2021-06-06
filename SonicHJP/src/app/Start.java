package app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.Core;
import ui.Text;

public class Start extends Application {
	private static Start start;
	
	private final int width = 800;
	private final int height = 600;
	private final float timePerFixedUpdate = 1f / 60f;
	
	private GUI_Launcher launcher;
	private Stage gameStage;
	
	private Canvas canvas;
	private GraphicsContext gc;
	private AnimationTimer animTimer;
	
	private Spielwelt dieSpielwelt;
	
	
	
	public static void main(String args[]) {
		System.out.println("#############################################");
		System.out.println("# Sonic the Hedgehog Halbjahresprojekt 2021 #");
		System.out.println("#############################################");
		
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		start = this;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("launcher.fxml"));
		
		Scene scene = new Scene((Parent)loader.load());
		launcher = (GUI_Launcher)loader.getController();
		launcher.setStart(this);
		launcher.setPrimaryStage(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sonic HJP Launcher");
		
		primaryStage.setResizable(false);
		
		primaryStage.show();
	}
	
	
	public void startGame(Stage pGameStage, int mapID) throws Exception {
		gameStage = pGameStage;
		
		// Create Window
		Group root = new Group();
		canvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		gc.setImageSmoothing(false);
		root.getChildren().add(canvas);
		
		Scene scene = new Scene((Parent)root);
		
		gameStage.setScene(scene);
		gameStage.setTitle("Sonic the Hedgehog HJP");
		//primaryStage.setWidth(width);
		//primaryStage.setHeight(height);
		gameStage.setResizable(false);
		
		gameStage.show();
		
		
		// Initialize static main stuff
		Kamera.init(gc, width, height);
		Eingabe.init(scene);
		Kollision.init(90, 10);
		Core.init(gc, width, height);
		
		
		// Load game
		LevelLeser leser = new LevelLeser("files/maps/map_0" + mapID + ".txt");
		dieSpielwelt = leser.erzeugeSpielwelt();
		//dieSpielwelt = new Spielwelt(new Image("file:files/textures/maps/map_01.png"), 32, 30, 16f, null, null, null, null);
		
		
		// Game loop
		SoundMan.init(dieSpielwelt.getKamera(), mapID - 1);
		runGame();
		
		
		// Close Event
		gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				quit();
			}
		});
		
		
		// Mouse Debug
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() != MouseButton.SECONDARY) return;
				
				float px = dieSpielwelt.getKamera().pixelZuEinheitX((float)event.getX());
				float py = dieSpielwelt.getKamera().pixelZuEinheitY((float)event.getY());
				
				dieSpielwelt.getSpieler().setX(px);
				dieSpielwelt.getSpieler().setY(py);
				
				System.out.println("Debug Teleport " + px + " " + py);
			}
		});
	}
	
	
	private void runGame() {
		// START
		dieSpielwelt.start();
		
		
		// Debug
		DevTools devTools = new DevTools(dieSpielwelt);
		
		
		// LOOPS
		animTimer = new AnimationTimer() {
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
				delta *= devTools.deltaMult;
				lastNanoSecs = currentNanoSecs;
				
				dieSpielwelt.update(delta);
				
				fixedUpdateTimer += delta;
				while (fixedUpdateTimer >= timePerFixedUpdate) {
					fixedUpdateTimer -= timePerFixedUpdate;
					
					dieSpielwelt.fixedUpdate(timePerFixedUpdate);
				}
				devTools.update(delta);
				
				dieSpielwelt.draw();
				Core.draw();
				devTools.draw(delta);
			}
		};
		animTimer.start();
	}
	
	
	public static void quit() {
		start.animTimer.stop();
		start.launcher.show();
		start.gameStage.close();
		SoundMan.stopMusic();
	}
}








