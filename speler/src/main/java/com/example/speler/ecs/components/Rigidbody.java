package com.example.speler.ecs.components;

import com.example.speler.SerializableComponent;
import com.example.speler.Vector2;
import com.example.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class Rigidbody implements Component, SerializableComponent {

		public Vector2 acceleration = new Vector2(0,1);
		public float friction = 0.98f;
		
		
		public Rigidbody(){}

		//TODO Serilize correct
    @Override
    public JsonObject serialize() {
		JsonObject obj = new JsonObject();

        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
    }
}
