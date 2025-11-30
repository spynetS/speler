
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

public class Main {
	public static void main(String[] args) {

			
			Game game = new Editor();

			int w = 240;
			int o = -100;
			
			new Wall(game.getEcs(), new Vector2(0,0+o),new Vector2(w,40));
			new Wall(game.getEcs(), new Vector2(0,w+o),new Vector2(w,40));
			new Wall(game.getEcs(), new Vector2(-w/2,w/2+o),new Vector2(40,w));
			new Wall(game.getEcs(), new Vector2(w / 2, w / 2 + o), new Vector2(40, w));

						
			GameObject ob = new GameObject(game.getEcs());
			ob.transform.worldW = 20;
			ob.transform.worldH = 20;
			
			ob.addComponent(new ScriptComponent(new MyScript()));
			ColliderComponent c = new ColliderComponent();
			c.circle = true;
			ob.addComponent(c);
			ob.addComponent(new Rigidbody());


			
			GameObject ob1 = new GameObject(game.getEcs());
			ob1.transform.worldW = 20;
			ob1.transform.worldH = 20;
			ob1.transform.worldX = 50;
			//			ob1.addComponent(new ScriptComponent(new MyScript()));
			ColliderComponent c1 = new ColliderComponent();
			c1.circle = true;
			ob1.addComponent(c1);
			ob1.addComponent(new Rigidbody());


			
			game.run();
    }

		static class Wall extends GameObject {
				public Wall(ECS ecs, Vector2 pos, Vector2 scale){
						super(ecs);
						transform.worldW = (int)scale.x;
						transform.worldH = (int)scale.y;
						transform.worldX = (int)pos.getX();
						transform.worldY = (int)pos.getY();
						addComponent(new ColliderComponent());
				}
		}
		
}
