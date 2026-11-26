package com.github.spynets.speler.render;

import java.awt.Color;

import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.ecs.Camera;

public interface Renderer {

    void setWindowSize(Vector2 size);
    Vector2 getWindowSize();
		
    void begin(int screenWidth, int screenHeight);

    void drawSprite(
                    Camera camera,
            String imageId,
            Vector2 position,
            float rotationDeg,
            Vector2 scale,
            boolean flipX
    );

    void drawRect(
                  Camera camera,
            Vector2 position,
            Vector2 size,
            float rotationDeg,
            Color color
    );

    void fillRect(
            Camera camera,
            Vector2 position,
            Vector2 size,
            float rotationDeg,
            Color color
    );
		
    void end();

    void beginUI();

    void drawUISprite(
            String imageId,
            Vector2 screenPosition,
            Vector2 size
    );

    void drawUIText(
            String text,
            Vector2 screenPosition,
            float fontSize
    );

    void drawUIRect(
            Vector2 screenPosition,
            Vector2 size
    );

    void endUI();
		
}
