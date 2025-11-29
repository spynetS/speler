
package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;

import com.example.speler.input.Input;
import com.example.speler.input.Keys;

import com.example.speler.scripting.Script;

public class MyScript extends Script {

		Vector2 acceleration = new Vector2(0, 1);
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);

			//			acceleration.y += 1 * 0.016;

			// acceleration.x *= 0.998;
			// acceleration.y *= 0.998;

			
			if (Input.isKeyDown(Keys.A)){
					//					this.gameObject.transform.worldX-=1;
		acceleration.x += -0.1;
			}
					
			if (Input.isKeyDown(Keys.D)){
					//					this.gameObject.transform.worldX+=1;
					acceleration.x += 0.1;
			}


			if (Input.isKeyDown(Keys.W)){
					//					this.gameObject.transform.worldY-=1;
					acceleration.y -= 0.1;
			}
			if (Input.isKeyDown(Keys.S)){
					//					this.gameObject.transform.worldY+=1;
					acceleration.y -= 0.1;
			}
			
			this.gameObject.transform.worldX += acceleration.getX();
			this.gameObject.transform.worldY += acceleration.getY();
		}
			

		@Override
		public void onCollision(CollisionEvent event) {

				if(event.a.equals(this.gameObject.id)){
						this.gameObject.transform.worldX -= event.penetrationDepth * event.normalX;
						this.gameObject.transform.worldY -= event.penetrationDepth * event.normalY;

						//						acceleration.x *= event.normalX;
						//						acceleration.y *= event.normalY;
						
						acceleration.x -= event.penetrationDepth * event.normalX * 2;
						acceleration.y -= event.penetrationDepth * event.normalY * 2;

				}
				
		}
		
}
