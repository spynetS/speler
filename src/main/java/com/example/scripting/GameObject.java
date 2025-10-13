package com.example.scripting;

import java.util.Map;

import com.example.ECS;
import com.example.ECS.*;

public class GameObject {

		public int id;
		ECS ecs;

		public GameObject(ECS ecs){
				this.ecs = ecs;
				this.id = ecs.instanstiate();
				this.ecs.addComponent(this.id,new Transform());
				this.ecs.addComponent(this.id,new Renderable());
		}
		




}
