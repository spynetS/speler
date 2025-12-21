package com.example.speler.ecs.systems;

import java.util.UUID;
import java.util.concurrent.TransferQueue;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ParentComponent;
import com.example.speler.ecs.components.Transform;

public class ParentSystem implements UpdateSystem {


		@Override
		public void start(ECS ecs) {
				// TODO Auto-generated method stub
		}

		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID id : ecs.getEntities()) {
						ParentComponent pc = ecs.getComponent(id, ParentComponent.class);
						Transform child = ecs.getComponent(id, Transform.class);
						if (pc == null) {
								// if we are parent we should set the world position to be the child position
								child.worldPosition = child.position;
							continue;
						}

						Transform parent = ecs.getComponent(pc.parentId, Transform.class);
						if (parent != null) {

								// Local offset1
								float ox = child.position.getX();
								float oy = child.position.getY();

								// Parent world position
								float px = parent.worldPosition.getX();
								float py = parent.worldPosition.getY();

								// Apply parent rotation to offset
								float angleRad = (float) Math.toRadians(parent.worldRotation);
								float rotatedX = (float) (ox * Math.cos(angleRad) - oy * Math.sin(angleRad));
								float rotatedY = (float) (ox * Math.sin(angleRad) + oy * Math.cos(angleRad));

								// Child world position = parent position + rotated offset
								child.worldPosition.setX((px + rotatedX));
								child.worldPosition.setY((py + rotatedY));

								// Child world rotation = parent rotation + local rotation
								child.worldRotation = parent.worldRotation + child.rotation;

								// Optional: scale can be multiplied similarly
								child.worldScale.setX(parent.worldScale.getX() * child.scale.getX());
								child.worldScale.setY(parent.worldScale.getY() * child.scale.getY());
					
						}
			
				}
		
		}

		
}
