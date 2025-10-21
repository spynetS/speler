package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.ecs.components.*;
import com.example.speler.editor.Editor;
import com.example.speler.editor.Editor.MyScript;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class Main {
	public static void main(String[] args) {


			Game game = new Editor();
			GameObject g = new GameObject(game.getEcs());
			g.addComponent(new SpriteComponent("/home/spy/Pictures/davve.png"));
			//			g.addComponent(new ScriptComponent(new MyScript()));

			game.run();
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
