package com.example.speler.scripting;

import com.example.speler.scripting.GameObject;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.SerializableComponent;
import com.example.speler.ecs.systems.UpdateSystem;

public abstract class Script {

	public GameObject gameObject;
	protected String scriptName;

		public Script(){}
		
		public Script(GameObject gameObject, String scriptName){
			this.gameObject = gameObject;
			this.scriptName = scriptName;
			ScriptManager.registerScript(scriptName, this.getClass());
		}
		
		public void update(float deltatime) {}

		public void start() {
		}

		public String getScriptName() {
		    return scriptName;
		}
		
}
