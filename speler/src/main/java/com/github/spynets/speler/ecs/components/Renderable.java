package com.github.spynets.speler.ecs.components;

import com.github.spynets.speler.SerializableComponent;
import com.github.spynets.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class Renderable implements Component, SerializableComponent {
		public String sprite = "";

		public Renderable() {}
    public Renderable(String sprite) { this.sprite = sprite; }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("sprite", sprite);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        sprite = obj.get("sprite").getAsString();
    }
}
