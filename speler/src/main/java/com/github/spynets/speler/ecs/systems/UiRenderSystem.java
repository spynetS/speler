package com.github.spynets.speler.ecs.systems;

import java.util.*;

import com.github.spynets.speler.*;
import com.github.spynets.speler.ecs.*;
import com.github.spynets.speler.ecs.components.*;
import com.github.spynets.speler.ecs.components.ui.TextElement;
import com.github.spynets.speler.render.Renderer;

public class UiRenderSystem implements RenderSystem {

    @Override
    public void render(ECS ecs, Renderer renderer, int w, int h) {

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
