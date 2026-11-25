package com.github.spynets.speler;

import com.google.gson.JsonObject;

public interface SerializableComponent {
    JsonObject serialize();
    void deserialize(JsonObject data);
}
