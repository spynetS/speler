package com.example.speler.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

import javax.swing.JPanel;

import java.util.LinkedList;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.example.speler.*;
import com.example.speler.ecs.Camera;
import com.example.speler.input.Input;
import com.example.speler.render.Renderer;
import com.example.speler.resources.ResourceManager;

public class SwingRenderer implements Renderer {

    private Graphics2D g;
    private Camera camera;
    private int screenW, screenH;

    public SwingRenderer(Graphics2D g, Camera camera) {
        this.g = g;
        this.camera = camera;
    }

    @Override
    public void begin(int w, int h) {
        this.screenW = w;
        this.screenH = h;
    }

    @Override
    public void drawSprite(
            String imageId,
            Vector2 pos,
            float rot,
            Vector2 scale,
            boolean flipX
    ) {
        BufferedImage img = ResourceManager.getImage(imageId);

        int x = camera.worldToScreenX(pos.x, screenW);
        int y = camera.worldToScreenY(pos.y, screenH);
        int w = camera.worldToScreenSize((int) scale.x);
        int h = camera.worldToScreenSize((int) scale.y);

        if (flipX) w = -w;

        AffineTransform old = g.getTransform();

        g.translate(x, y);
        g.rotate(Math.toRadians(rot));
        g.drawImage(img, -w / 2, -h / 2, w, h, null);

        g.setTransform(old);
    }

    @Override
    public void drawRect(Vector2 pos, Vector2 size, float rot) {
        int x = camera.worldToScreenX(pos.x, screenW);
        int y = camera.worldToScreenY(pos.y, screenH);
        int w = camera.worldToScreenSize((int) size.x);
        int h = camera.worldToScreenSize((int) size.y);

        AffineTransform old = g.getTransform();

        g.translate(x, y);
        g.rotate(Math.toRadians(rot));
        g.fillRect(-w / 2, -h / 2, w, h);

        g.setTransform(old);
    }

		public Camera getCamera() {
				return camera;
		}

		public void setGraphics(Graphics2D g) {
				this.g = g;
		}

		@Override
		public void end() {
		}

}
