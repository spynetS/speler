package com.example.speler;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.example.speler.GameWindow;
import com.example.speler.Scene;
import com.example.speler.ecs.*;
import com.example.speler.ecs.systems.*;
import com.example.speler.input.Input;
import com.example.speler.resources.*;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.ScriptManager;


public class Game implements Runnable {
		private boolean running = true;


		protected ResourceManager resourceManager;
		protected GameWindow window;
		protected Scene selectedScene;
		protected ECS ecs;

		public Game(GameWindow window) {
			init(window, null);
		}

		
		public Game(GameWindow window, Scene scene) {
			init(window, scene);
		}

		private void init(GameWindow window, Scene scene) {
				this.window = window;
				this.ecs = new ECS();
				// add systems
				ColliderSystem cs = new ColliderSystem(); // collider first because other systems needs them
				ecs.listeners.add(cs);
				ecs.addSystem(new RigidbodySystem());

				ecs.addSystem(new ParentSystem());
				ecs.addSystem(new ScriptSystem());
				ecs.addSystem(new AnimationSystem());
				ecs.addSystem(cs);

				Sprite.game = this;
				setSelectedScene(scene);
				resourceManager = new ResourceManager();
		}
				
		public void setSelectedScene(Scene scene) {
				if (scene == null)
						return;
				try{
						scene.setEcs(this.ecs);
						scene.start(this);
						this.selectedScene = scene;
				}catch(Exception e){
						e.printStackTrace();
				}
		}

		public void run() {
				ecs.start();

				final double FPS = 144.0;
				final double TIME_PER_UPDATE = 1_000_000_000 / FPS; // nanoseconds per update
				final double TIME_PER_FRAME = 1_000_000_000 / FPS;
				long lastTime = System.nanoTime();
				double delta = 0;

				long timer = System.currentTimeMillis(); // for FPS counting
				int frames = 0; // rendered frames

				while (running) {
						long now = System.nanoTime();
						delta += (now - lastTime) / TIME_PER_UPDATE;
						lastTime = now;

						while (delta >= 1) {
								update(1 / FPS); // update game logic
								delta--;
						}

						render(); // draw the current state
						frames++;
						// reset mouse pressed
						Input.setMousePressed(1000);
						// Sleep to cap render FPS
						long frameTime = System.nanoTime() - now;
						long sleepTime = (long) TIME_PER_FRAME - frameTime;
						if (sleepTime > 0) {
							try {
								Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}


				}
		}

    
		
    protected void update(double delta) {
				ecs.update((float)delta);
    }

		protected void render() {
			getWindow().validate();
			try{
					selectedScene.render();
			}
			catch(Exception e){
					e.printStackTrace();
					running = false;
			}
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
