package com.example.ecs;

import java.awt.Graphics2D;

public interface RenderSystem {

		public void render(ECS ecs, Graphics2D g2, Camera camera, int screenWidth, int screenHeight);
}
