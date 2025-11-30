package com.example.speler.ecs.systems;

import com.example.speler.ecs.systems.UpdateSystem;
import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.CollisionListener;
import com.example.speler.scripting.GameObject;
import java.util.UUID;

// we listen to collision events and act on them based on our rigibidbody
public class RigidbodySystem implements UpdateSystem, CollisionListener {

		@Override
		public void onCollision(CollisionEvent event) {
				Rigidbody rb1 = event.ecs.getComponent(event.a, Rigidbody.class);
				if(rb1 != null){
						event.transformA.worldX -= event.penetrationDepth * event.normalX;
						event.transformA.worldY -= event.penetrationDepth * event.normalY;

						rb1.acceleration.x -= event.penetrationDepth * event.normalX * 2f;
						rb1.acceleration.y -= event.penetrationDepth * event.normalY * 2f;
						
				}
		}
		
		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						Rigidbody rb = ecs.getComponent(entity, Rigidbody.class);
						if(rb != null){
								rb.acceleration.multiply(new Vector2(rb.friction,rb.friction));
								
								Transform transform = ecs.getComponent(entity, Transform.class);
								transform.worldX += rb.acceleration.x;
								transform.worldY += rb.acceleration.y;

								rb.acceleration.y += 9.82 * 0.005;
						}

				}
		}
		@Override
		public void start(ECS ecs) {
		}
}
