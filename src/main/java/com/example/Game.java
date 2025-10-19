package com.example;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.example.GameWindow;
import com.example.Scene;
import com.example.ecs.*;
import com.example.resources.*;
import com.example.resources.ResourceManager.Sprite;


public class Game implements Runnable {
	private boolean running = true;


		protected ResourceManager resourceManager;
		protected GameWindow window;
		protected Scene selectedScene;
		protected ECS ecs;

		public Game() {
				this.window = new GameWindow();
				this.ecs = new ECS();
				setSelectedScene(new Scene(ecs));

				// add systems
				ecs.addSystem(new ScriptSystem());
				ecs.addSystem(new AnimationSystem());
				ecs.addSystem(new ParentSystem());

				Sprite.game = this;
				
				resourceManager = new ResourceManager();
		}
				
		public void setSelectedScene(Scene scene) {
				this.selectedScene = scene;
				this.window.add(scene, BorderLayout.CENTER);
		}

		public void run() {
				final double FPS =144.0;
				final double TIME_PER_UPDATE = 1_000_000_000 / FPS; // nanoseconds per update
				long lastTime = System.nanoTime();
				double delta = 0;
				while (running) {
						long now = System.nanoTime();
						delta += (now - lastTime) / TIME_PER_UPDATE;
						lastTime = now;

						while (delta >= 1) {
								update(1/FPS); // update game logic
								delta--;
						}

						render(); // draw the current state
				}
		}
    
		
    protected void update(double delta) {
		// 60fps
				ecs.update((float)delta);
    }

	protected void render() {
		// repaint JPanel or use buffer strategy
		selectedScene.repaint();
	}


		public boolean isRunning() {
			return running;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

		public ResourceManager getResourceManager() {
			return resourceManager;
		}

		public void setResourceManager(ResourceManager resourceManager) {
			this.resourceManager = resourceManager;
		}

		public GameWindow getWindow() {
			return window;
		}

		public void setWindow(GameWindow window) {
			this.window = window;
		}

		public Scene getSelectedScene() {
			return selectedScene;
		}

		public ECS getEcs() {
			return ecs;
		}

		public void setEcs(ECS ecs) {
			this.ecs = ecs;
		}

		
}
