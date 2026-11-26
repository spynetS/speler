package com.github.spynets.speler.ecs.systems;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.github.spynets.speler.ecs.ECS;
import com.github.spynets.speler.ecs.components.ParentComponent;
import com.github.spynets.speler.ecs.components.Transform;

/***
	Derives every entity's world transform from its local one and its parent's
	world transform.

	position / rotation are the local values authored by game code; a root's
	world values are simply copies of them. worldScale is the exception: it is
	authored directly on roots (see {@link Transform}) and only derived for
	children, which multiply the parent's worldScale by their local scale.
 **/
public class ParentSystem implements UpdateSystem {


		@Override
		public void start(ECS ecs) {
				// TODO Auto-generated method stub
		}

		@Override
		public void update(ECS ecs, float deltaTime) {
				// Resolve parents before children regardless of where they sit in the
				// entity list, and collapse hierarchies of any depth in one pass. A
				// flat loop over getEntities() read a stale parent transform whenever
				// a child was created first, and advanced deep hierarchies only one
				// level per frame.
				Set<UUID> resolved = new HashSet<>();
				for (UUID id : ecs.getEntities()) {
						resolve(ecs, id, resolved, new HashSet<UUID>());
				}
		}

		/***
			Resolves one entity, walking up to its parent first.

			@param resolved entities already given a world transform this frame
			@param chain the ancestors currently being resolved, used to stop a
			             parent cycle from recursing until the stack runs out
		 **/
		private void resolve(ECS ecs, UUID id, Set<UUID> resolved, Set<UUID> chain) {
				if (id == null || resolved.contains(id)) return;

				Transform transform = ecs.getComponent(id, Transform.class);
				// Not every entity carries a Transform.
				if (transform == null) return;

				// Already on the stack: the parent links form a cycle. Leave this one
				// to the call that is still unwinding rather than recursing again.
				if (!chain.add(id)) return;

				ParentComponent pc = ecs.getComponent(id, ParentComponent.class);
				Transform parent = null;
				if (pc != null && pc.parentId != null && !pc.parentId.equals(id)) {
						resolve(ecs, pc.parentId, resolved, chain);
						// Null when the parent was removed or never had a Transform; such
						// an orphan is treated as a root rather than left stale.
						parent = ecs.getComponent(pc.parentId, Transform.class);
				}

				if (parent == null) {
						// Copy the values, never the reference. Aliasing worldPosition to
						// position means a later addChild() writes the world position
						// straight back into position - the local offset - so the child
						// drifts further from its parent every frame.
						transform.worldPosition.setX(transform.position.getX());
						transform.worldPosition.setY(transform.position.getY());
						transform.worldRotation = transform.rotation;
						// worldScale is authored directly on roots, so leave it alone.
				}
				else {
						float ox = transform.position.getX();
						float oy = transform.position.getY();

						// The parent's rotation turns the child's local offset with it.
						float angleRad = (float) Math.toRadians(parent.worldRotation);
						float cos = (float) Math.cos(angleRad);
						float sin = (float) Math.sin(angleRad);

						transform.worldPosition.setX(parent.worldPosition.getX() + ox * cos - oy * sin);
						transform.worldPosition.setY(parent.worldPosition.getY() + ox * sin + oy * cos);

						transform.worldRotation = parent.worldRotation + transform.rotation;

						transform.worldScale.setX(parent.worldScale.getX() * transform.scale.getX());
						transform.worldScale.setY(parent.worldScale.getY() * transform.scale.getY());
				}

				chain.remove(id);
				resolved.add(id);
		}


}
