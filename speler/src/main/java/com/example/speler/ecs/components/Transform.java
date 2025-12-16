package com.example.speler.ecs.components;

import com.example.speler.SerializableComponent;
import com.example.speler.Vector2;
import com.example.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;


// TODO
// I want A vector to be in the transform
// I want it to 

public class Transform implements Component, SerializableComponent {
		public Vector2 position = new Vector2();
		public Vector2 scale = new Vector2();
    public float rotation = 0;

		public Vector2 worldPosition = new Vector2();
    public float worldRotation = 0;
		public Vector2 worldScale = new Vector2(100,100);


    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("position", position.serialize());
        obj.add("scale", scale.serialize());
				obj.add("worldPosition", worldPosition.serialize());
        obj.add("worldScale", worldScale.serialize());
        obj.addProperty("rotation", rotation);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        if (obj.has("position")) {
            position.deserialize(obj.getAsJsonObject("position"));
        }
        if (obj.has("scale")) {
            scale.deserialize(obj.getAsJsonObject("scale"));
        }
        if (obj.has("rotation")) {
            rotation = obj.get("rotation").getAsFloat();
        }
        if (obj.has("worldPosition")) {
            worldPosition.deserialize(obj.getAsJsonObject("worldPosition"));
        }
				if (obj.has("worldScale")) {
            worldScale.deserialize(obj.getAsJsonObject("worldScale"));
        }

		}
}
