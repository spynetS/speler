package com.github.spynets.speler.animations;

import com.github.spynets.speler.SerializableComponent;
import com.github.spynets.speler.animations.AnimationListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents an animation consisting of multiple keyframes.
 *
 * <p>Each keyframe specifies a value at a particular point in time.
 * The animation interpolates between consecutive keyframes and applies
 * the resulting value to a target {@link Animatable}.
 *
 * <p>An {@code AnimationTrack} can be used by an {@code AnimationComponent}
 * to animate an object as part of the entity-component system.
 *
 * <p>For automatic time tracking, consider using {@code TimeTrackedAnimation}.
 *
 * @param <T> the type of values animated by this track
 */

public abstract class AnimationTrack<T> implements SerializableComponent {
  
    public static class Keyframe<T> implements SerializableComponent {
        public float time;
        public T value;

        public Keyframe(float time, T value) {
            this.time = time;
            this.value = value;
        }

        @Override
        public String toString() {
            return (String)value;
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
    private List<AnimationListener> listeners = new ArrayList();

    public AnimationTrack(List<Keyframe<T>> keyframes, Animatable<T> target, boolean loop) {
        this.keyframes = keyframes;
        this.target = target;
        this.loop = loop;
    }

    public void addListener(AnimationListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Applies the value for the given point in time to the target.
     *
     * <p>The last keyframe marks where the track ends: its time is the total
     * duration, and when looping the cycle wraps before reaching it. A track
     * of N visible values therefore needs N+1 keyframes, the last acting as a
     * terminator that supplies the duration of the one before it.
     *
     * @param elapsedTime seconds since the animation started
     */
    public void apply(float elapsedTime) {
        if (keyframes.size() == 0) return;

        if(elapsedTime == 0) {
            this.animationStarted();
        }

        float totalDuration = keyframes.get(keyframes.size() - 1).time;

        if (elapsedTime >= totalDuration) {
            this.animationEnded();
        }
        // A single keyframe, or one sitting at time zero, has no span to walk
        // across; apply it directly rather than dividing by a zero duration.
        if (totalDuration <= 0f) {
            target.apply(keyframes.get(0).value);
            return;
        }
        float t = loop ? elapsedTime % totalDuration : Math.min(elapsedTime, totalDuration);

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


    protected void animationEnded(){
        for(AnimationListener listener : listeners) {
            listener.animationEnded();
        }
    }

    protected void animationStarted(){
        for(AnimationListener listener : listeners) {
            listener.animationStarted();
        }
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
