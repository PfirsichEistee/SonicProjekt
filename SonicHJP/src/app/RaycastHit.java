package app;

public class RaycastHit {
	// ATTRIBUTE //
	public float hitX, hitY;
	public float normalX, normalY;
	public float distance;
	
	
	// KONSTRUKTOR //
	public RaycastHit(float pHitX, float pHitY, float pNormalX, float pNormalY, float pDistance) {
		hitX = pHitX;
		hitY = pHitY;
		normalX = pNormalX;
		normalY = pNormalY;
		distance = pDistance;
	}
	
	
	// METHODEN //
	// ...
}
