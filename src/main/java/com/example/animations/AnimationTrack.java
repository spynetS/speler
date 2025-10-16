package com.example.animations;

import java.util.List;

public class AnimationTrack<T> {
    public static class Keyframe<T> {
        public float time;
        public T value;

        public Keyframe(float time, T value) {
            this.time = time;
            this.value = value;
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
		if (keyframes.size() == 0)
			return;

		System.out.println(elapsedTime);
		
        float totalDuration = keyframes.get(keyframes.size() - 1).time;
        float t = loop ? (elapsedTime % totalDuration) : Math.min(elapsedTime, totalDuration);

        // Find current segment
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
            return (T) Float.valueOf( v1f +  (v2f -  v1f) * alpha);
        }
        // You could add more interpolation logic for Vector2, Color, etc.
        return v1;
    }
}
