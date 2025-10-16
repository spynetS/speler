package com.example.scripting;

import java.util.Map;
import java.util.UUID;

import com.example.ecs.ECS;
import com.example.ecs.ECS.*;



public class GameObject {

		public UUID id;
		ECS ecs;
		public final Transform transform = new Transform();

		public GameObject(ECS ecs){
				this.ecs = ecs;
				this.id = ecs.instantiate();
				this.ecs.addComponent(this.id,transform);
				this.ecs.addComponent(this.id,new Renderable(""));
		}
		
		public <T extends Component> void addComponent(T comp){
				ecs.addComponent(this.id,comp);
		}
		public <T extends Component> T getComponent(Class<T> componentClass){
				return ecs.getComponent(this.id,componentClass);
		}
		


}
