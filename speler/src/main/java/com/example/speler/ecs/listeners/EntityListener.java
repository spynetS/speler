package com.example.speler.ecs.listeners;

import java.util.UUID;

import com.example.speler.ecs.ECS.Component;

public interface EntityListener {
	public void onComponentAdded(UUID id, Component component);
}
