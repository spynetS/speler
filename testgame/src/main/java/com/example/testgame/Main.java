
package com.example.testgame;

import com.example.speler.Game;

import com.example.speler.Vector2;
import com.example.speler.ecs.systems.ColliderSystem;
import com.example.speler.editor.Editor;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.scripting.GameObject;
import com.example.testgame.MyScript;

import com.example.speler.ecs.components.*;
import com.example.speler.scripting.GameObject;


public class Main {
	public static void main(String[] args) {
			
			Game game = new Game();

			int w = 240;
			int o = -100;

			new Wall(game.getEcs(), new Vector2(0,0+o),new Vector2(w,40));
			new Wall(game.getEcs(), new Vector2(0,w+o),new Vector2(w,40));
			new Wall(game.getEcs(), new Vector2(-w/2,w/2+o),new Vector2(40,w));
			new Wall(game.getEcs(), new Vector2(w / 2, w / 2 + o), new Vector2(40, w));

						
			GameObject ob = new GameObject(game.getEcs());
			ob.transform.worldScale.x = 20;
			ob.transform.worldScale.y = 20;
			
			ob.addComponent(new ScriptComponent(new MyScript()));
			ColliderComponent c = new ColliderComponent();
			c.circle = true;
			ob.addComponent(c);
			ob.addComponent(new Rigidbody());


			
			GameObject ob1 = new GameObject(game.getEcs());
			ob1.transform.worldScale.x = 20;
			ob1.transform.worldScale.y = 20;
			ob1.transform.position.x = 50;
			//ob1.addComponent(new ScriptComponent(new MyScript()));
			ColliderComponent c1 = new ColliderComponent();
			c1.circle = true;
			ob1.addComponent(c1);
			ob1.addComponent(new Rigidbody());


			game.run();
    }

		static class Wall extends GameObject {
				public Wall(ECS ecs, Vector2 pos, Vector2 scale){
						super(ecs);
						transform.worldScale.x = (int)scale.x;
						transform.worldScale.y = (int)scale.y;
						transform.position.x = (int)pos.getX();
						transform.position.y = (int)pos.getY();
						addComponent(new ColliderComponent());
				}
		}
		
}
