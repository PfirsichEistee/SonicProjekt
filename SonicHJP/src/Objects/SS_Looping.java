package Objects;

import app.Kamera;
import app.Kollision;
import app.TriggerBox;
import javafx.scene.image.Image;

public class SS_Looping extends SpezialStrecke {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/loop.png");
	
	
	private Kollision[] kollisionenLinks;
	private Kollision[] kollisionenRechts;
	private boolean leftActive;
	
	private TriggerBox[] triggerBox;
	
	
	// KONSTRUKTOR //
	public SS_Looping(float px, float py) {
		super(px, py);
		
		triggerBox = new TriggerBox[3];
		triggerBox[0] = new TriggerBox(px - 6, py, 2, 5, this, "enterLeft");
		triggerBox[1] = new TriggerBox(px + 4, py - 2, 2, 5, this, "enterRight");
		triggerBox[2] = new TriggerBox(px - 1, py + 4, 2, 2, this, "flip");
		
		// static collisions
		new Kollision(-1.1340942f + px, -0.03976822f + py, 1.9740143f + px, -1.0532818f + py, false, false);
		new Kollision(4.014557f + px, -1.9992275f + py, 1.9740143f + px, -1.0532818f + py, false, false);
		new Kollision(-2.9584045f + px, 2.9602337f + py, -2.8232727f + px, 3.5683422f + py, false, false);
		new Kollision(-2.6070557f + px, 4.014288f + py, -2.8232727f + px, 3.5683422f + py, false, false);
		new Kollision(-2.2479553f + px, 4.4764233f + py, -2.6070557f + px, 4.014288f + py, false, false);
		new Kollision(-1.850296f + px, 4.8656387f + py, -2.2479553f + px, 4.4764233f + py, false, false);
		new Kollision(-1.336792f + px, 5.203476f + py, -1.850296f + px, 4.8656387f + py, false, false);
		new Kollision(-0.73309326f + px, 5.407116f + py, -1.336792f + px, 5.203476f + py, false, false);
		new Kollision(-0.20166016f + px, 5.5007744f + py, -0.73309326f + px, 5.407116f + py, false, false);
		new Kollision(0.44699097f + px, 5.487261f + py, -0.20166016f + px, 5.5007744f + py, false, false);
		new Kollision(1.0145569f + px, 5.3386116f + py, 0.44699097f + px, 5.487261f + py, false, false);
		new Kollision(1.5010529f + px, 5.0953693f + py, 1.0145569f + px, 5.3386116f + py, false, false);
		new Kollision(1.946991f + px, 4.798071f + py, 1.5010529f + px, 5.0953693f + py, false, false);
		new Kollision(2.3118591f + px, 4.3791523f + py, 1.946991f + px, 4.798071f + py, false, false);
		new Kollision(2.595642f + px, 3.9737473f + py, 2.3118591f + px, 4.3791523f + py, false, false);
		new Kollision(2.8118591f + px, 3.514288f + py, 2.595642f + px, 3.9737473f + py, false, false);
		new Kollision(2.9605103f + px, 2.919693f + py, 2.8118591f + px, 3.514288f + py, false, false);
		
		
		leftActive = false;
		
		// left collisions
		kollisionenLinks = new Kollision[8];
		
		kollisionenLinks[0] = new Kollision(-2.9695435f + px, 2.7392006f + py, -2.9230194f + px, 2.3903637f + py, false, true);
		kollisionenLinks[1] = new Kollision(-2.8183746f + px, 2.0880375f + py, -2.9230194f + px, 2.3903637f + py, false, true);
		kollisionenLinks[2] = new Kollision(-2.6555786f + px, 1.6345491f + py, -2.8183746f + px, 2.0880375f + py, false, true);
		kollisionenLinks[3] = new Kollision(-2.4230194f + px, 1.2740841f + py, -2.6555786f + px, 1.6345491f + py, false, true);
		kollisionenLinks[4] = new Kollision(-2.1323242f + px, 0.8089676f + py, -2.4230194f + px, 1.2740841f + py, false, true);
		kollisionenLinks[5] = new Kollision(-1.8183746f + px, 0.5415268f + py, -2.1323242f + px, 0.8089676f + py, false, true);
		kollisionenLinks[6] = new Kollision(-1.4462891f + px, 0.2043171f + py, -1.8183746f + px, 0.5415268f + py, false, true);
		kollisionenLinks[7] = new Kollision(-0.99279785f + px, -0.07475281f + py, -1.4462891f + px, 0.2043171f + py, false, true);
		
		// right collisions
		kollisionenRechts = new Kollision[9];
		kollisionenRechts[0] = new Kollision(-3.9432068f + px, -0.021800995f + py, 0.020645142f + px, -0.009752274f + py, false, true);
		kollisionenRechts[1] = new Kollision(0.65919495f + px, 0.07458496f + py, 0.020645142f + px, -0.009752274f + py, false, true);
		kollisionenRechts[2] = new Kollision(1.3459473f + px, 0.3155489f + py, 0.65919495f + px, 0.07458496f + py, false, true);
		kollisionenRechts[3] = new Kollision(1.7917328f + px, 0.56856155f + py, 1.3459473f + px, 0.3155489f + py, false, true);
		kollisionenRechts[4] = new Kollision(2.261612f + px, 1.0143442f + py, 1.7917328f + px, 0.56856155f + py, false, true);
		kollisionenRechts[5] = new Kollision(2.5748596f + px, 1.4601269f + py, 2.261612f + px, 1.0143442f + py, false, true);
		kollisionenRechts[6] = new Kollision(2.7435303f + px, 1.8456697f + py, 2.5748596f + px, 1.4601269f + py, false, true);
		kollisionenRechts[7] = new Kollision(2.9242554f + px, 2.2914524f + py, 2.7435303f + px, 1.8456697f + py, false, true);
		kollisionenRechts[8] = new Kollision(2.9965515f + px, 2.7492828f + py, 2.9242554f + px, 2.2914524f + py, false, true);
		
		for (int i = 0; i < kollisionenLinks.length; i++) {
			Kollision.altRemove(kollisionenLinks[i]);
		}
	}
	
	
	// METHODEN //
	public void enterLeft() {
		if (leftActive) {
			leftActive = false;
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altRemove(kollisionenLinks[i]);
			}
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altAdd(kollisionenRechts[i]);
			}
		}
	}
	public void enterRight() {
		if (!leftActive) {
			leftActive = true;
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altRemove(kollisionenRechts[i]);
			}
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altAdd(kollisionenLinks[i]);
			}
		}
	}
	public void flip() {
		if (leftActive) {
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altRemove(kollisionenLinks[i]);
			}
			
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altAdd(kollisionenRechts[i]);
			}
		} else {
			for (int i = 0; i < kollisionenRechts.length; i++) {
				Kollision.altRemove(kollisionenRechts[i]);
			}
			
			for (int i = 0; i < kollisionenLinks.length; i++) {
				Kollision.altAdd(kollisionenLinks[i]);
			}
		}
		
		leftActive = !leftActive;
	}


	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, 0, 0, 128, 256, x - 4, y - 2, 4, 8);
	}
	@Override
	public void lateDraw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, 128, 0, 128, 256, x, y - 2, 4, 8);
	}
	@Override
	public boolean isPointInRange(float px, float py) {
		return false;
	}
	@Override
	public float getSonicX(float progress) {
		return 0;
	}
	@Override
	public float getSonicY(float progress) {
		return 0;
	}
}
