package com.example.scripting;

import com.example.scripting.GameObject;
import com.example.ecs.ECS.Component;
import com.example.ecs.UpdateSystem;

public abstract class Script {

		public GameObject gameObject;

		public Script(GameObject gameObject){
				this.gameObject = gameObject;
		}
		
		public void update(float deltatime) {}
		public void start() {}
		
}
