
package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;

import com.example.speler.input.Input;
import com.example.speler.input.Keys;

import com.example.speler.scripting.Script;

public class MyScript extends Script {

		Vector2 acceleration = new Vector2(0, 10);
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);

			acceleration.y += 1 * 0.016;

			// acceleration.x *= 0.998;
			// acceleration.y *= 0.998;

			
			if (Input.isKeyDown(Keys.A))
					acceleration.x += -0.1;
			if (Input.isKeyDown(Keys.D))
				acceleration.x += 0.1;

			if (Input.isKeyPressed(Keys.W))
					acceleration.y -= 5;
			
			this.gameObject.transform.worldX += acceleration.getX();
			this.gameObject.transform.worldY += acceleration.getY();
		}
			

		@Override
		public void onCollision(CollisionEvent event) {
			this.gameObject.transform.worldX -= event.penetrationDepth * event.normalX;
			this.gameObject.transform.worldY -= event.penetrationDepth * event.normalY;
			
			acceleration.x -= event.penetrationDepth * event.normalX * 1.8;
			acceleration.y -= event.penetrationDepth * event.normalY * 1.8;
		}
		
}
