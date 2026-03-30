package com.example.speler.ecs.listeners;

import java.util.UUID;

import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.systems.UpdateSystem;


/**
	 This is a listner for the EntityCompoentSystem (ECS)
	 which gets this events
 */
public interface EntityListener {
		void onComponentAdded(UUID id, Component component);
		void onSystemAdded(UpdateSystem system);
 }
