package com.example.speler;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.example.speler.GameWindow;
import com.example.speler.Scene;
import com.example.speler.ecs.*;
import com.example.speler.ecs.systems.*;
import com.example.speler.resources.*;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.ScriptManager;


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
				ColliderSystem cs = new ColliderSystem(); // collider first because other systems needs them
				ecs.listeners.add(cs);
				ecs.addSystem(cs);

				ecs.addSystem(new ParentSystem());
				ecs.addSystem(new ScriptSystem());
				ecs.addSystem(new AnimationSystem());

				Sprite.game = this;

				//				ScriptManager.loadScripts("scripts/");
				
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
						//selectedScene.requestFocus();
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
