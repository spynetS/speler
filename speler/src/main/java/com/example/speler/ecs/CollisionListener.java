package com.example.speler.ecs;

import java.util.UUID;

public interface CollisionListener {

	public void onCollision(UUID entityA, UUID entityB);
	
}
