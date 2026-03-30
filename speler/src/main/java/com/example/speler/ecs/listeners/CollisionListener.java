package com.example.speler.ecs.listeners;

import com.example.speler.ecs.CollisionEvent;

public interface CollisionListener {

	void onCollision(CollisionEvent event);
	void onTrigger(CollisionEvent event);
	
}
