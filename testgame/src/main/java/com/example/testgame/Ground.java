package com.example.testgame;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;

public class Ground extends GameObject{

		public Ground(ECS ecs, int type) {
		super(ecs);
		
		SpriteSheet spriteSheet = new SpriteSheet(
				ResourceManager.loadImage("/home/spy/dev/playengine/testgame/sprites/Outdoor/Fences.png"), 64 / 4,
				64 / 4);
		try {
				switch(type)
				{
					case 0:
						addComponent(new SpriteComponent(Sprite.getSprite(spriteSheet, 0, 1)));
						break;
				case 1:
						addComponent(new SpriteComponent(Sprite.getSprite(spriteSheet, 0, 2)));
						break;
				case 2:
						addComponent(new SpriteComponent(Sprite.getSprite(spriteSheet, 0, 3)));
						break;
				}

		}catch(Exception e){}
		var c = new ColliderComponent();
		c.height = -50;
		c.width = -50;
		addComponent(c);
	}
}
