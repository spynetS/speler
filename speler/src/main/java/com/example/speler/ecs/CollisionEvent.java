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

    // optional: useful extra data
    public float penetrationDepth;
    public float normalX;
    public float normalY;

    public CollisionEvent(UUID a, UUID b,
                          Transform ta, ColliderComponent ca,
                          Transform tb, ColliderComponent cb,
			CollisionManifold manifold) {
		this.a = a;
		this.b = b;
		this.transformA = ta;
		this.colliderA = ca;
		this.transformB = tb;
		this.colliderB = cb;

		this.normalX = manifold.normalX;
		this.normalY = manifold.normalY;
		this.penetrationDepth = manifold.penetrationDepth;
	}

		@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("CollisionEvent:\n");
    sb.append("  Entity A: ").append(a).append("\n");
    sb.append("    Transform: x=").append(transformA.worldX)
      .append(", y=").append(transformA.worldY).append("\n");
    if (colliderA != null) {
        sb.append("    Collider: ").append(colliderA).append("\n");
    }

    sb.append("  Entity B: ").append(b).append("\n");
    sb.append("    Transform: x=").append(transformB.worldX)
      .append(", y=").append(transformB.worldY).append("\n");
    if (colliderB != null) {
        sb.append("    Collider: ").append(colliderB).append("\n");
    }

    sb.append("  Collision Info:\n");
    sb.append("    Penetration Depth: ").append(penetrationDepth).append("\n");
    sb.append("    Normal: (").append(normalX).append(", ").append(normalY).append(")\n");

    return sb.toString();
}

		
}
