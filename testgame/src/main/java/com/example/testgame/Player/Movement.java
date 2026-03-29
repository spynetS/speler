package com.example.testgame.Player;

import com.example.speler.Vector2;
import com.example.speler.ecs.components.AnimationComponent;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.example.testgame.Main;

public class Movement extends Script {

	Rigidbody rb;
	AnimationComponent ac;
	SpriteComponent sc;

		float attackTime = 0;
		
		@Override
		public void start() {
				// TODO Auto-generated method stub
				super.start();
				rb = gameObject.getComponent(Rigidbody.class);
				ac = gameObject.getComponent(AnimationComponent.class);
				sc = gameObject.getComponent(SpriteComponent.class);

		}

		float strength = 20;
		float zoom = 0;
		@Override
		public void update(float deltatime) {
				super.update(deltatime);
				Main.game.getSelectedScene().getCamera().setPosition(gameObject.transform.position.getX(),gameObject.transform.position.getY());
				
				Vector2 dir = Vector2.zero;

				float scroll = Input.getScrollValue(); // e.g., -1 or 1 per tick
				float zoomFactor = 0.1f * zoom; // proportional to current zoom
				float targetZoom = zoom + scroll * zoomFactor;
				targetZoom = Math.max(0.1f, Math.min(targetZoom, 5.0f));
				zoom += (targetZoom - zoom) * 0.8f; // smoothing factor
				Main.game.getSelectedScene().getCamera().zoom = zoom;

				ac.currentTrack = 0;
				if (Input.isKeyDown(Keys.A)) {
						dir = dir.add(Vector2.left);
						ac.currentTrack = 1;
						sc.inverted = true;
				}
				if (Input.isKeyDown(Keys.D)) {
						dir = dir.add(Vector2.right);

						ac.currentTrack = 1;
						sc.inverted = false;

				}
				if (Input.isKeyDown(Keys.W)) {
						dir = dir.add(Vector2.up);
						ac.currentTrack = 3;

				}
				if (Input.isKeyDown(Keys.S)) {
						dir = dir.add(Vector2.down);
						ac.currentTrack = 2;											
				}

				rb.acceleration = rb.acceleration.add(dir.getNormalized().multiply(strength));
				

				if(Input.isMouseButtonPressed(Keys.LEFTCLICK)){
						Bullet bullet = new Bullet(Main.game.getEcs());
						bullet.transform.position = gameObject.transform.position.add(new Vector2(10,0));
						bullet.send();

						attackTime = 0.3f;
				}
				if(attackTime > 0){
						ac.currentTrack = 4;
						attackTime -= deltatime;
				}

		}
		
}
