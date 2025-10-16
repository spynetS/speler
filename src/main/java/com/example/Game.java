package com.example;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.example.GameWindow;
import com.example.Scene;
import com.example.ecs.*;
import com.example.resources.*;


public class Game implements Runnable {
		private boolean running = true;
		ResourceManager resourceManager;
		GameWindow window;
		Scene selectedScene;
		ECS ecs;

		public Game() {
				this.window = new GameWindow();
				this.ecs = new ECS();
				setSelectedScene(new Scene(ecs));

				// add systems
				ecs.addSystem(new ScriptSystem());
				ecs.addSystem(new AnimationSystem());

				
				resourceManager = new ResourceManager();
		}
				
		public void setSelectedScene(Scene scene) {
				this.selectedScene = scene;
				this.window.add(scene, BorderLayout.CENTER);
		}

		public void run() {
				final double FPS = 60.0;
				final double TIME_PER_UPDATE = 1_000_000_000 / FPS; // nanoseconds per update
				long lastTime = System.nanoTime();
				double delta = 0;

				while (running) {
						long now = System.nanoTime();
						delta += (now - lastTime) / TIME_PER_UPDATE;
						lastTime = now;

						while (delta >= 1) {
								update(); // update game logic
								delta--;
						}

						render(); // draw the current state
				}
		}
    
		
    private void update() {
				// 60fps
				ecs.update(0.016f);
    }

    private void render() {
        // repaint JPanel or use buffer strategy
        selectedScene.repaint();
    }

}
