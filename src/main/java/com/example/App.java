package com.example;

import com.example.Game;
import com.example.animations.*;
import com.example.scripting.GameObject;
import com.example.scripting.Script;
import com.example.ecs.AnimationSystem;
import com.example.ecs.ECS.*;
import com.example.input.Input;
import com.example.input.Keys;
import com.example.resources.*;
import com.example.resources.ResourceManager.Sprite;

import java.util.List;

public class App 
{
		public static Game game = new Game();



    public static void main( String[] args ) throws Exception
    {
				String path = "/home/spy/dev/playengine/Sprites/01-King Human/Run (78x58).png";


				// load the image into a sheet
				SpriteSheet sheet = new SpriteSheet(game.getResourceManager().loadImage(path),78,58);
				// add the extracted tile into out resource manager and save the key to it
				// we give out spritecomponent the key later
				String[] keys = new String[8];
				keys[0] = Sprite.getSprite(sheet,0,0);
				keys[1] = Sprite.getSprite(sheet,0,1);
				keys[2] = Sprite.getSprite(sheet,0,2);
				keys[3] = Sprite.getSprite(sheet,0,3);
				keys[4] = Sprite.getSprite(sheet,0,4);
				keys[5] = Sprite.getSprite(sheet,0,5);
				keys[6] = Sprite.getSprite(sheet,0,6);
				keys[7] = Sprite.getSprite(sheet,0, 7);


				GameObject gameObject1 = new GameObject(game.ecs);
				gameObject1.addComponent(new SpriteComponent(Sprite.getSprite(sheet,0,0)));
				
				GameObject gameObject2 = new GameObject(game.ecs);
				gameObject2.getComponent(Transform.class).x = 20;
				gameObject2.getComponent(Transform.class).y = 0;
				gameObject2.addComponent(new ScriptComponent(new MyScript(gameObject2)));
				gameObject2.addComponent(new SpriteComponent(keys[0]));
				gameObject2.getComponent(SpriteComponent.class).inverted = true;


				
				Animatable<Integer> transformAnim = new Animatable<Integer>() {
								@Override
								public void apply(Integer value) {
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
										new AnimationTrack.Keyframe<>(0.7f,7)										
										);
				
				TimeTrackedAnimation<Integer> anim = new TimeTrackedAnimation<>(frames, transformAnim, true);
				

				gameObject2.addComponent(new AnimationComponent(List.of(anim)));

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
			Transform transform = this.gameObject.getComponent(Transform.class);


			if (Input.isKeyDown(Keys.D)) {
				transform.x += 2;
									gameObject.getComponent(SpriteComponent.class).inverted = false;
			}
			
			if (Input.isKeyDown(Keys.A)) {
				transform.x -= 2;
				gameObject.getComponent(SpriteComponent.class).inverted = true;
					
			}

			
			if (Input.isKeyDown(Keys.W))
				transform.y+=-2;
			if (Input.isKeyDown(Keys.S))
					transform.y -= -2;
			
			App.game.selectedScene.getCamera().x = transform.x;
			App.game.selectedScene.getCamera().y = transform.y;

		}

	}
		
}
