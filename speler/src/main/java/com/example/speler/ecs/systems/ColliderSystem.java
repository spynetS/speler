package com.example.speler.ecs.systems;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.CollisionManifold;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.CollisionListener;
import com.example.speler.ecs.listeners.EntityListener;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class ColliderSystem implements UpdateSystem, EntityListener {

		// When we detect a collision we send that event to all the collisionListeners
		public static List<CollisionListener> onCollisioners = new LinkedList<>();

		// if ecs gets a new system and its a collision listener add it to
		// our listeners
		@Override
		public void onSystemAdded(UpdateSystem system) {
				if(system instanceof CollisionListener){
						onCollisioners.add((CollisionListener)system);
				}
		}
		
		@Override
		public void update(ECS ecs, float dt) {
			var entities = ecs.getEntities();
			
			for (UUID a : entities) {
				Transform ta = ecs.getComponent(a, Transform.class);
				ColliderComponent ca = ecs.getComponent(a, ColliderComponent.class);
				
				if (ca == null)
					continue;

				for (UUID b : entities) {
					if (a.equals(b))
						continue;

					Transform tb = ecs.getComponent(b, Transform.class);
					ColliderComponent cb = ecs.getComponent(b, ColliderComponent.class);

					if (cb == null)
						continue;

					if(ca.layer != cb.layer)
							continue;
					
					handleCollision(a, ta, ca, b, tb, cb,ecs);
				}
			}
		}

		private void onCollision(UUID a, Transform ta, ColliderComponent ca,
														 UUID b, Transform tb, ColliderComponent cb,
														 CollisionManifold manifold, ECS ecs) {
			if (!manifold.collides)
				return;
			
			CollisionEvent event = new CollisionEvent(a, b, ta, ca, tb, cb, manifold, ecs);

			// event.transformB.worldPosition.getX() -= event.penetrationDepth * event.normalX;
			// event.transformB.worldPosition.y -= event.penetrationDepth * event.normalY;
			
			if(!event.colliderA.isTrigger && !event.colliderB.isTrigger){
					event.transformA.position.setX(event.transformA.position.getX() - event.penetrationDepth * event.normalX);
					event.transformA.position.setY(event.transformA.position.getY() - event.penetrationDepth * event.normalY);
					for (CollisionListener listener : onCollisioners) {
							listener.onCollision(event);
					}
			}
			else{
					for (CollisionListener listener : onCollisioners) {
							listener.onTrigger(event);
					}
			}
			
		}

		
		private void handleCollision(UUID a, Transform ta, ColliderComponent ca,
																 UUID b, Transform tb, ColliderComponent cb, ECS ecs) {

				boolean aCircle = ca.circle;
				boolean bCircle = cb.circle;

				// Circle vs Rectangle
				if (aCircle && !bCircle) {
					var m = circleRect(ta, ca, tb, cb);
					onCollision(a, ta, ca, b, tb, cb,m,ecs);
					
				} else if (!aCircle && bCircle) {
						var m  = circleRect(tb, cb, ta, ca);
						onCollision(a, ta, ca,b, tb, cb,m,ecs);
				} else if (aCircle && bCircle) {
						var m = circleCircle(tb,cb,ta,ca);
						onCollision(a, ta, ca,b, tb, cb,m,ecs);
				} else if (!aCircle && !bCircle) {
						var m = rectRect(ta,ca,tb,cb);
						onCollision(a, ta, ca,b, tb, cb,m,ecs);
				}
		}

		private CollisionManifold circleCircle(Transform at, ColliderComponent ac, Transform bt, ColliderComponent bc) {
			float ax = at.worldPosition.getX();
			float ay = at.worldPosition.getY();
			float ar = at.worldScale.getX() / 2;

			float bx = bt.worldPosition.getX();
			float by = bt.worldPosition.getY();
			float br = bt.worldScale.getX() / 2;

			float dx = ax - bx;
			float dy = ay - by;

			float distanceSq = dx * dx + dy * dy;
			float r = ar+br;

			if (distanceSq > r * r)
        return CollisionManifold.none();

			CollisionManifold m = new CollisionManifold(true);
			float distance = (float) Math.sqrt(distanceSq);
			
			if (distance != 0) {

					m.normalX = dx / distance;
					m.normalY = dy / distance;
					m.penetrationDepth = r - distance;
			} else {
					// circles are directly on top of each other
					m.normalX = 1;
					m.normalY = 0;
					m.penetrationDepth = r;
			}

			return m;
		}
		

		private CollisionManifold circleRect(Transform tc, ColliderComponent cc,
				Transform tr, ColliderComponent rc) {

			float cx = tc.worldPosition.getX();
			float cy = tc.worldPosition.getY();
			float r = tc.worldScale.getX() / 2 + rc.height;

			float rx = tr.worldPosition.getX();
			float ry = tr.worldPosition.getY();

			float halfW = (tr.worldScale.getX() + rc.width) * 0.5f;
			float halfH = (tr.worldScale.getY() + rc.height) * 0.5f;

			// Find closest point on rectangle to the circle center
			float closestX = Math.max(rx - halfW, Math.min(cx, rx + halfW));
			float closestY = Math.max(ry - halfH, Math.min(cy, ry + halfH));

			float dx = closestX - cx;
			float dy = closestY - cy;

			float distSq = dx * dx + dy * dy;
			if (distSq > r * r) {
				return CollisionManifold.none();
			}

			CollisionManifold m = new CollisionManifold(true);

			float dist = (float) Math.sqrt(distSq);

			if (dist != 0) {
				m.normalX = dx / dist;
				m.normalY = dy / dist;
				m.penetrationDepth = r - dist;
			} else {
				// Circle is inside rectangle center
				// Pick a normal pointing outward
				if (Math.abs(dx) > Math.abs(dy)) {
					m.normalX = dx < 0 ? -1 : 1;
					m.normalY = 0;
					m.penetrationDepth = r;
				} else {
					m.normalX = 0;
					m.normalY = dy < 0 ? -1 : 1;
					m.penetrationDepth = r;
				}
			}

			return m;
		}

		public CollisionManifold rectRect(Transform tr1, ColliderComponent cr1,
				Transform tr2, ColliderComponent cr2) {
			float halfW1 = (tr1.worldScale.getX() + cr1.width) * 0.5f;
			float halfH1 = (tr1.worldScale.getY() + cr1.height) * 0.5f;
			float minX1 = tr1.worldPosition.getX() - halfW1;
			float maxX1 = tr1.worldPosition.getX() + halfW1;
			float minY1 = tr1.worldPosition.getY() - halfH1;
			float maxY1 = tr1.worldPosition.getY() + halfH1;

			float halfW2 = (tr2.worldScale.getX() + cr2.width) * 0.5f;
			float halfH2 = (tr2.worldScale.getY() + cr2.height) * 0.5f;
			float minX2 = tr2.worldPosition.getX() - halfW2;
			float maxX2 = tr2.worldPosition.getX() + halfW2;
			float minY2 = tr2.worldPosition.getY() - halfH2;
			float maxY2 = tr2.worldPosition.getY() + halfH2;

			// Check for no overlap (using Separating Axis Theorem for AABBs)
			if (maxX1 < minX2 || minX1 > maxX2 || maxY1 < minY2 || minY1 > maxY2) {
				return CollisionManifold.none();
			}

			// Collision detected, calculate manifold
			CollisionManifold m = new CollisionManifold(true);

			// Vector from center of rect1 to center of rect2
			float dx = tr2.worldPosition.getX() - tr1.worldPosition.getX();
			float dy = tr2.worldPosition.getY() - tr1.worldPosition.getY();

			// Calculate overlap on each axis
			float overlapX = (halfW1 + halfW2) - Math.abs(dx);
			float overlapY = (halfH1 + halfH2) - Math.abs(dy);

			// The axis of minimum penetration is the collision axis
			if (overlapX < overlapY) {
				m.penetrationDepth = overlapX;
				m.normalX = dx > 0 ? 1 : -1;
				m.normalY = 0;
			} else {
				m.penetrationDepth = overlapY;
				m.normalX = 0;
				m.normalY = dy > 0 ? 1 : -1;
			}

			return m;
		}
		
		@Override
		public void start(ECS ecs) {
				// TODO Auto-generated method stub
		}

		@Override
		public void onComponentAdded(UUID id, Component component) {} // not used
}


