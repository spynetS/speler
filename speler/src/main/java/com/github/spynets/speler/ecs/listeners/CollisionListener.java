package com.github.spynets.speler.ecs.listeners;

import com.github.spynets.speler.ecs.CollisionEvent;

public interface CollisionListener {

	void onCollision(CollisionEvent event);
	void onTrigger(CollisionEvent event);
	
}
