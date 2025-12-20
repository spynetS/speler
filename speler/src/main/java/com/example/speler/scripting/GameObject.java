package com.example.speler.scripting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.listeners.EntityListener;

public class GameObject  {

		public UUID id;
		ECS ecs;
		public final Transform transform;
		private ArrayList<Component> newComponents = new ArrayList<>();

		
		public GameObject(){
			this.transform = new Transform();
		}
		
		public GameObject(ECS ecs) {
			this.ecs = ecs;
			transform = new Transform();
			this.instantiate(ecs);
		}

		public GameObject(ECS ecs, UUID id){
				this.ecs = ecs;
				this.id = id;
				transform = this.ecs.getComponent(this.id,Transform.class);
		}	

		/**
			 To instantiate a this {@link GameObject}
		 */
		public void instantiate(ECS ecs){
				this.ecs = ecs;
				this.id = ecs.instantiate();
				this.ecs.addComponent(id, transform);
		}
		/**
			 To instantiate a new {@link GameObject}
		 */
		public void instantiate(GameObject gameObject){
				gameObject.ecs = ecs;
				gameObject.id = ecs.instantiate();
				this.ecs.addComponent(gameObject.id, gameObject.transform);
		}
		
		public <T extends Component> void addComponent(T comp) {
				if(ecs == null) return;
				
				ecs.addComponent(this.id, comp);
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
