package com.example.testgame.Resources;

import java.io.File;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.*;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;

public class Tree extends GameObject {
		
		public Tree(ECS ecs){
				super(ecs);
				try{
						addComponent(new SpriteComponent(Sprite.getSprite("/home/spy/dev/playengine/testgame/sprites/Outdoor/Oak_Tree.png")));
				}catch (Exception e){}

				var collider = new ColliderComponent();
				collider.height = -170;
				collider.width = -150;
				this.addComponent(collider);

				addComponent(new SoundComponent(new File("/home/spy/dev/playengine/sound.wav"), 0.4f));
				getComponent(SoundComponent.class).shouldPlay = true;

				var trigger = new ColliderComponent();
				trigger.height = -100;
				trigger.width = -150;
				trigger.layer = 1;
				this.addComponent(trigger);

				var script = new KillableEntity();

				
				script.dropping.add(new Wood());
				this.addComponent(new ScriptComponent(script));
				this.transform.worldScale = new Vector2(200,200);
		}
		
}
