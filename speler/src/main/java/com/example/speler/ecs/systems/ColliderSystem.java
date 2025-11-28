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

public class ColliderSystem implements UpdateSystem, EntityListener {

		public static List<CollisionListener> onCollisioners = new LinkedList<>();

		// if ecs gets a new component and its a collision listener add it to
		// our listeners
		@Override
		public void onComponentAdded(UUID id, Component component) {
				if(component instanceof CollisionListener){
						onCollisioners.add((CollisionListener)component);
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

					handleCollision(a, ta, ca, b, tb, cb);
				}
			}
		}

		private void onCollision(UUID a, Transform ta, ColliderComponent ca,
														 UUID b, Transform tb, ColliderComponent cb,
																 CollisionManifold manifold) {
			if (!manifold.collides)
				return;
			
				CollisionEvent event = new CollisionEvent(a, b, ta, ca, tb, cb,manifold);

				for (CollisionListener listener : onCollisioners) {

						listener.onCollision(event);
				}
		}

		
		private void handleCollision(UUID a, Transform ta, ColliderComponent ca,
																 UUID b, Transform tb, ColliderComponent cb) {

				boolean aCircle = ca.circle;
				boolean bCircle = cb.circle;

				// Circle vs Rectangle
				if (aCircle && !bCircle) {
					var m = circleRect(ta, ca, tb, cb);
					onCollision(a, ta, ca, b, tb, cb,m);
					
				} else if (!aCircle && bCircle) {
					var m = circleRect(tb, cb, ta, ca);
					onCollision(a, ta, ca,b, tb, cb,m);
				}
		}

		private CollisionManifold circleRect(Transform tc, ColliderComponent cc,
															 Transform tr, ColliderComponent rc) {

				float cx = tc.worldX;
				float cy = tc.worldY;
				float r = tc.worldW/2;

				float rx = tr.worldX;
				float ry = tr.worldY;
				
				float halfW = (tr.worldW + rc.width) * 0.5f;
				float halfH = (tr.worldH + rc.height) * 0.5f;

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

				float dist = (float)Math.sqrt(distSq);

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

		@Override
		public void start() {
				// TODO Auto-generated method stub
		}

}

