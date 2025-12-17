package com.example.testgame.Player;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.input.Input;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class Aimer extends Script {

		@Override
		public void start() {
			super.start();
			try{
					gameObject.addComponent(
																	new SpriteComponent(Sprite.getSprite("/home/spy/dev/playengine/testgame/sprites/aim.png")));
			} catch(Exception e){}
			
		}

		@Override
		public void update(float deltatime) {
				// TODO Auto-generated method stub
				super.update(deltatime);
				gameObject.transform.position = Input.getMousePosition();
		}
}
