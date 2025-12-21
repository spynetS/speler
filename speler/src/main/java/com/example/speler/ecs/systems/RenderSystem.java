package com.example.speler.ecs.systems;

import com.example.speler.ecs.Camera;
import com.example.speler.ecs.ECS;
import com.example.speler.render.Renderer;

public interface RenderSystem {

		public void render(ECS ecs, Renderer renderer, Camera camera, int w, int h);
		
}
