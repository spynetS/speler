package com.example;

import com.example.Game;
import com.example.scripting.GameObject;
import com.example.scripting.Script;
import com.example.ecs.ECS.ScriptComponent;
import com.example.ecs.ECS.Transform;

import javax.swing.*;
import java.awt.*;

public class App 
{
    public static void main( String[] args )
    {
				Game game = new Game();
				GameObject gameObject = new GameObject(game.ecs);
				gameObject.addComponent(new ScriptComponent(new Script(gameObject) {
								@Override
								public void update(float deltatime) {
								    // TODO Auto-generated method stub
								    super.update(deltatime);
										Transform transform  = this.gameObject.getComponent(Transform.class);
										transform.x ++;
										transform.y ++;
								}
				}));

				game.run();
				
    }
}
