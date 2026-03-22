package com.example.speler.render;

import com.example.speler.GameWindow;
import com.example.speler.Vector2;
import com.example.speler.ecs.Camera;

public interface Renderer {
		
    void begin(int screenWidth, int screenHeight);

    void drawSprite(
            String imageId,
            Vector2 position,
            float rotationDeg,
            Vector2 scale,
            boolean flipX
    );

    void drawRect(
            Vector2 position,
            Vector2 size,
            float rotationDeg
    );
		
    void end();
		Camera getCamera();

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
