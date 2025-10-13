package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import com.example.ecs.*;

public class Scene extends JPanel
{
	private RenderSystem renderer;
	private ECS ecs;
	public Scene(ECS ecs) {
			this.ecs = ecs;
		this.setBackground(Color.CYAN);
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer = new RenderSystem(ecs, (Graphics2D) g);
        renderer.render();
    }
		
}
