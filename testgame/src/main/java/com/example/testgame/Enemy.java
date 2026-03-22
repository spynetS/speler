package com.example.testgame;

import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.scripting.GameObject;
import com.example.testgame.Player.AnimationFactory;
import com.example.testgame.Player.Movement;

public class Enemy extends GameObject {

		public Enemy(ECS ecs){
				super(ecs);

				var spriteComponent = new SpriteComponent();
				addComponent(spriteComponent);
				addComponent(new AnimationFactory(spriteComponent,"/home/spy/dev/playengine/testgame/sprites/Player/Player.png").getAnimationComponent());

				var script = new ScriptComponent(new Movement());
				addComponent(script);

				var c = new ColliderComponent();
				c.height = -50;
				c.width = -50;
				addComponent(c);

				var rb = new Rigidbody(false);
				rb.friction = new Vector2(0.99f,0.99f);
				addComponent(rb);

				
		}
		
}
