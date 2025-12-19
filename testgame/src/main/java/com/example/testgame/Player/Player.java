package com.example.testgame.Player;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.testgame.Tile;
import com.example.testgame.Player.AnimationFactory;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;

public class Player extends GameObject {

	SpriteComponent spriteComponent;
		public Player(ECS ecs) {
			super(ecs);
			

			GameObject aimer = new GameObject(ecs);
			aimer.addComponent(new ScriptComponent(new Aimer()));
			aimer.transform.worldScale = new Vector2(20,20);
			
			spriteComponent = new SpriteComponent();			
			addComponent(spriteComponent);


			addComponent(new AnimationFactory(spriteComponent).getAnimationComponent());
			addComponent(new ScriptComponent(new Movement()));

			var c = new ColliderComponent();
			c.height = -50;
			c.width = -50;
			addComponent(c);

			var rb = new Rigidbody(false);
			rb.friction = new Vector2(0.9f,0.9f);
			addComponent(rb);
		}
		
}
