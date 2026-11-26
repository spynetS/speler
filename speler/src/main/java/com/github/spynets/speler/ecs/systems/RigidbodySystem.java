package com.github.spynets.speler.ecs.systems;

import com.github.spynets.speler.ecs.CollisionEvent;
import com.github.spynets.speler.ecs.ECS;
import com.github.spynets.speler.ecs.components.*;
import com.github.spynets.speler.ecs.listeners.CollisionListener;
import java.util.UUID;

// we listen to collision events and act on them based on our rigibidbody
public class RigidbodySystem implements UpdateSystem, CollisionListener {

		@Override
		public void onCollision(CollisionEvent event) {
				Rigidbody rb1 = event.ecs.getComponent(event.a, Rigidbody.class);
				if(rb1 != null){
						// event.transformA.worldPosition.x -= event.penetrationDepth * event.normalX;
						// event.transformA.worldPosition.y -= event.penetrationDepth * event.normalY;

						rb1.acceleration.setX(rb1.acceleration.getX() - event.penetrationDepth * event.normalX * 200f);
						rb1.acceleration.setY(rb1.acceleration.getY() - event.penetrationDepth * event.normalY * 200f);
						
				}
		}

		@Override
		public void onTrigger(CollisionEvent event) {		}
		
		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID entity : ecs.getEntities()) {
						Rigidbody rb = ecs.getComponent(entity, Rigidbody.class);
						if(rb != null){
								rb.acceleration = rb.acceleration.multiply(rb.friction);
							
								Transform transform = ecs.getComponent(entity, Transform.class);
								if(transform == null) continue;
								// Integrate into the local position: ParentSystem derives
								// worldPosition from it every frame, so anything written to
								// worldPosition here is overwritten before it is ever drawn.
								transform.position.setX(transform.position.getX() + rb.acceleration.getX()*deltaTime);
								transform.position.setY(transform.position.getY() + rb.acceleration.getY()*deltaTime);
								if(rb.useGravity)
										rb.acceleration.setY(rb.acceleration.getY() + 9.82f * 0.5f);
						}

				}
		}
		@Override
		public void start(ECS ecs) {
		}
}
