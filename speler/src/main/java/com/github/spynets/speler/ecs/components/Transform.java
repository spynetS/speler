package com.github.spynets.speler.ecs.components;

import com.github.spynets.speler.SerializableComponent;
import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;


// TODO
// I want A vector to be in the transform
// I want it to 

public class Transform implements Component, SerializableComponent {
		public Vector2 position = new Vector2();
		// Local scale is a multiplier against the parent's worldScale, so it has
		// to start at identity. At (0,0) every child collapses to zero size the
		// moment it is parented.
		public Vector2 scale = new Vector2(1,1);
    public float rotation = 0;

		public Vector2 worldPosition = new Vector2();
    public float worldRotation = 0;
		public Vector2 worldScale = new Vector2(100,100);

    public String tag;

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
