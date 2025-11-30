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
        // obj.addProperty("x", x);
        // obj.addProperty("y", y);
        // obj.addProperty("w", w);
        // obj.addProperty("h", h);
        obj.addProperty("rotation", rotation);
        // obj.addProperty("worldX", worldX);
        // obj.addProperty("worldY", worldY);
        // obj.addProperty("worldW", worldW);
        // obj.addProperty("worldH", worldH);
        obj.addProperty("worldRotation", worldRotation);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        // x = obj.get("x").getAsInt();
        // y = obj.get("y").getAsInt();
        // w = obj.get("w").getAsFloat();
        // h = obj.get("h").getAsFloat();
        // rotation = obj.get("rotation").getAsFloat();
        // worldX = obj.get("worldX").getAsInt();
        // worldY = obj.get("worldY").getAsInt();
        // worldW = obj.get("worldW").getAsFloat();
        // worldH = obj.get("worldH").getAsFloat();
        worldRotation = obj.get("worldRotation").getAsFloat();
    }
}
