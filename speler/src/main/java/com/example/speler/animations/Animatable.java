package com.example.speler.animations;

/**
 * Represents an object that can receive values from an animation.
 *
 * <p>An {@code Animatable} acts as the target of an {@link AnimationTrack}.
 * When an animation is updated, the interpolated value is passed to
 * {@link #apply(Object)}.
 *
 * @param <T> the type of value that can be applied
 */
public interface Animatable<T> {
	void apply(T value);
}
