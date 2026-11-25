package com.github.spynets.speler.ecs.systems;

import com.github.spynets.speler.ecs.ECS;

public interface UpdateSystem {
		void update(ECS ecs, float deltaTime);
		void start(ECS ecs);
}
