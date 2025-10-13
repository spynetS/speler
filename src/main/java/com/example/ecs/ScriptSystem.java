package com.example.ecs;

import com.example.ecs.UpdateSystem;
import com.example.ecs.ECS;
import com.example.ecs.ECS.ScriptComponent;
import java.util.UUID;


public class ScriptSystem implements UpdateSystem {
		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						ScriptComponent sc = ecs.getComponent(entity, ScriptComponent.class);
						System.out.println("HERE");
						if (sc != null && sc.script != null) {
								sc.script.update(deltaTime);
						}
				}
		}
		@Override
		public void start() {
		}
}
