
package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.ecs.components.*;
import com.example.speler.scripting.GameObject;

public class Main {
	public static void main(String[] args) {
			Game game = new Game();
		
				
			GameObject ob = new GameObject(game.getEcs());
			ob.addComponent(new ScriptComponent(new MyScript()));

			game.run();
    }


		
}
