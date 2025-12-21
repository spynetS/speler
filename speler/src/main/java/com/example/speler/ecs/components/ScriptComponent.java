package com.example.speler.ecs.components;

import com.example.speler.ecs.CollisionEvent;
import com.example.speler.ecs.listeners.*;
import com.example.speler.ecs.ECS.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.speler.*;
import com.example.speler.scripting.*;

import com.google.gson.*;

public class ScriptComponent implements Component, SerializableComponent {
		
		private Map<Class<?>, Script> scripts = new HashMap<>();

		public ScriptComponent(){}

		public ScriptComponent(Script script) {
				scripts.put(script.getClass(), script);
		}

		public void addScript (Script script)	{
				scripts.put(script.getClass(), script);
		}

		public void removeScript(Script script) { scripts.remove(script); }

		public Collection<Script> getScripts() { return List.copyOf(scripts.values()); }

		public <T> T getScript(Class<T> scriptClass) {
				return scriptClass.cast(scripts.get(scriptClass));
		}
		

		//TODO FIX THE SERILIZE AND DESZERILIZE
    @Override
    public JsonObject serialize() {
		JsonObject obj = new JsonObject();
				System.out.println("SERILIZE script");
				obj.addProperty("scriptName", this.scripts.get(0).getScriptName()); // or path
        return obj;
    }

    @Override
	public void deserialize(JsonObject obj) {
		String name = obj.get("scriptName").getAsString();
		scripts.add(ScriptManager.getScript(name));
	}

		
}
