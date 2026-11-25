package com.github.spynets.speler.ecs.components;

import com.github.spynets.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

import com.github.spynets.speler.SerializableComponent;

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
