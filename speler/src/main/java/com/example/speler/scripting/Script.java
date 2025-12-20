package com.example.speler.scripting;

import com.example.speler.scripting.GameObject;
import com.example.speler.ecs.ECS.Component;
import com.example.speler.ecs.components.Transform;

import java.util.UUID;

import com.example.speler.SerializableComponent;

import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.listeners.CollisionListener;
import com.example.speler.ecs.systems.UpdateSystem;


public abstract class Script {

		public GameObject gameObject;
		public Transform transform;
		protected String scriptName;

		public Script() {
			
		}

		public Script(GameObject gameObject, String scriptName) {
				this.scriptName = scriptName;
				initScript(gameObject);
		}

		public void initScript(GameObject gameObject){
				this.gameObject = gameObject;
				this.transform = gameObject.transform;
		}
		
		/**
			 Update will run on every update cykle
		 */
		public void update(float deltatime) {
		}

		/**
			 Start will run after the latest update sequanes when the script was added
			 as a {@link com.example.speler.ecs.ECS.Component}
		 */
		public void start() {
		}
		/**
			 onTrigger will be run when ATLEAST a trigger collider is colliding
		 */
		public void onTrigger(CollisionEvent event) {
		}

		/**
			 onCollision will be run when 2 colliders are colliders
		*/
		public void onCollision(CollisionEvent event) {
		}
	
		public void setScriptName(String scriptName){
				this.scriptName = scriptName;
				ScriptManager.registerScript(scriptName, this.getClass());
		}
		public String getScriptName() {
				return scriptName;
		}


}

