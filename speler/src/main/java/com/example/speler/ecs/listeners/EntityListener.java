package com.example.speler.ecs.listeners;

import java.util.UUID;

import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.systems.UpdateSystem;

public interface EntityListener {
		public void onComponentAdded(UUID id, Component component);
		public void onSystemAdded(UpdateSystem system);
}
