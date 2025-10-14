package com.example.ecs;

import java.awt.Graphics2D;
import java.util.UUID;
import com.example.*;
import com.example.ecs.ECS.Renderable;
import com.example.ecs.ECS.Transform;

public class RenderSystem {
    private ECS ecs;
    private Graphics2D g;

    public RenderSystem(ECS ecs, Graphics2D g) {
        this.ecs = ecs;
        this.g = g;
    }

    public void render() {
        for (UUID entityId : ecs.getEntities()) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            Renderable r = ecs.getComponent(entityId, Renderable.class);
            if (t != null && r != null) {
                g.fillOval(t.x,t.y,10,10);
            }
        }
    }
}
