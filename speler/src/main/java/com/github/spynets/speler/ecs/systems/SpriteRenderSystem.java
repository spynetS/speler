package com.github.spynets.speler.ecs.systems;

import java.awt.Color;
import java.util.*;

import com.github.spynets.speler.*;
import com.github.spynets.speler.ecs.*;
import com.github.spynets.speler.ecs.components.*;
import com.github.spynets.speler.render.Renderer;

public class SpriteRenderSystem implements RenderSystem {

    @Override
    public void render(ECS ecs, Renderer renderer, Camera camera, int w, int h) {

        renderer.begin(w, h);

        List<UUID> ordered = new ArrayList<>();

        for (UUID id : ecs.getEntities()) {

            Transform t = ecs.getComponent(id, Transform.class);
            SpriteComponent sprite = ecs.getComponent(id, SpriteComponent.class);
            Renderable r = ecs.getComponent(id, Renderable.class);
            ColliderComponent cp = ecs.getComponent(id, ColliderComponent.class);

            if (t == null) continue;

            if (sprite != null) {
                if (sprite.order) ordered.add(id);
                else draw(id, ecs, renderer);
            }

            if (r != null) {
                renderer.fillRect(
                        t.worldPosition,
                        t.worldScale,
                        t.worldRotation,
                        new Color(225,225,225)
                );
            }

            if (cp != null) {
                renderer.drawRect(
                        t.worldPosition,
                        t.worldScale.add(new Vector2(cp.width, cp.height)),
                        t.worldRotation,
                        new Color(0,225,0)
                );
            }
        }

        ordered.sort(Comparator.comparing(
																					id -> ecs.getComponent(id, Transform.class).position.getY()
        ));

        for (UUID id : ordered)
            draw(id, ecs, renderer);

        renderer.end();
    }

    private void draw(
            UUID id,
            ECS ecs,
            Renderer renderer) {
        Transform t = ecs.getComponent(id, Transform.class);
        SpriteComponent s = ecs.getComponent(id, SpriteComponent.class);

        renderer.drawSprite(
                s.image,
                t.worldPosition.add(s.offset),
                t.worldRotation,
                t.worldScale,
                s.inverted
        );
    }
}
