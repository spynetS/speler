package com.example.testgame.Resources;

import java.util.UUID;

import com.example.speler.Vector2;
import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.example.testgame.Player.Player;

public class ItemScript extends Script {


		public GameObject player;

		@Override
		public void start() {
				var c = new ColliderComponent();
				c.isTrigger = true;
				gameObject.addComponent(c);
		}
		
		@Override
		public void onTrigger(CollisionEvent event) {
				player = new GameObject(event.ecs, event.b);
		}

		public void use(){
				
		}
}
