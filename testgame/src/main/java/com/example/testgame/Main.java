
package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.ecs.components.*;
import com.example.speler.editor.Editor;
import com.example.speler.editor.Editor.MyScript;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
	public static void main(String[] args) {

			try{
					FlatDarculaLaf.setup();
			}
			catch(Exception e){}
			
			Game game = new Editor();

			//			game.setSelectedScene(new MyScene(game.getEcs()));
			try {
				game.getSelectedScene().loadScene("/home/spy/dev/playengine/scene.json");
			} catch (Exception e) {

				e.printStackTrace();
			}
			game.run();
    }


		
}
