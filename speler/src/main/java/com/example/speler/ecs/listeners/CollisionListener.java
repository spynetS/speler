package com.example.speler.ecs.listeners;

import java.util.UUID;

import com.example.speler.ecs.CollisionEvent;

public interface CollisionListener {

	public void onCollision(CollisionEvent event);
	
}
