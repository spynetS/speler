package com.example.speler.ecs.systems;

import com.example.speler.ecs.systems.UpdateSystem;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.CollisionListener;
import com.example.speler.ecs.listeners.EntityListener;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

import java.util.LinkedList;
import java.util.UUID;

// we listen for collisions and send them to the user scripts so they get
// collision events
public class ScriptSystem implements UpdateSystem, CollisionListener {

		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						ScriptComponent sc = ecs.getComponent(entity, ScriptComponent.class);
						if(sc == null) continue;
						for(Script script : sc.getScripts()) {
								if(script.gameObject == null){
										script.initScript(new GameObject(ecs, entity));
								}

								script.update(deltaTime);
						}
				}
		}

		@Override
		public void onCollision(CollisionEvent event) {

				ScriptComponent sc1 = event.ecs.getComponent(event.a, ScriptComponent.class);
				ScriptComponent sc2 = event.ecs.getComponent(event.b, ScriptComponent.class);
				
				for(Script script : sc1.getScripts()) {
						if (script != null) {
								script.onCollision(event);
						}
				}
				for(Script script : sc2.getScripts()) {
						if (script != null) {
								script.onCollision(event);
						}
				}				


		}
		@Override
		public void onTrigger(CollisionEvent event) {

				ScriptComponent sc1 = event.ecs.getComponent(event.a, ScriptComponent.class);
				ScriptComponent sc2 = event.ecs.getComponent(event.b, ScriptComponent.class);
				for(Script script : sc1.getScripts()) {
						if (script != null) {
								script.onTrigger(event);
						}
				}
				for(Script script : sc2.getScripts()) {
						if (script != null) {
								script.onTrigger(event);
						}
				}				

		}

		@Override
		public void start(ECS ecs) {

		}

}
