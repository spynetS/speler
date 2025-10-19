package com.example.ecs.components;

import com.example.SerializableComponent;
import com.example.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class SpriteComponent implements Component, SerializableComponent {
    public String image;
    public boolean inverted = false;

    public SpriteComponent(String image) { this.image = image; }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("image", image);
        obj.addProperty("inverted", inverted);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        image = obj.get("image").getAsString();
        inverted = obj.get("inverted").getAsBoolean();
    }
}
