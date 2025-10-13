package com.example;

import java.awt.Graphics2D;

import com.example.*;
import com.example.ECS.Renderable;
import com.example.ECS.Transform;

public class RenderSystem {
    private ECS ecs;
    private Graphics2D g;

    public RenderSystem(ECS ecs, Graphics2D g) {
        this.ecs = ecs;
        this.g = g;
    }

    public void render() {
        for (int entityId : ecs.getEntities()) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            Renderable r = ecs.getComponent(entityId, Renderable.class);
            if (t != null && r != null) {
                g.drawOval(t.x,t.y,10,10);
            }
        }
    }
}
