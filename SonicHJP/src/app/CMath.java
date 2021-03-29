package app;

public class CMath {
	// ATTRIBUTE //
	// ...
	

	// KONSTRUKTOR //
	// ...
	
	
	// METHODEN //
	public static float distance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	public static float move(float from, float to, float delta) {
		float ph = to - from;
		
		if (Math.abs(ph) < delta) {
			delta = Math.abs(ph);
		}
		
		return from + Math.signum(to - from) * delta;
	}
	
	public static float angleBetweenDirs(float dx1, float dy1, float dx2, float dy2) {
		// cos(a) = (VEC1 * VEC2) / (|VEC1| * |VEC2|)
		// V1 * V2 = V1x * V2x + V1y * V2y
		// |V1| = Betrag davon -> laenge
		
		float num = clamp((float)((dx1 * dx2 + dy1 * dy2) / (Math.sqrt(dx1 * dx1 + dy1 * dy1) * Math.sqrt(dx2 * dx2 + dy2 * dy2))), -1, 1);
		
		return (float)Math.acos(num) * (float)(180f / Math.PI);
	}
	
	public static float min(float num, float mini) {
		if (num < mini)
			return mini;
		
		return num;
	}
	
	public static float max(float num, float maxi) {
		if (num > maxi)
			return maxi;
		
		return num;
	}
	
	public static float clamp(float num, float mini, float maxi) {
		if (num < mini)
			return mini;
		else if (num > maxi)
			return maxi;
		return num;
	}
}
