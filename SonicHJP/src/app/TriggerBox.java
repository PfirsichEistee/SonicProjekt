package app;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TriggerBox {
	// ATTRIBUTE //
	private static ArrayList<TriggerBox> tbList = new ArrayList<TriggerBox>();
	
	private float x, y;
	private float w, h;
	public boolean wasInside;
	
	public Object callObject;
	public String callMethod;
	
	
	// KONSTRUKTOR //
	public TriggerBox(float px, float py, float pw, float ph, Object pCallObject, String pCallMethod) {
		x = px;
		y = py;
		w = pw;
		h = ph;
		wasInside = false;
		callObject = pCallObject;
		callMethod = pCallMethod;
		
		tbList.add(this);
	}
	
	
	// METHODEN //
	public static void update(float px, float py) {
		for (TriggerBox tb : tbList) {
			if (tb.isInside(px, py)) {
				if (tb.wasInside)
					continue;
				
				
				tb.wasInside = true;
				
				// "Reflektion"
				Class cls = tb.callObject.getClass();
				try {
					Method mth = cls.getMethod(tb.callMethod, null);
					
					mth.invoke(tb.callObject, null);
				} catch (Exception e) {
					System.out.println("[TRIGGERBOX] Failed calling Method '" + tb.callMethod + "'");
				}
				
				break;
			} else if (tb.wasInside) {
				tb.wasInside = false;
			}
		}
	}
	
	
	public boolean isInside(float px, float py) {
		if (px > x && px < (x + w) && py > y && py < (y + h))
			return true;
		
		return false;
	}
	
	
	public static void init() {
		tbList = new ArrayList<TriggerBox>();
	}
}
