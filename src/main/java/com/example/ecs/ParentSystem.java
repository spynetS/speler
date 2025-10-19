package com.example.ecs;

import java.util.UUID;
import java.util.concurrent.TransferQueue;

import com.example.ecs.components.ParentComponent;
import com.example.ecs.components.Transform;

public class ParentSystem implements UpdateSystem {


		@Override
		public void start() {
				// TODO Auto-generated method stub
		
		}

		@Override
		public void update(ECS ecs, float deltaTime) {
				for (UUID id : ecs.getEntities()) {
						ParentComponent pc = ecs.getComponent(id, ParentComponent.class);
						if (pc == null)
								continue;

			
						Transform child = ecs.getComponent(id, Transform.class);
						Transform parent = ecs.getComponent(pc.parentId, Transform.class);
						if (parent != null) {

								// Local offset
								float ox = child.x;
								float oy = child.y;

								// Parent world position
								float px = parent.worldX;
								float py = parent.worldY;

								// Apply parent rotation to offset
								float angleRad = (float) Math.toRadians(parent.worldRotation);
								float rotatedX = (float) (ox * Math.cos(angleRad) - oy * Math.sin(angleRad));
								float rotatedY = (float) (ox * Math.sin(angleRad) + oy * Math.cos(angleRad));

								// Child world position = parent position + rotated offset
								child.worldX = (int)(px + rotatedX);
								child.worldY = (int) (py + rotatedY);

								// Child world rotation = parent rotation + local rotation
								child.worldRotation = parent.worldRotation + child.rotation;

								// Optional: scale can be multiplied similarly
								child.worldW = parent.worldW * child.w;
								child.worldH = parent.worldH * child.h;
					
						}
			
				}
		
		}

		
}
