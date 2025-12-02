
package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;

import com.example.speler.scripting.Script;

public class MyScript extends Script {
		Rigidbody rb;
		
		@Override
		public void start() {
				// TODO Auto-generated method stub
				super.start();
				System.out.println("START SCRIPT");

				rb = gameObject.getComponent(Rigidbody.class);
				this.scriptName  = "myscript";
		}
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);


			if (Input.isKeyDown(Keys.A)){
					rb.acceleration.x += -5;
			}
					
			if (Input.isKeyDown(Keys.D)){
					rb.acceleration.x += 5;
			}

			if (Input.isKeyDown(Keys.W)){
					rb.acceleration.y -= 10;
			}
			if (Input.isKeyDown(Keys.S)){
					rb.acceleration.y += 5;
			}
				 
			//			this.gameObject.transform.worldX += rb.acceleration.getX();
			//			this.gameObject.transform.worldY += rb.acceleration.getY();
		}
			

		@Override
		public void onCollision(CollisionEvent event) {

				if(event.a.equals(this.gameObject.id)){

						
						// rb.acceleration.x -= event.penetrationDepth * event.normalX * 2;
						//rb.acceleration.y -= event.penetrationDepth * event.normalY * 2; 
				}
		}
}
