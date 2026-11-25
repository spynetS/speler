package com.github.spynets.speler.ecs.components;

import com.github.spynets.speler.SerializableComponent;
import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class ColliderComponent implements Component, SerializableComponent {

		Vector2 offset = new Vector2();
		public float radius;   // used if circle
		public float width, height; // used if rectangle
		public boolean circle = false;

		public boolean isTrigger = false;
		public int layer = 0;
		
		public ColliderComponent(){}

		//TODO Serilize correct
    @Override
    public JsonObject serialize() {
				JsonObject obj = new JsonObject();
				obj.addProperty("circle", circle);
        return obj;
    }

    @Override
		public void deserialize(JsonObject obj) {
				this.circle = obj.get("circle").getAsBoolean();
    }
}
