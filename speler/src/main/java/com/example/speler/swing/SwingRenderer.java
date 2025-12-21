package com.example.speler.swing;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.example.speler.*;
import com.example.speler.ecs.Camera;
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

        // Round to whole pixels to prevent gaps between tiles
        int x = Math.round(camera.worldToScreenX(pos.getX(), screenW));
        int y = Math.round(camera.worldToScreenY(pos.getY(), screenH));
        int w = Math.round(camera.worldToScreenSize((int) scale.getX()));
        int h = Math.round(camera.worldToScreenSize((int) scale.getY()));

        if (flipX) w = -w;

        AffineTransform old = g.getTransform();

        g.translate(x, y);
        g.rotate(Math.toRadians(rot));
        g.drawImage(img, -w / 2, -h / 2, w, h, null);

        g.setTransform(old);
    }

    @Override
    public void drawRect(Vector2 pos, Vector2 size, float rot) {
        // Round to whole pixels to prevent gaps
        int x = Math.round(camera.worldToScreenX(pos.getX(), screenW));
        int y = Math.round(camera.worldToScreenY(pos.getY(), screenH));
        int w = Math.round(camera.worldToScreenSize((int) size.getX()));
        int h = Math.round(camera.worldToScreenSize((int) size.getY()));

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

	@Override
	public void beginUI() {
	}

	@Override
	public void drawUISprite(String imageId, Vector2 screenPosition, Vector2 size) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'drawUISprite'");
	}

	@Override
	public void drawUIText(String text, Vector2 screenPosition, float fontSize) {
			g.drawString(text, screenPosition.getX(), screenPosition.getY());
	}

	@Override
	public void drawUIRect(Vector2 screenPosition, Vector2 size) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'drawUIRect'");
	}

	@Override
	public void endUI() {
	}

}
