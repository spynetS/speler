package com.example.speler.ecs.systems;

import java.util.*;

import com.example.speler.*;
import com.example.speler.ecs.*;
import com.example.speler.ecs.components.*;
import com.example.speler.ecs.components.ui.TextElement;
import com.example.speler.render.Renderer;

public class UiRenderSystem implements RenderSystem {

    @Override
    public void render(ECS ecs, Renderer renderer, Camera camera, int w, int h) {

				renderer.beginUI();
				for (UUID id : ecs.getEntities()) {
            Transform t = ecs.getComponent(id, Transform.class);
						TextElement te = ecs.getComponent(id, TextElement.class);
						if(te != null)
								renderer.drawUIText(te.text, t.position, te.fontSize); 
				}


				renderer.endUI();
		}
		
}
