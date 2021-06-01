package Objects;

import java.util.ArrayList;

import app.CMath;
import app.Kamera;
import app.Kollision;
import javafx.scene.image.Image;

public class MovingPlatform {
	// ATTRIBUTE //
	private static Image[] dasImage = {
			new Image("file:files/textures/misc/platform1.png"),
			new Image("file:files/textures/misc/platform2.png"),
	};
	public static ArrayList<MovingPlatform> list = new ArrayList<MovingPlatform>();
	
	private Kollision collision;
	
	private int id;
	private float x, y;
	private float targetX, targetY;
	private float w, h;
	private float deltaX, deltaY;
	
	private float progress;
	private float targetProgress;;
	
	
	// KONSTRUKTOR //
	public MovingPlatform(int pID, float px, float py, float pw, float ph, float pTargetX, float pTargetY) {
		id = pID;
		x = px;
		y = py;
		targetX = pTargetX;
		targetY = pTargetY;
		w = pw;
		h = ph;
		deltaX = 0;
		deltaY = 0;
		
		progress = (float)Math.random();
		targetProgress = 1;
		
		collision = new Kollision(x, y, x + w, y, true, true);
		list.add(this);
	}
	
	
	// METHODEN //
	public void fixedUpdate(float delta) {
		float lastPosX = (targetX - x) * progress + x;
		float lastPosY = (targetY - y) * progress + y;
		
		progress = CMath.move(progress, targetProgress, delta);
		if (progress == targetProgress)
			targetProgress = 1 - targetProgress;
		

		float newPosX = (targetX - x) * progress + x;
		float newPosY = (targetY - y) * progress + y;
		
		deltaX = newPosX - lastPosX;
		deltaY = newPosY - lastPosY;
		
		collision.x1 = newPosX;
		collision.y1 = newPosY;
		collision.x2 = newPosX + w;
		collision.y2 = newPosY;
	}
	
	
	public void draw(Kamera dieKamera) {
		dieKamera.drawImage(dasImage[id], (targetX - x) * progress + x, (targetY - y) * progress + y - h, w, h, 0);
	}
	
	
	public boolean isCollision(Kollision k) {
		return (collision == k);
	}
	
	public float getDeltaX() {
		return deltaX;
	}
	public float getDeltaY() {
		return deltaY;
	}
}
