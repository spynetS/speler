package com.example.ecs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.UUID;
import com.example.*;

import com.example.input.*;

import com.example.ecs.ECS.*;
import com.example.ecs.*;
import com.example.resources.*;

public class RenderSystem {
    private ECS ecs;
    private Graphics2D g;
    private Camera camera;
    private int screenWidth, screenHeight;

    public RenderSystem(ECS ecs, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
        this.ecs = ecs;
        this.g = g;
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void render() {
        for (UUID entityId : ecs.getEntities()) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            Renderable r = ecs.getComponent(entityId, Renderable.class);
            SpriteComponent spriteComponent = ecs.getComponent(entityId, SpriteComponent.class);

            if (t != null && r != null) {
                int x = camera.worldToScreenX(t.x, screenWidth);
                int y = camera.worldToScreenY(t.y, screenHeight);
                int size = camera.worldToScreenSize(10);
                g.fillOval(x - size / 2, y - size / 2, size, size);
            }

			if (t != null && spriteComponent != null) {
				BufferedImage image = ResourceManager.getImage(spriteComponent.image);
				int x = camera.worldToScreenX(t.x, screenWidth);
				int y = camera.worldToScreenY(t.y, screenHeight);
				int w = camera.worldToScreenSize(t.w);
				int h = camera.worldToScreenSize(t.h);

				int flip = spriteComponent.inverted ? -1 : 1;
				w = w * flip;
				g.drawImage(image, x - w / 2, y - h / 2, w, h, null);
				g.drawString(String.valueOf(Input.getMousePosition().x),x - w / 2, y - h -10 / 2);
			}


        }
    }
}

