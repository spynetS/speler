package com.example.testgame;

import com.example.speler.Game;
import com.example.speler.swing.JGameWindow;


public class Main {

		public static Game game = new Game(new JGameWindow(), new MyScene());
		public static void main(String[] args) {
				game.getWindow().setBlankCursor();
				game.run();
    }
}
