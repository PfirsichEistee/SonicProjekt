package app;

public class RaycastHit {
	// ATTRIBUTE //
	public float hitX, hitY;
	public float normalX, normalY;
	public float distance;
	public Kollision sourceCollision; // if collider was platform && inside alternative array, this will be filled. otherwise null
	
	
	// KONSTRUKTOR //
	public RaycastHit(float pHitX, float pHitY, float pNormalX, float pNormalY, float pDistance) {
		hitX = pHitX;
		hitY = pHitY;
		normalX = pNormalX;
		normalY = pNormalY;
		distance = pDistance;
		sourceCollision = null;
	}
	
	
	// METHODEN //
	// ...
}
