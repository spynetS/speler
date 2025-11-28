package com.example.speler.ecs;

import java.util.Map;
import java.util.UUID;

import com.example.speler.ecs.components.*;

public class ColliderSystem implements UpdateSystem {


		public static Map<
		
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

		private void onCollision(UUID a, UUID b) {
				System.out.println(a+" "+b);
		}

		
		private void handleCollision(UUID a, Transform ta, ColliderComponent ca,
																 UUID b, Transform tb, ColliderComponent cb) {

				boolean aCircle = ca.circle;
				boolean bCircle = cb.circle;

				// Circle vs Circle
				if (aCircle && bCircle) {
						if (circleCircle(ta, ca, tb, cb)) {
								onCollision(a, b);
						}
				}
				// Circle vs Rectangle
				else if (aCircle && !bCircle) {
						if (circleRect(ta, ca, tb, cb)) {
								onCollision(a, b);
						}
				} else if (!aCircle && bCircle) {
						if (circleRect(tb, cb, ta, ca)) { // reverse args
								onCollision(a, b);
						}
				}
				// Rectangle vs Rectangle
				else {
						if (rectRect(ta, ca, tb, cb)) {
								onCollision(a, b);
						}
				}
		}

		private boolean circleCircle(Transform ta, ColliderComponent ca,
																 Transform tb, ColliderComponent cb) {

				float dx = tb.worldX - ta.worldX;
				float dy = tb.worldY - ta.worldY;
				float distSq = dx * dx + dy * dy;

				float r = ca.radius + cb.radius;

				return distSq <= r * r;
		}

		private boolean circleRect(Transform tc, ColliderComponent cc,
															 Transform tr, ColliderComponent rc) {

				float cx = tc.worldX;
				float cy = tc.worldY;
				float r = cc.radius;

				float rx = tr.worldX;
				float ry = tr.worldY;
				
				float halfW = rc.width * 0.5f;
				float halfH = rc.height * 0.5f;

				// Find closest point on rectangle to the circle center
				float closestX = Math.max(rx - halfW, Math.min(cx, rx + halfW));
				float closestY = Math.max(ry - halfH, Math.min(cy, ry + halfH));

				float dx = closestX - cx;
				float dy = closestY - cy;

				return dx * dx + dy * dy <= r * r;
		}

		private boolean rectRect(Transform ta, ColliderComponent ca,
														 Transform tb, ColliderComponent cb) {

				return Math.abs(ta.worldX - tb.worldX) * 2 < (ca.width + cb.width) &&
						Math.abs(ta.worldY - tb.worldY) * 2 < (ca.height + cb.height);
		}
		@Override
		public void start() {
				// TODO Auto-generated method stub
		}

}

