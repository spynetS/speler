package com.example.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

import com.example.input.*;
import com.example.Vector2;
import com.example.ecs.Camera;
import com.example.ecs.ECS;
import com.example.ecs.RenderSystem;
import com.example.ecs.UpdateSystem;
import com.example.ecs.ECS.Transform;

public class EditorSystem implements RenderSystem, UpdateSystem {


		
		UUID selectedEntity = null;
		boolean draggingX = false, draggingY = false;
		Vector2 dragOffset = new Vector2(0,0);

		public void render(ECS ecs, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
			for (UUID id : ecs.getEntities()) {
				Transform t = ecs.getComponent(id, Transform.class);
				
				int objX = t.worldX;
				int objY = t.worldY;
				int objSize = 100;

				// Draw gizmo only for selected entity
				if (selectedEntity != null && selectedEntity.equals(id)) {
					int centerX = objX + objSize / 2;
					int centerY = objY + objSize / 2;
					int arrowSize = 40;

					g.fillOval(camera.worldToScreenX(centerX,screenWidth), camera.worldToScreenY(centerY,screenHeight), 10, 10);


					
					Vector2 mousePos = Input.getMousePosition();
					g.fillOval(camera.worldToScreenX((int)mousePos.x,screenWidth),camera.worldToScreenY((int)mousePos.y,screenHeight), 10, 10);
					
					// X-axis arrow
					g.setColor(Color.RED);
					g.drawLine(camera.worldToScreenX(centerX,screenWidth), camera.worldToScreenY(centerY,screenHeight), camera.worldToScreenX(centerX + arrowSize,screenWidth), camera.worldToScreenY(centerY,screenHeight));
					g.fillPolygon(
												new int[] { camera.worldToScreenX(centerX + arrowSize,screenWidth), camera.worldToScreenX( centerX + arrowSize - 10,screenWidth), camera.worldToScreenX(centerX + arrowSize - 10,screenWidth) },
												new int[] { camera.worldToScreenY(centerY,screenHeight),camera.worldToScreenY(centerY - 5,screenHeight), camera.worldToScreenY(centerY + 5,screenHeight) }, 3);

					// Y-axis arrow
					g.setColor(Color.GREEN);
					g.drawLine(camera.worldToScreenX(centerX,screenWidth), camera.worldToScreenY(centerY,screenHeight), camera.worldToScreenX(centerX,screenWidth), camera.worldToScreenY(centerY-arrowSize,screenHeight));


					g.fillPolygon(
												new int[] { camera.worldToScreenX(centerX,screenWidth), camera.worldToScreenX( centerX - 5,screenWidth), camera.worldToScreenX(centerX + 5,screenWidth) },
												new int[] { camera.worldToScreenY(centerY-arrowSize,screenHeight),camera.worldToScreenY(centerY - arrowSize + 10,screenHeight), camera.worldToScreenY(centerY - arrowSize + 10,screenHeight) }, 3);


					// Drag logic
					if (Input.isKeyDown(Keys.SPACE)) {
						// Start dragging if mouse is near X arrow
						if (!draggingX && Math.abs(mousePos.y - centerY) < 40 && mousePos.x > centerX
								&& mousePos.x < centerX + arrowSize) {
							draggingX = true;

							dragOffset.x = ((float)t.worldX) - mousePos.x;
							System.out.println(dragOffset);
						}

						// Start dragging if mouse is near Y arrow
						if (!draggingY && Math.abs(mousePos.x - centerX) < 10 && mousePos.y < centerY
								&& mousePos.y > centerY - arrowSize) {
							draggingY = true;
							dragOffset.y = objY - mousePos.y;
						}
					} else{
							//						System.out.println("reset");
						draggingX = false;
						draggingY = false;
					}

					// Update transform while dragging
					if (draggingX) {
						t.worldX = (int) Math.round(mousePos.x + dragOffset.x);
						System.out.println(mousePos.x + dragOffset.x);
					}

					if (draggingY)
						t.worldY = (int) (mousePos.y + dragOffset.y);
				}
			}
		}

		public void setSelectedEntity(UUID selectedEntity) {
		    this.selectedEntity = selectedEntity;
		}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ECS ecs, float deltaTime) {
		// TODO Auto-generated method stub
		
	}

}
