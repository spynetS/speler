
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
					//					this.gameObject.transform.worldX-=1;
					rb.acceleration.x += -0.1;
			}
					
			if (Input.isKeyDown(Keys.D)){
					//					this.gameObject.transform.worldX+=1;
					rb.acceleration.x += 0.1;
			}


			if (Input.isKeyDown(Keys.W)){
					//					this.gameObject.transform.worldY-=1;
					rb.acceleration.y -= 0.1;
			}
			if (Input.isKeyDown(Keys.S)){
					//					this.gameObject.transform.worldY+=1;
					rb.acceleration.y += 0.1;
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
