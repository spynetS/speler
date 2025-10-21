package com.example.speler.animations;

import com.example.speler.animations.*;

public class TimeTrackedAnimation<T> extends AnimationTrack<T> {
    private float elapsed = 0f;

    public TimeTrackedAnimation(java.util.List<Keyframe<T>> keyframes, Animatable<T> target, boolean loop) {
        super(keyframes, target, loop);
    }

    public void update(float deltaTime) {
        elapsed += deltaTime;
        apply(elapsed);
    }
}
