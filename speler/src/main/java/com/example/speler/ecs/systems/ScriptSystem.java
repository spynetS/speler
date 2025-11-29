package com.example.speler.ecs.systems;

import com.example.speler.ecs.systems.UpdateSystem;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.CollisionListener;
import com.example.speler.scripting.GameObject;

import java.util.UUID;


public class ScriptSystem implements UpdateSystem, CollisionListener {

		
		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						ScriptComponent sc = ecs.getComponent(entity, ScriptComponent.class);
						if (sc != null && sc.script != null) {
								if(sc.script.gameObject == null)
										sc.script.gameObject = new GameObject(ecs, entity);
								sc.script.update(deltaTime);
						}
				}
		}

		@Override
		public void onCollision(CollisionEvent event) {

				ScriptComponent sc1 = event.ecs.getComponent(event.a, ScriptComponent.class);
				ScriptComponent sc2 = event.ecs.getComponent(event.b, ScriptComponent.class);
				if (sc1 != null && sc1.script != null) {
						sc1.script.onCollision(event);
				}
				if (sc2 != null && sc2.script != null) {
						sc2.script.onCollision(event);
				}

		}

		
		@Override
		public void start() {
		}
}
