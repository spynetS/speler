package com.github.spynets.speler.ecs.systems;

import com.github.spynets.speler.ecs.Camera;
import com.github.spynets.speler.ecs.ECS;
import com.github.spynets.speler.render.Renderer;

public interface RenderSystem {

		void render(ECS ecs, Renderer renderer, int w, int h);
		
}
