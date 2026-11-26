package com.github.spynets.speler;

import com.github.spynets.speler.debug.DebugInfo;
import com.github.spynets.speler.ecs.*;
import com.github.spynets.speler.ecs.systems.*;
import com.github.spynets.speler.resources.*;
import com.github.spynets.speler.resources.ResourceManager.Sprite;


public class Game implements Runnable {
		private boolean running = true;
    private int current_fps = 60;

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

				ecs.addSystem(new SoundSystem());
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
						window.setSelectedScene(scene);
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



				long lastFpsTime = System.nanoTime(); // for FPS counting
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
            // Update FPS once per second
            if (now - lastFpsTime >= 1_000_000_000L) {
                current_fps = frames;
                frames = 0;
                lastFpsTime += 1_000_000_000L;
            }

						// reset mouse pressed
						//						Input.setMousePressed(1000);
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
				getWindow().renderWindow();

        Runtime runtime = Runtime.getRuntime();

        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();

				getWindow().renderDebug(new DebugInfo(this.current_fps,
                                              0.0f,
                                              ecs.getEntities().size(),
                                              0,
                                              ecs.updateSystems.size(),
                                              0,
                                              0,
                                              usedMemory,
                                              maxMemory));
        
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
