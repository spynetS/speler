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
public class ScriptSystem implements UpdateSystem, CollisionListener, EntityListener {

		LinkedList<Script> shouldStart = new LinkedList<>();
		
		@Override
		public void onComponentAdded(UUID id, Component component) {
				if(component instanceof ScriptComponent && ((ScriptComponent)component).script != null ){
						shouldStart.add(((ScriptComponent)component).script);
				}
		}
		
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

				for(Script s : shouldStart) s.start();
				shouldStart.clear();
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
		public void onTrigger(CollisionEvent event) {

				ScriptComponent sc1 = event.ecs.getComponent(event.a, ScriptComponent.class);
				ScriptComponent sc2 = event.ecs.getComponent(event.b, ScriptComponent.class);
				if (sc1 != null && sc1.script != null) {
						sc1.script.onTrigger(event);
				}
				if (sc2 != null && sc2.script != null) {
						sc2.script.onTrigger(event);
				}

		}

		@Override
		public void start(ECS ecs) {
				for (UUID entity : ecs.getEntities()) {
						ScriptComponent sc = ecs.getComponent(entity, ScriptComponent.class);
						if (sc != null && sc.script != null) {
								if(sc.script.gameObject == null)
										sc.script.gameObject = new GameObject(ecs, entity);
									sc.script.start();
									
						}
				}
		}

		@Override
		public void onSystemAdded(UpdateSystem system) {

		}
}
