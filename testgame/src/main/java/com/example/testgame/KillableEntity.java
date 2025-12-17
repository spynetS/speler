package com.example.testgame;

import com.example.speler.ecs.CollisionEvent;
import com.example.speler.scripting.Script;

public class KillableEntity extends Script {

		float hp = 5;
		
		@Override
		public void onTrigger(CollisionEvent event) {
				// TODO Auto-generated method stub
				super.onTrigger(event);
				System.out.println("TRIGGER");
				if(--hp <= 0)
					gameObject.getEcs().removeEntity(gameObject.id);

				gameObject.getEcs().removeEntity(event.b);
		}

		
}
