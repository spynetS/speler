package com.github.spynets.speler.ecs.components;

import com.github.spynets.speler.SerializableComponent;
import com.github.spynets.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;
import com.github.spynets.speler.*;


public class SpriteComponent implements Component, SerializableComponent {
    public String image = "";
		public boolean inverted = false;
		public boolean order = true;

    /** will add to the transform so (0,1) is one unit above transform */
    public Vector2 offset = new Vector2(0,0);

		public SpriteComponent(){}
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
