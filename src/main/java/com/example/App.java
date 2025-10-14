package com.example;

import com.example.Game;
import com.example.scripting.GameObject;
import com.example.scripting.Script;
import com.example.ecs.ECS.*;


import javax.swing.*;
import java.awt.*;

public class App 
{
    public static void main( String[] args )
    {
				Game game = new Game();
				String path = "/home/spy/dev/playengine/Sprites/08-Box/Idle.png";
				game.resourceManager.loadImage(path);
				
				for(int i = -25; i < 25; i ++ ){
						for(int k = -25; k < 25; k ++ ){
								GameObject gameObject2 = new GameObject(game.ecs);
								gameObject2.getComponent(Transform.class).x = i*90;
								gameObject2.getComponent(Transform.class).y = k*90;
								gameObject2.addComponent(new SpriteComponent(path));
						}
				}
						
				game.run();
				
    }


		static class MyScript extends Script {

				int vx = 1;
				int vy = 1;
				
				public MyScript(GameObject gameObject) {
						super(gameObject);
				}

				@Override
				public void update(float deltatime) {
						super.update(deltatime);
						Transform transform  = this.gameObject.getComponent(Transform.class);
						transform.x += vx*2;
						transform.y += vy*2;

						if(transform.x >= 200){
								vx = -1;
						}
						if(transform.x <= 0){
								vx = 1;
						}
						if(transform.y >= 200){
								vy = -1;
						}
						if(transform.y <= 0){
								vy = 1;
						}
						
				}

				
		}
		
}
