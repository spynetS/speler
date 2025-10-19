package com.example;

import com.google.gson.JsonObject;

public interface SerializableComponent {
    JsonObject serialize();
    void deserialize(JsonObject data);
}
