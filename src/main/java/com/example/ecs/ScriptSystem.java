package com.example.ecs;

import com.example.ecs.UpdateSystem;
import com.example.ecs.ECS;
import com.example.ecs.components.*;
import com.example.scripting.GameObject;

import java.util.UUID;


public class ScriptSystem implements UpdateSystem {
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
		public void start() {
		}
}
