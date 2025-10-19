package com.example.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;

import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;
import com.example.input.Input;
import com.example.input.Keys;

public class EditorScene extends Scene {

		EditorSystem system = new EditorSystem();
		
		public EditorScene(ECS ecs) {
				super(ecs);
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
				}
				else if (d) {
						d = false;
				}
			

				system.render(this.ecs,(Graphics2D) g, camera, this.getWidth(), this.getHeight());
						

		}
		
}
