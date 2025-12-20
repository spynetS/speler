package com.example.testgame;

import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.*;
import com.example.speler.scripting.GameObject;

public class Tile extends GameObject {

		public Tile(ECS ecs, String tile) {
			super(ecs);
			addComponent(new SpriteComponent(tile));
		}
		
}
