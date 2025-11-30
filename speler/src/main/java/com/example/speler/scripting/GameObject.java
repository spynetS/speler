package com.example.speler.scripting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.*;

public class GameObject {

		public UUID id;
		ECS ecs;
		public final Transform transform;

		public GameObject(ECS ecs) {
			this.ecs = ecs;
			this.id = ecs.instantiate();
			transform = new Transform();
			this.ecs.addComponent(this.id, transform);
		}

		public GameObject(ECS ecs, UUID id){
				this.ecs = ecs;
				this.id = id;
				transform = this.ecs.getComponent(this.id,Transform.class);
		}	
		
		public <T extends Component> void addComponent(T comp){
				ecs.addComponent(this.id,comp);
		}

		public <T extends Component> T getComponent(Class<T> componentClass) {
			return ecs.getComponent(this.id, componentClass);
		}

		public List<Component> getComponents() {
			return ecs.getComponents(this.id);
		}

		public void addChild(GameObject child) {
			child.addComponent(new ParentComponent(this.id));
		}

		public <T extends Component> void removeComponent(Class<T> compClass) {
				ecs.removeComponent(this.id,compClass);
		}

		public List<GameObject> getChildren() {
			List<GameObject> children = new ArrayList<>();
			for (UUID entity : ecs.getEntities()) {
				ParentComponent pc = ecs.getComponent(entity, ParentComponent.class);
				if (pc == null)
					continue;

				if (pc.parentId == this.id) {
					children.add(new GameObject(ecs, entity));
				}

			}
			return children;
		}

		public ECS getEcs() {
			return ecs;
		}

		public UUID getId() {
			return id;
		}
}
