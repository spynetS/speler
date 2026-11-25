package com.github.spynets.speler.ecs.listeners;

import java.util.UUID;

import com.github.spynets.speler.ecs.ECS.Component;
import com.github.spynets.speler.ecs.systems.UpdateSystem;


/**
	 This is a listner for the EntityCompoentSystem (ECS)
	 which gets this events
 */
public interface EntityListener {
		public void onComponentAdded(UUID id, Component component);
		public void onComponentRemoved(UUID id, Component component);
		public void onSystemAdded(UpdateSystem system);

 }
