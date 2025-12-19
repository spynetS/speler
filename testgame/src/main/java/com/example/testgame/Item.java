package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.scripting.Script;

public class Item extends Script {

		@Override
		public void onTrigger(CollisionEvent event) {
				gameObject.getEcs().removeEntity(gameObject.id);
				event.transformB.worldScale = new Vector2(200, 200);
				event.transformA.worldScale = new Vector2(200, 200);
		}
}
