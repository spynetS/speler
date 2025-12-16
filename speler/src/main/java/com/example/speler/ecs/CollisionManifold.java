package com.example.speler.ecs;

public class CollisionManifold {
    public boolean collides;

    public float normalX;         // Normal pointing from A -> B
    public float normalY;
    public float penetrationDepth;

    public CollisionManifold(boolean collides) {
        this.collides = collides;
    }

    public static CollisionManifold none() {
        return new CollisionManifold(false);
    }
}
