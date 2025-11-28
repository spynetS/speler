
package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.scripting.Script;

public class MyScript extends Script {

		Vector2 acceleration = new Vector2(1, 0);
		
		@Override
		public void update(float deltatime) {
			super.update(deltatime);
			this.gameObject.transform.worldX += acceleration.getX();
			this.gameObject.transform.worldY += acceleration.getY();
		}
	
}
