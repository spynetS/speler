package com.github.spynets.speler.tiled;

import org.mapeditor.core.MapObject;

import com.github.spynets.speler.ecs.components.Transform;
import com.github.weisj.jsvg.nodes.Image;

public class LevelObject {

    public Transform transform;
    private MapObject original;

    public LevelObject(Transform transform,
                       MapObject original){

        this.transform = transform;
        this.original = original;
    }

	public Transform getTransform() {
		return transform;
	}

	public MapObject getOriginal() {
		return original;
	}


}
