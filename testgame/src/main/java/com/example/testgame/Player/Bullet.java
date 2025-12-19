package com.example.testgame.Player;

import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.input.Input;
import com.example.speler.scripting.GameObject;

public class Bullet extends GameObject {

	public Bullet(ECS ecs) {
		super(ecs);
		transform.worldScale = new Vector2(10, 2);
		addComponent(new Renderable());
		var c = new ColliderComponent();
		c.isTrigger = true;
		c.layer = 1;
		addComponent(c);
	}

	public void send() {
		var r = new Rigidbody(false);
		Vector2 direction = this.transform.position.lookAt(Input.getMousePosition());
			r.acceleration = new Vector2(direction.multiply(1000));
			transform.worldRotation = direction.getAngle();
			
			r.friction = new Vector2(0.99999f, 0.99999f);
			addComponent(r);
	}
}
