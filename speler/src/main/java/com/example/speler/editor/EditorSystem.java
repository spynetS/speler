package com.example.speler.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

import com.example.speler.input.*;
import com.example.speler.scripting.GameObject;
import com.example.speler.Vector2;
import com.example.speler.ecs.Camera;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.systems.RenderSystem;
import com.example.speler.ecs.systems.UpdateSystem;
import com.example.speler.ecs.components.ParentComponent;
import com.example.speler.ecs.components.Transform;

public class EditorSystem implements RenderSystem, UpdateSystem {


		
		UUID selectedEntity = null;
		boolean draggingX = false, draggingY = false;
		Vector2 dragOffset = new Vector2(0,0);
		
		public void render(Editor editor, ECS ecs, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
			for (UUID id : ecs.getEntities()) {
				Vector2 mousePos = Input.getMousePosition();
				Transform t = ecs.getComponent(id, Transform.class);

				if (Input.isMouseDown() && rectRect((float)mousePos.x,(float) mousePos.y, 10, 10, t.worldX-t.worldW/2, t.worldY-t.worldH/2, t.worldW, t.worldH)) {
					editor.setSelectedGameObject(new GameObject(ecs, id));
				}
				renderArrows(ecs, id, g, camera, screenWidth, screenHeight);
			}
		}

		boolean rectRect(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y, float r2w, float r2h) {
				// are the sides of one rectangle touching the other?

				if (r1x + r1w >= r2x &&    // r1 right edge past r2 left
						r1x <= r2x + r2w &&    // r1 left edge past r2 right
						r1y + r1h >= r2y &&    // r1 top edge past r2 bottom
						r1y <= r2y + r2h) {    // r1 bottom edge past r2 top
						return true;
				}
				return false;
		}

		public void renderArrows(ECS ecs, UUID id, Graphics2D g, Camera camera, int screenWidth, int screenHeight) {
				Transform t = ecs.getComponent(id, Transform.class);
				ParentComponent parent = ecs.getComponent(id, ParentComponent.class);
				int objX = (int) t.worldX;
				int objY = (int) t.worldY;
				int objSize = 100;

				// Draw gizmo only for selected entity
				if (selectedEntity != null && selectedEntity.equals(id)) {
						int centerX = objX;// + objSize / 2;
						int centerY = objY;// + objSize / 2;
						int arrowSize = 40;

						g.fillOval(camera.worldToScreenX(centerX, screenWidth),
											 camera.worldToScreenY(centerY, screenHeight), 10, 10);

						Vector2 mousePos = Input.getMousePosition();
						g.fillOval(camera.worldToScreenX((int) mousePos.x, screenWidth),
											 camera.worldToScreenY((int) mousePos.y, screenHeight), 10, 10);

						// X-axis arrow
						g.setColor(Color.RED);
						g.drawLine(camera.worldToScreenX(centerX, screenWidth),
											 camera.worldToScreenY(centerY, screenHeight),
											 camera.worldToScreenX(centerX + arrowSize, screenWidth),
											 camera.worldToScreenY(centerY, screenHeight));
						g.fillPolygon(
													new int[] { camera.worldToScreenX(centerX + arrowSize, screenWidth),
																			camera.worldToScreenX(centerX + arrowSize - 10, screenWidth),
																			camera.worldToScreenX(centerX + arrowSize - 10, screenWidth) },
													new int[] { camera.worldToScreenY(centerY, screenHeight),
																			camera.worldToScreenY(centerY - 5, screenHeight),
																			camera.worldToScreenY(centerY + 5, screenHeight) },
													3);

						// Y-axis arrow
						g.setColor(Color.GREEN);
						g.drawLine(camera.worldToScreenX(centerX, screenWidth),
											 camera.worldToScreenY(centerY, screenHeight), camera.worldToScreenX(centerX, screenWidth),
											 camera.worldToScreenY(centerY - arrowSize, screenHeight));

						g.fillPolygon(
													new int[] { camera.worldToScreenX(centerX, screenWidth),
																			camera.worldToScreenX(centerX - 5, screenWidth),
																			camera.worldToScreenX(centerX + 5, screenWidth) },
													new int[] { camera.worldToScreenY(centerY - arrowSize, screenHeight),
																			camera.worldToScreenY(centerY - arrowSize + 10, screenHeight),
																			camera.worldToScreenY(centerY - arrowSize + 10, screenHeight) },
													3);

						// Drag logic
						if (Input.isMouseDown(Keys.LEFTCLICK)) {
								// Start dragging if mouse is near X arrow
								if (!draggingX && Math.abs(mousePos.y - centerY) < 10 && mousePos.x > centerX
										&& mousePos.x < centerX + arrowSize) {
										draggingX = true;

										dragOffset.x = ((float) t.worldX) - mousePos.x;
										System.out.println(dragOffset);
								}

								// Start dragging if mouse is near Y arrow
								if (!draggingY && Math.abs(mousePos.x - centerX) < 10 && mousePos.y < centerY
										&& mousePos.y > centerY - arrowSize) {
										draggingY = true;
										dragOffset.y = objY - mousePos.y;
								}
						} else {
								//						System.out.println("reset");
								draggingX = false;
								draggingY = false;
						}

						// Update transform while dragging
						if (draggingX) {
								if (parent == null)
										t.worldX = (int) Math.round(mousePos.x + dragOffset.x);
								else {
										t.x = (int) Math.round(mousePos.x + dragOffset.x);
								}

						}

						if (draggingY)
								if (parent == null)
										t.worldY = (int) Math.round(mousePos.y + dragOffset.y);
								else
										t.y = (int) Math.round(mousePos.y + dragOffset.y);

				}
			
		}
		
		public void setSelectedEntity(UUID selectedEntity) {
		    this.selectedEntity = selectedEntity;
		}

		@Override
		public void start(ECS ecs) {
				// TODO Auto-generated method stub
		
		}

		@Override
		public void update(ECS ecs, float deltaTime) {
				// TODO Auto-generated method stub
		
		}

		@Override
		public void render(ECS ecs, Graphics2D g2, Camera camera, int screenWidth, int screenHeight) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'render'");
		}

}
