package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.scripting.GameObject;
import com.example.speler.swing.JGameWindow;


public class Main {

		public static Game game;// = new Game(new JGameWindow(), new MyScene());
		public static void main(String[] args) {

				game = new Game(new JGameWindow(), new MyScene());
				game.getWindow().setBlankCursor();
				game.run();
    }
}
