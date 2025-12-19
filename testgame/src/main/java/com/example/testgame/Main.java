
package com.example.testgame;

import java.awt.BorderLayout;
import java.io.File;

import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.swing.JScene;
import com.example.speler.swing.JGameWindow;
import com.example.speler.Vector2;
import com.example.speler.ecs.systems.ColliderSystem;
import com.example.speler.editor.Editor;
import com.example.speler.resources.ResourceManager;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.ScriptManager;

import com.example.speler.ecs.components.*;
import com.example.speler.scripting.GameObject;


public class Main {

		public static Game game = new Game(new JGameWindow(), new MyScene());
		
		public static void main(String[] args) {

			//game.setSelectedScene(new MyScene(game.getEcs()));			
				game.run();
    }
}
