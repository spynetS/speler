package com.example.testgame;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class KillableEntity extends Script {

		float hp = 5;
		
		@Override
		public void onTrigger(CollisionEvent event) {
				// TODO Auto-generated method stub
				super.onTrigger(event);
				System.out.println("TRIGGER");
				if(--hp <= 0){
						gameObject.getEcs().removeEntity(gameObject.id);

						GameObject droped = new GameObject(gameObject.getEcs());
						droped.addComponent(new Renderable());
						droped.transform.position = gameObject.transform.position;

						var r = new Rigidbody(false);
						r.acceleration = new Vector2(100,10);
						droped.addComponent(r);
						
						
						var c = new ColliderComponent();
						c.isTrigger = true;
						droped.addComponent(c);
						droped.addComponent(new ScriptComponent(new Item()));

						droped.transform.worldScale = new Vector2(10,10);
						
				}


				gameObject.getEcs().removeEntity(event.b);
		}

		
}
