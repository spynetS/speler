package com.example.speler.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.example.speler.JScene;
import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;

public class EditorScene extends JScene {

		EditorSystem system = new EditorSystem();
		Editor editor;
		public EditorScene(Editor editor,ECS ecs) {
			super(ecs);
			this.editor = editor;
		}

		Vector2 start = new Vector2();
		boolean d = false;		
		@Override
		public void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (Input.isMouseDown(Keys.RIGHTCLICK)) {
						if (!d) {
								start = Input.getMousePosition();
								d = true;
						}
						camera.x += start.subtract(Input.getMousePosition()).x;
						camera.y += start.subtract(Input.getMousePosition()).y;
					} else if (d) {
						d = false;
					}

				
				
				drawGrid((Graphics2D)g);
				system.render(editor,this.ecs, (Graphics2D) g, camera, this.getWidth(), this.getHeight());
		}
		private void drawGrid(Graphics2D g2) {
				int width = getWidth();
				int height = getHeight();

				// Grid settings
				int majorGridSize = 100;  // bigger lines
				int minorGridSize = 50;   // smaller lines
				Color minorColor = new Color(200, 200, 200, 100);
				Color majorColor = new Color(180, 180, 180, 150);

				// Compute world bounds of the screen
				float worldX0 = camera.screenToWorldX(0, width);
				float worldX1 = camera.screenToWorldX(width, width);
				float worldY0 = camera.screenToWorldY(0, height);
				float worldY1 = camera.screenToWorldY(height, height);

				// Compute start/end positions, snapping to grid
				int startX = (int) Math.floor(Math.min(worldX0, worldX1) / minorGridSize) * minorGridSize;
				int endX   = (int) Math.ceil(Math.max(worldX0, worldX1) / minorGridSize) * minorGridSize;
				int startY = (int) Math.floor(Math.min(worldY0, worldY1) / minorGridSize) * minorGridSize;
				int endY   = (int) Math.ceil(Math.max(worldY0, worldY1) / minorGridSize) * minorGridSize;

				// Draw vertical lines
				for (int x = startX; x <= endX; x += minorGridSize) {
						int screenX = camera.worldToScreenX(x, width);
						if (x % majorGridSize == 0) {
								g2.setColor(majorColor);
						} else {
								g2.setColor(minorColor);
						}
						g2.drawLine(screenX, 0, screenX, height);
				}

				// Draw horizontal lines
				for (int y = startY; y <= endY; y += minorGridSize) {
						int screenY = camera.worldToScreenY(y, height);
						if (y % majorGridSize == 0) {
								g2.setColor(majorColor);
						} else {
								g2.setColor(minorColor);
						}
						g2.drawLine(0, screenY, width, screenY);
				}

				// Optional: draw origin
				int originX = camera.worldToScreenX(0, width);
				int originY = camera.worldToScreenY(0, height);
				g2.setColor(Color.RED);
				g2.drawLine(originX - 10, originY, originX + 10, originY);
				g2.drawLine(originX, originY - 10, originX, originY + 10);
		}


}
