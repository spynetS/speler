package com.example.speler.ecs.systems;

import com.example.speler.ecs.ECS;

public interface UpdateSystem {
		void update(ECS ecs, float deltaTime);
		void start(ECS ecs);
}
