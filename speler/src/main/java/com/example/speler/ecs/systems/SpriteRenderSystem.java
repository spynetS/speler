package com.example.speler.ecs.systems;

import java.util.*;

import com.example.speler.*;
import com.example.speler.ecs.*;
import com.example.speler.ecs.components.*;
import com.example.speler.render.Renderer;

public class SpriteRenderSystem implements RenderSystem {

    @Override
    public void render(ECS ecs, Renderer renderer, Camera camera, int w, int h) {

        renderer.begin(w, h);

        List<UUID> ordered = new ArrayList<>();

        for (UUID id : ecs.getEntities()) {

            Transform t = ecs.getComponent(id, Transform.class);
            SpriteComponent sprite = ecs.getComponent(id, SpriteComponent.class);
            Renderable r = ecs.getComponent(id, Renderable.class);

            if (t == null) continue;

            if (sprite != null) {
                if (sprite.order) ordered.add(id);
                else draw(id, ecs, renderer, camera, w, h);
            }

            if (r != null) {
                renderer.drawRect(
                        t.worldPosition,
                        t.worldScale,
                        t.worldRotation
                );
            }
        }

        ordered.sort(Comparator.comparing(
                id -> ecs.getComponent(id, Transform.class).position.y
        ));

        for (UUID id : ordered)
            draw(id, ecs, renderer, camera, w, h);

        renderer.end();
    }

    private void draw(
            UUID id,
            ECS ecs,
            Renderer renderer,
            Camera camera,
            int w,
            int h
    ) {
        Transform t = ecs.getComponent(id, Transform.class);
        SpriteComponent s = ecs.getComponent(id, SpriteComponent.class);

        renderer.drawSprite(
                s.image,
                t.worldPosition,
                t.worldRotation,
                t.worldScale,
                s.inverted
        );
    }
}
