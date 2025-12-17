package com.example.speler.ecs.systems;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;

import com.example.speler.*;

import com.example.speler.input.*;


import com.example.speler.ecs.components.*;
import com.example.speler.ecs.*;
import com.example.speler.resources.*;

public class SpriteRenderSystem implements RenderSystem {

		private void renderSprite(UUID id, ECS ecs, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
			Transform t = ecs.getComponent(id, Transform.class);
			SpriteComponent spriteComponent = ecs.getComponent(id, SpriteComponent.class);
			BufferedImage image = ResourceManager.getImage(spriteComponent.image);

			int screenX = camera.worldToScreenX(t.worldPosition.x, screenWidth);
			int screenY = camera.worldToScreenY(t.worldPosition.y, screenHeight);
			int w = camera.worldToScreenSize((int) t.worldScale.x);
			int h = camera.worldToScreenSize((int) t.worldScale.y);

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
			g.drawImage(image, -w / 2, -h / 2, w, h, null);

			// Restore original transform
			g.setTransform(oldTransform);
	}
		
		public void render(ECS ecs, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
				List<UUID> ordered = new LinkedList<>();

				for (UUID entityId : ecs.getEntities()) {
					Transform t = ecs.getComponent(entityId, Transform.class);
					Renderable r = ecs.getComponent(entityId, Renderable.class);
					SpriteComponent spriteComponent = ecs.getComponent(entityId, SpriteComponent.class);
					ColliderComponent colliderComponent = ecs.getComponent(entityId, ColliderComponent.class);

					if (t == null)
						continue;

					if (t != null && r != null) {
						int x = camera.worldToScreenX(t.worldPosition.x, screenWidth);
						int y = camera.worldToScreenY(t.worldPosition.y, screenHeight);
						int h = camera.worldToScreenSize((int) t.worldScale.y);
						int w = camera.worldToScreenSize((int) t.worldScale.x);

						
						AffineTransform oldTransform = g.getTransform();

						// Translate to center of the sprite
						g.translate(x, y);
						// Rotate around the center
						g.rotate(Math.toRadians(t.worldRotation));
						// Draw the image centered at (0,0) after translation
						g.fillRect(-w / 2, - h / 2, w, h);
						// Restore original transform
						g.setTransform(oldTransform);
					}

					if (t != null && spriteComponent != null) {
							if(spriteComponent.order)
								ordered.add(entityId);
							else
									renderSprite(entityId, ecs, g, camera, screenWidth, screenHeight);
					}

					// DEBUG
					if (colliderComponent != null) {
						int x = camera.worldToScreenX(t.worldPosition.x, screenWidth);
						int y = camera.worldToScreenY(t.worldPosition.y, screenHeight);
						int h = camera.worldToScreenSize((int) (t.worldScale.y + colliderComponent.height));
						int w = camera.worldToScreenSize((int) (t.worldScale.x + colliderComponent.width));
						Color color = g.getColor();

						g.setColor(Color.GREEN);
						if (colliderComponent.circle)
							g.drawOval(x - w / 2, y - h / 2, w, h);
						else
							g.drawRect(x - w / 2, y - h / 2, w, h);
						g.setColor(color);
					}
				}

				ordered.sort(new Comparator<UUID>() {
								@Override
								public int compare(UUID o1, UUID o2) {
										Transform t1 = ecs.getComponent(o1, Transform.class);
										Transform t2 = ecs.getComponent(o2, Transform.class);
										
										return Float.compare(t1.position.y, t2.position.y);
								}
				});
				
				for (UUID id : ordered) {
						renderSprite(id, ecs, g, camera, screenWidth, screenHeight);
				}
				
    }
}

