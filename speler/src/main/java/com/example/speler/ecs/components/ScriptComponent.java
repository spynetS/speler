package com.example.speler.ecs.components;

import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.CollisionListener;
import com.example.speler.ecs.ECS.*;

import java.util.UUID;

import com.example.speler.*;
import com.example.speler.scripting.*;

import com.google.gson.*;

public class ScriptComponent implements Component, SerializableComponent, CollisionListener {
		public Script script;

		public ScriptComponent(){}

		public ScriptComponent(Script script) {
			this.script = script;
		}

		
		@Override
		public void onCollision(CollisionEvent event) {
			if (script != null) {
					script.onCollision(event);
			}
		}
		
    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
				obj.addProperty("scriptName", script.getScriptName()); // or path
        return obj;
    }

    @Override
	public void deserialize(JsonObject obj) {
		String name = obj.get("scriptName").getAsString();
		script = ScriptManager.getScript(name);
	}

		
}
