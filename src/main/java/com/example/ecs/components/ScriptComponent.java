package com.example.ecs.components;

import com.example.ecs.ECS.*;
import com.example.*;
import com.example.scripting.*;

import com.google.gson.*;

public class ScriptComponent implements Component, SerializableComponent {
		public Script script;

		public ScriptComponent(){}
    public ScriptComponent(Script script) { this.script = script; }

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
