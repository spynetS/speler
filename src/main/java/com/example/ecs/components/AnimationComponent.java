package com.example.ecs.components;

import java.util.ArrayList;
import java.util.List;

import com.example.SerializableComponent;
import com.example.animations.AnimationTrack;
import com.example.ecs.ECS.Component;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AnimationComponent implements Component, SerializableComponent {
    public List<AnimationTrack<?>> tracks;
    public int currentTrack;

    public AnimationComponent(List<AnimationTrack<?>> tracks) {
        this.tracks = tracks;
        currentTrack = 0;
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("currentTrack", currentTrack);
        JsonArray array = new JsonArray();
        for (AnimationTrack<?> track : tracks) {
            array.add(track.serialize()); // Assuming AnimationTrack also implements SerializableComponent
        }
        obj.add("tracks", array);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        currentTrack = obj.get("currentTrack").getAsInt();
        JsonArray array = obj.getAsJsonArray("tracks");
        tracks = new ArrayList<>();
        for (var e : array) {
            AnimationTrack<?> track = new AnimationTrack<>();
            track.deserialize(e.getAsJsonObject());
            tracks.add(track);
        }
    }
}
