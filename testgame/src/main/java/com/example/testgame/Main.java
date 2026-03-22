package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.ecs.Camera;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.opengl.GLGameWindow;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.swing.JGameWindow;
import com.example.speler.swing.SwingRenderer;


public class Main {

		public static Game game;// = new Game(new JGameWindow(), new MyScene());
		public static void main(String[] args) {

				game = new Game(new JGameWindow(), new MyScene()); 
				game.getWindow().setBlankCursor();
				game.run();
    }
}
