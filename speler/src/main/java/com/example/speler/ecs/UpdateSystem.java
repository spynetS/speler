package com.example.speler.ecs;

public interface UpdateSystem {
		void update(ECS ecs, float deltaTime);
		void start();
}
