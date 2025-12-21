package com.example.testgame.Resources;

import java.util.LinkedList;

import com.example.speler.Game;
import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.components.AnimationComponent;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class KillableEntity extends Script {

		float hp = 5;
		LinkedList<ItemScript> dropping = new LinkedList<>();

		AnimationComponent animationComponent;
		
		@Override
		public void start() {
				// TODO Auto-generated method stub
				super.start();
		}

		
		@Override
		public void onTrigger(CollisionEvent event) {
				super.onTrigger(event);
				if(--hp <= 0){
						for(ItemScript item : dropping){
								GameObject dropped = new GameObject(gameObject.getEcs());
								dropped.transform.worldScale = new Vector2(10,10);
								dropped.transform.position = gameObject.transform.position;
								dropped.addComponent(new ScriptComponent(item));
								
						}
						// remove tree
						gameObject.getEcs().removeEntity(gameObject.id);
				}

				// remove bulletp

				gameObject.getEcs().removeEntity(event.b);
		}

		
}
