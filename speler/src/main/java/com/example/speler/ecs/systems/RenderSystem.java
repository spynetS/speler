package com.example.speler.ecs.systems;

import java.awt.Graphics2D;

import com.example.speler.ecs.Camera;
import com.example.speler.ecs.ECS;

public interface RenderSystem {

		public void render(ECS ecs, Graphics2D g2, Camera camera, int screenWidth, int screenHeight);
}
