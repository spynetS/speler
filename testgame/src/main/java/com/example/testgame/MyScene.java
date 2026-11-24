package com.example.testgame;


import com.example.speler.swing.SwingRenderer;
import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.scripting.GameObject;

public class MyScene extends Scene {

		@Override
		public void start (Game game) throws Exception {
				super.start(game);
        setRenderer(new SwingRenderer(this.camera));


        GameObject player = new GameObject(game.getEcs());
        player.addComponent(new ScriptComponent(new Player()));
		}
}
