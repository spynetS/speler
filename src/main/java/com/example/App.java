package com.example;

import com.example.Game;
import com.example.scripting.GameObject;
import com.example.scripting.Script;
import com.example.ecs.ECS.*;
import com.example.resources.*;

import javax.swing.*;
import java.awt.*;

public class App 
{
    public static void main( String[] args )
    {
				Game game = new Game();
				String path = "/home/spy/dev/playengine/Sprites/10-Cannon/Shoot(44x28).png";


				// load the image into a sheet
				SpriteSheet sheet = new SpriteSheet(game.resourceManager.loadImage(path),44,28);
				// add the extracted tile into out resource manager and save the key to it
				// we give out spritecomponent the key later
				String[] keys = new String[4];
				keys[0] = game.resourceManager.registerImage(sheet.getFrame(0,0));
				keys[1] = game.resourceManager.registerImage(sheet.getFrame(0,1));
				keys[2] = game.resourceManager.registerImage(sheet.getFrame(0,2));
				keys[3] = game.resourceManager.registerImage(sheet.getFrame(0,3));
				

				
				for(int k =0; k < 4; k ++ ){
								GameObject gameObject2 = new GameObject(game.ecs);
								gameObject2.getComponent(Transform.class).x = k*90;
								//								gameObject2.getComponent(Transform.class).y = k*90;
								gameObject2.addComponent(new SpriteComponent(keys[ Math.abs(k) % 4]));
								//				gameObject2.addComponent(new ScriptComponent(new MyScript(gameObject2)));
				}
				

				//for(int i = -25; i < 25; i ++ ){
				//				for(int k = -25; k < 25; k ++ ){
				//								GameObject gameObject2 = new GameObject(game.ecs);
				//								gameObject2.getComponent(Transform.class).x = i*90;
				//								gameObject2.getComponent(Transform.class).y = k*90;
				//								gameObject2.addComponent(new SpriteComponent(keys[ Math.abs(i) % 3]));
				//								gameObject2.addComponent(new ScriptComponent(new MyScript(gameObject2)));
				//						}
				//		}
						
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
