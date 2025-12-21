package com.example.testgame.Resources;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;

public class Wood extends ItemScript {

		SpriteComponent spriteComponent;

		@Override
		public void start() {
				super.start();
				try{
						SpriteSheet sheet = new SpriteSheet(ResourceManager.loadImage("/home/spy/dev/playengine/testgame/sprites/Outdoor/Fences.png"), 64/4, 64/4);
						spriteComponent = new SpriteComponent(Sprite.getSprite(sheet,3, 0));
						gameObject.addComponent(spriteComponent);
						//transform.worldScale = new Vector2();

				}catch(Exception e){e.printStackTrace();}
		}

		@Override
		public void use(){
				player.transform.worldScale = new Vector2(200,200);
		}
		
}
