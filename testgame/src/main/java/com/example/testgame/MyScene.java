package com.example.testgame;


import com.example.speler.Scene;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class MyScene extends Scene {

	public MyScene(ECS ecs) {
		super(ecs);

					
			///////////////////////////////////////////////////////////////////////////
			// GameObject g = new GameObject(ecs);																	 //
			// g.addComponent(new SpriteComponent("/home/spy/Pictures/davve.png"));	 //
			// g.addComponent(new ScriptComponent(new MyScript()));									 //
			///////////////////////////////////////////////////////////////////////////


	}

		public static class MyScript extends Script {
				@Override
				public void update(float deltatime) {
				    // TODO Auto-generated method stub
				    super.update(deltatime);
						gameObject.transform.worldX ++;
				}
		}

	
}
