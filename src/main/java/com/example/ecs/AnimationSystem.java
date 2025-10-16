
package com.example.ecs;
import java.util.ArrayList;
import java.util.List;

import com.example.animations.*;

public class AnimationSystem implements UpdateSystem {
    private List<AnimationTrack<?>> activeTracks = new ArrayList<>();

    public void addTrack(AnimationTrack<?> track) {
        activeTracks.add(track);
    }

    public void update(ECS ecs, float deltaTime) {
        for (AnimationTrack<?> track : activeTracks) {
            // Each track should track its own elapsed time
            if (track instanceof TimeTrackedAnimation<?> timeTracked) {
                timeTracked.update(deltaTime);
            }
        }
    }

	@Override
	public void start() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'start'");
	}
}
