package com.example.testgame.Player;

import com.example.speler.ecs.components.AnimationComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;


import java.util.LinkedList;
import java.util.List;
import com.example.speler.animations.Animatable;
import com.example.speler.animations.TimeTrackedAnimation;
import com.example.speler.animations.AnimationTrack;


public class AnimationFactory extends AnimationComponent {

		List<AnimationTrack<?>> animationTracks;
		float speed = 0.1f;
		float attackSpeed = 0.1f;
		
		public AnimationFactory(SpriteComponent spriteComponent, String path) {
				
		SpriteSheet spriteSheet = new SpriteSheet(
				ResourceManager.loadImage(
						path),
				192 / 6, 320 / 10);
		// IDLE
		List<AnimationTrack.Keyframe<String>> idleFrames = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			idleFrames.add(new AnimationTrack.Keyframe<String>(
					speed * i,
					ResourceManager.registerImage(spriteSheet.getFrame(0, i))));
		}
		// RUN
		List<AnimationTrack.Keyframe<String>> runSideFrames = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			runSideFrames.add(new AnimationTrack.Keyframe<String>(
					speed * i,
					ResourceManager.registerImage(spriteSheet.getFrame(4, i))));
		}
		// run down
		List<AnimationTrack.Keyframe<String>> runForwardFrames = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			runForwardFrames.add(new AnimationTrack.Keyframe<String>(
					speed * i,
					ResourceManager.registerImage(spriteSheet.getFrame(3, i))));
		}
		// run up
	List<AnimationTrack.Keyframe<String>> runBackWardsFrames = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			runBackWardsFrames.add(new AnimationTrack.Keyframe<String>(
					speed * i,
					ResourceManager.registerImage(spriteSheet.getFrame(5, i))));
		}

		List<AnimationTrack.Keyframe<String>> attack = new LinkedList<>();
		for (int i = 0; i < 4; i++) {
				attack.add(new AnimationTrack.Keyframe<String>(
																											 attackSpeed * i,
																											 ResourceManager.registerImage(spriteSheet.getFrame(6, i))));
		}

		
		animationTracks = new LinkedList<>();
		animationTracks.add(new TimeTrackedAnimation<String>(idleFrames, new SpriteAnimation(spriteComponent), true));
		animationTracks.add(new TimeTrackedAnimation<String>(runSideFrames, new SpriteAnimation(spriteComponent), true));
		animationTracks.add(new TimeTrackedAnimation<String>(runForwardFrames, new SpriteAnimation(spriteComponent), true));
		animationTracks.add(new TimeTrackedAnimation<String>(runBackWardsFrames, new SpriteAnimation(spriteComponent), true));
		animationTracks.add(new TimeTrackedAnimation<String>(attack, new SpriteAnimation(spriteComponent), true));
	}

	public AnimationComponent getAnimationComponent() {
		return new AnimationComponent(animationTracks);
	}

	static class SpriteAnimation implements Animatable<String> {
		SpriteComponent sp;

		public SpriteAnimation(SpriteComponent sp) {
			this.sp = sp;
			}
			public void apply(String arg0) {
				sp.image = arg0;
			}
	}
	
}
