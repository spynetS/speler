package com.example.speler.ecs.components;

import com.example.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

import com.example.speler.SerializableComponent;

public class SoundListenerComponent implements Component, SerializableComponent {


		public SoundListenerComponent(){
		}

		public JsonObject serialize() {
				throw new UnsupportedOperationException("Unimplemented method 'serialize'");
		}
		
    public void deserialize(JsonObject data) {
				throw new UnsupportedOperationException("Unimplemented method 'deserilize'");
		}
}
