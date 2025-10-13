package com.example.ecs;

public interface UpdateSystem {
		void update(ECS ecs, float deltaTime);
		void start();
}
