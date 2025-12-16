package com.example.speler.ecs;

import java.util.UUID;

import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Transform;

public class CollisionEvent {
    public final UUID a;
    public final UUID b;

    public final Transform transformA;
    public final Transform transformB;

    public final ColliderComponent colliderA;
		public final ColliderComponent colliderB;

		public final ECS ecs;
		
    // optional: useful extra data
    public float penetrationDepth;
    public float normalX;
    public float normalY;

    public CollisionEvent(UUID a, UUID b,
                          Transform ta, ColliderComponent ca,
                          Transform tb, ColliderComponent cb,
													CollisionManifold manifold, ECS ecs) {
		this.a = a;
		this.b = b;
		this.transformA = ta;
		this.colliderA = ca;
		this.transformB = tb;
		this.colliderB = cb;

		this.ecs = ecs;

		this.normalX = manifold.normalX;
		this.normalY = manifold.normalY;
		this.penetrationDepth = manifold.penetrationDepth;
	}
}
