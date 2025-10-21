package com.example.speler.ecs.components;

import java.util.UUID;

import com.example.speler.SerializableComponent;
import com.example.speler.ecs.ECS.Component;
import com.google.gson.JsonObject;

public class ParentComponent implements Component, SerializableComponent {
	public UUID parentId;

		public ParentComponent(){}
    public ParentComponent(UUID parentId) { this.parentId = parentId; }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("parentId", parentId != null ? parentId.toString() : "");
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        String idStr = obj.get("parentId").getAsString();
        parentId = idStr.isEmpty() ? null : UUID.fromString(idStr);
    }
}
