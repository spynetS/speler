package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;
import com.example.ecs.*;

public class Scene extends JPanel
{
	private RenderSystem renderer;
	private ECS ecs;
	private Camera camera;
		
	public Scene(ECS ecs) {
			this.ecs = ecs;
			this.setBackground(Color.CYAN);
			this.camera = new Camera(0,0,1);
	}

    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Toolkit.getDefaultToolkit().sync();
		
		renderer = new RenderSystem(ecs, (Graphics2D) g,camera, this.getWidth(), this.getHeight());
        renderer.render();
    }

	public RenderSystem getRenderer() {
		return renderer;
	}

	public void setRenderer(RenderSystem renderer) {
		this.renderer = renderer;
	}

	public ECS getEcs() {
		return ecs;
	}

	public void setEcs(ECS ecs) {
		this.ecs = ecs;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
		
}
