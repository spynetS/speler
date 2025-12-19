package com.example.speler.animations;

import com.example.speler.SerializableComponent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;


/**
	 Use the TimeTrackedanimation if you dont want to track time yourself.
	 AnimationTrack is a animation of multiple keyFrames where
	 each keyframe do a apply on a animatable object.
	 AnimationTrack can be used by the animationComponent that has a list of
	 them and playes one.
 */
public class AnimationTrack<T> implements SerializableComponent {
    public static class Keyframe<T> implements SerializableComponent {
        public float time;
        public T value;

        public Keyframe(float time, T value) {
            this.time = time;
            this.value = value;
        }

        @Override
        public JsonObject serialize() {
            JsonObject obj = new JsonObject();
            obj.addProperty("time", time);
            // Assuming value is Float for simplicity; extend for Vector2, Color, etc.
            if (value instanceof Float f) obj.addProperty("value", f);
            return obj;
        }

        @Override
        public void deserialize(JsonObject obj) {
            time = obj.get("time").getAsFloat();
            if (value instanceof Float) value = (T) Float.valueOf(obj.get("value").getAsFloat());
        }
    }

    private List<Keyframe<T>> keyframes;
    private Animatable<T> target;
    private boolean loop;

    public AnimationTrack(List<Keyframe<T>> keyframes, Animatable<T> target, boolean loop) {
        this.keyframes = keyframes;
        this.target = target;
        this.loop = loop;
    }

    public void apply(float elapsedTime) {
        if (keyframes.size() == 0) return;

        float totalDuration = keyframes.get(keyframes.size() - 1).time;
        float t = loop ? (elapsedTime % totalDuration) : Math.min(elapsedTime, totalDuration);

        for (int i = 0; i < keyframes.size() - 1; i++) {
            Keyframe<T> k1 = keyframes.get(i);
            Keyframe<T> k2 = keyframes.get(i + 1);
            if (t >= k1.time && t <= k2.time) {
                float alpha = (t - k1.time) / (k2.time - k1.time);
                T value = interpolate(k1.value, k2.value, alpha);
                target.apply(value);
                return;
            }
        }
        target.apply(keyframes.get(keyframes.size() - 1).value);
    }

    @SuppressWarnings("unchecked")
    private T interpolate(T v1, T v2, float alpha) {
        if (v1 instanceof Float && v2 instanceof Float) {
            Float v1f = (Float) v1;
            Float v2f = (Float) v2;
            return (T) Float.valueOf(v1f + (v2f - v1f) * alpha);
        }
        // Extend for Vector2, Color, etc.
        return v1;
    }

    // Serialization for the whole track
    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("loop", loop);

        JsonArray array = new JsonArray();
        for (Keyframe<T> kf : keyframes) {
            array.add(kf.serialize());
        }
        obj.add("keyframes", array);
        return obj;
    }

    @Override
    public void deserialize(JsonObject obj) {
        loop = obj.get("loop").getAsBoolean();
        JsonArray array = obj.getAsJsonArray("keyframes");
        keyframes = new ArrayList<>();
        for (var e : array) {
            Keyframe<T> kf = new Keyframe<>(0f, null); // initialize placeholder
            kf.deserialize(e.getAsJsonObject());
            keyframes.add(kf);
        }
    }
}
