
package com.example.testgame;

import java.util.UUID;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.ECS;
import com.example.speler.input.Input;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class MyScript extends Script {

		Vector2 acceleration = new Vector2(1, 0);
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);
			//this.gameObject.transform.worldX += acceleration.getX();
			this.gameObject.transform.worldY += acceleration.getY();

			this.gameObject.transform.worldX = (float)Input.getMousePosition().x;
			//			this.gameObject.transform.worldY = (float)Input.getMousePosition().y;
		}

		@Override
		public void onCollision(CollisionEvent event) {
				System.out.println("COLLISION");
		}
		
}
