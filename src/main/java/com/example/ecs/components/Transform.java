package com.example.ecs.components;

import com.example.SerializableComponent;
import com.example.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class Transform implements Component, SerializableComponent {
    public int x = 0, y = 0;
    public float w = 1, h = 1;
    public float rotation = 0;

    public int worldX, worldY;
    public float worldRotation = 0;
    public float worldW = 100, worldH = 100;

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", x);
        obj.addProperty("y", y);
        obj.addProperty("w", w);
        obj.addProperty("h", h);
        obj.addProperty("rotation", rotation);
        obj.addProperty("worldX", worldX);
        obj.addProperty("worldY", worldY);
        obj.addProperty("worldW", worldW);
        obj.addProperty("worldH", worldH);
        obj.addProperty("worldRotation", worldRotation);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        x = obj.get("x").getAsInt();
        y = obj.get("y").getAsInt();
        w = obj.get("w").getAsFloat();
        h = obj.get("h").getAsFloat();
        rotation = obj.get("rotation").getAsFloat();
        worldX = obj.get("worldX").getAsInt();
        worldY = obj.get("worldY").getAsInt();
        worldW = obj.get("worldW").getAsFloat();
        worldH = obj.get("worldH").getAsFloat();
        worldRotation = obj.get("worldRotation").getAsFloat();
    }
}
