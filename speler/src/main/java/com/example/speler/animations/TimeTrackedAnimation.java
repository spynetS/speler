package com.example.speler.animations;

/**
 * An animation track that automatically keeps track of elapsed time.
 *
 * <p>{@code TimeTrackedAnimation} extends {@link AnimationTrack} by
 * maintaining an internal timer. Instead of manually providing the
 * elapsed animation time, the caller only needs to provide the time
 * elapsed since the previous update (delta time).
 *
 * <p>This makes it suitable for use in a game loop, where animations
 * are updated once per frame.
 *
 * @param <T> the type of values animated by this track
 */
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
