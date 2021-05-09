package Objects;

import app.CMath;
import app.Kamera;
import javafx.scene.image.Image;

public class SS_Looping extends SpezialStrecke {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/s_loop.png");
	
	
	// KONSTRUKTOR //
	public SS_Looping(float px, float py) {
		super(px, py);
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		
	}
	
	@Override
	public void lateDraw(Kamera dieKamera) {
		
	}
	
	@Override
	public boolean isPointInRange(float px, float py) {
		return false;
	}
	
	@Override
	public float getSonicX(float progress) {
		return -1;
	}

	
	@Override
	public float getSonicY(float progress) {
		return -1;
	}
}
