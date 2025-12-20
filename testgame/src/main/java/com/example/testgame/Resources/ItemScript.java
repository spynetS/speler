package com.example.testgame.Resources;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.scripting.Script;

public class ItemScript extends Script {


		@Override
		public void start() {
				var c = new ColliderComponent();
				c.isTrigger = true;
				gameObject.addComponent(c);
		}
		
		@Override
		public void onTrigger(CollisionEvent event) {
				gameObject.getEcs().removeEntity(gameObject.id);
				event.transformB.worldScale = new Vector2(200, 200);
				event.transformA.worldScale = new Vector2(200, 200);
		}
}
