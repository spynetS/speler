package com.example.speler.ecs.systems;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.UUID;
import com.example.speler.*;

import com.example.speler.input.*;

import com.example.speler.ecs.components.*;
import com.example.speler.ecs.*;
import com.example.speler.resources.*;

public class SpriteRenderSystem implements RenderSystem {

    public void render(ECS ecs, Graphics2D g, Camera camera,int screenWidth, int screenHeight) {
        for (UUID entityId : ecs.getEntities()) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            Renderable r = ecs.getComponent(entityId, Renderable.class);
            SpriteComponent spriteComponent = ecs.getComponent(entityId, SpriteComponent.class);

            if (t != null && r != null) {
                int x = camera.worldToScreenX(t.worldPosition.x, screenWidth);
                int y = camera.worldToScreenY(t.worldPosition.y, screenHeight);
                int h = camera.worldToScreenSize((int)t.worldScale.y);
                int w = camera.worldToScreenSize((int)t.worldScale.x);
                g.fillRect(x - w / 2, y - h / 2, w, h);
            }

						if (t != null && spriteComponent != null) {
								BufferedImage image = ResourceManager.getImage(spriteComponent.image);

								int screenX = camera.worldToScreenX(t.worldPosition.x, screenWidth);
								int screenY = camera.worldToScreenY(t.worldPosition.y, screenHeight);
								int w = camera.worldToScreenSize((int)t.worldScale.x);
								int h = camera.worldToScreenSize((int)t.worldScale.y);

								// Handle horizontal flip
								int flip = spriteComponent.inverted ? -1 : 1;
								w = w * flip;

								// Save current transform
								AffineTransform oldTransform = g.getTransform();

								// Translate to center of the sprite
								g.translate(screenX, screenY);
								// Rotate around the center
								g.rotate(Math.toRadians(t.worldRotation));
								// Draw the image centered at (0,0) after translation
								g.drawImage(image, -w/2, -h/2, w, h, null);

								// Restore original transform
								g.setTransform(oldTransform);
						}


        }
    }
}

