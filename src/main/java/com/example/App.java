package com.example;

import com.example.Game;
import com.example.animations.*;
import com.example.scripting.GameObject;
import com.example.scripting.Script;
import com.example.ecs.AnimationSystem;
import com.example.ecs.ECS.*;
import com.example.resources.*;


import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
				Game game = new Game();
				String path = "/home/spy/dev/playengine/Sprites/01-King Human/Run (78x58).png";


				// load the image into a sheet
				SpriteSheet sheet = new SpriteSheet(game.resourceManager.loadImage(path),78,58);
				// add the extracted tile into out resource manager and save the key to it
				// we give out spritecomponent the key later
				String[] keys = new String[8];
				keys[0] = game.resourceManager.registerImage(sheet.getFrame(0,0));
				keys[1] = game.resourceManager.registerImage(sheet.getFrame(0,1));
				keys[2] = game.resourceManager.registerImage(sheet.getFrame(0,2));
				keys[3] = game.resourceManager.registerImage(sheet.getFrame(0,3));
				keys[4] = game.resourceManager.registerImage(sheet.getFrame(0,4));
				keys[5] = game.resourceManager.registerImage(sheet.getFrame(0,5));
				keys[6] = game.resourceManager.registerImage(sheet.getFrame(0,6));
				keys[7] = game.resourceManager.registerImage(sheet.getFrame(0,7));
				

				GameObject gameObject2 = new GameObject(game.ecs);
				gameObject2.getComponent(Transform.class).x = 0;
				gameObject2.getComponent(Transform.class).y = 0;
				gameObject2.addComponent(new SpriteComponent(keys[0]));

				Animatable<Integer> transformAnim = new Animatable<Integer>() {
								@Override
								public void apply(Integer value) {
										System.out.println("UPDATE "+value);
										gameObject2.getComponent(SpriteComponent.class).image = keys[value];
								}
				};

				List<AnimationTrack.Keyframe<Integer>> frames =
						List.of(
										new AnimationTrack.Keyframe<>(0,0),
										new AnimationTrack.Keyframe<>(0.1f,1),
										new AnimationTrack.Keyframe<>(0.2f,2),
										new AnimationTrack.Keyframe<>(0.3f,3),
										new AnimationTrack.Keyframe<>(0.4f,4),
										new AnimationTrack.Keyframe<>(0.5f,5),
										new AnimationTrack.Keyframe<>(0.6f,6),
										new AnimationTrack.Keyframe<>(1.4f,7)										
										);
				
				TimeTrackedAnimation<Integer> anim = new TimeTrackedAnimation<>(frames, transformAnim, true);
				AnimationSystem animationSystem = new AnimationSystem();
				animationSystem.addTrack(anim);

				game.ecs.addSystem(animationSystem);
				
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
