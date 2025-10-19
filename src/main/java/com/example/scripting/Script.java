package com.example.scripting;

import com.example.scripting.GameObject;
import com.example.ecs.ECS.Component;
import com.example.SerializableComponent;
import com.example.ecs.UpdateSystem;

public abstract class Script {

	public GameObject gameObject;
	String scriptName;

		public Script(GameObject gameObject, String scriptName){
			this.gameObject = gameObject;
			this.scriptName = scriptName;
		}
		
		public void update(float deltatime) {}

		public void start() {
		}

		public String getScriptName() {
		    return scriptName;
		}
		
}
