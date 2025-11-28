
package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.Vector2;
import com.example.speler.ecs.systems.ColliderSystem;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.scripting.GameObject;
import com.example.testgame.MyScript;

public class Main {
	public static void main(String[] args) {

			
			Game game = new Game();

			int w = 200;
			int o = -100;
			
			//			new Wall(game.getEcs(), new Vector2(0,0+o),new Vector2(w,1));
			//			new Wall(game.getEcs(), new Vector2(0,w+o),new Vector2(w,1));
			//			new Wall(game.getEcs(), new Vector2(-w/2,w/2+o),new Vector2(1,w));
			new Wall(game.getEcs(), new Vector2(w/2,w/2+o),new Vector2(1,w));
			
			
			GameObject ob = new GameObject(game.getEcs());
			ob.addComponent(new ScriptComponent(new MyScript()));
			ColliderComponent c = new ColliderComponent();
			c.circle = true;
			ob.addComponent(c);

			
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
