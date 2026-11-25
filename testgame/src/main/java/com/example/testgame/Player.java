package com.example.testgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.speler.Vector2;
import com.example.speler.animations.Animatable;
import com.example.speler.animations.AnimationTrack;
import com.example.speler.animations.TimeTrackedAnimation;
import com.example.speler.animations.AnimationTrack.Keyframe;
import com.example.speler.ecs.components.AnimationComponent;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.input.Input;
import com.example.speler.input.Keys;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;

public class Player extends Script {

    SpriteComponent comp = new SpriteComponent();
    AnimationComponent animationComponent = new AnimationComponent();
    BufferedImage image = ResourceManager.loadImage("./farm/characters/main character/walk and idle.png");
    SpriteSheet playerSheet = new SpriteSheet(image, 197/8, 72/3);

    float speed = 100;

    private void createWalkAnimation() throws Exception {
        Animatable<String> spriteAnimateable = new Animatable<String>() {
                @Override
                public void apply(String value) {
                    comp.image = value;
                }
            };

        ArrayList<Keyframe<String>> walkKeyframe = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            walkKeyframe.add(new Keyframe<>(i * 0.1f, Sprite.getSprite(playerSheet, 1, i)));
        }

        ArrayList<Keyframe<String>> idleKeyframe = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            idleKeyframe.add(new Keyframe<>(i * 0.3f, Sprite.getSprite(playerSheet, 0, i)));
        }

        List<AnimationTrack<?>> tracks = new ArrayList<>();
        tracks.add(new TimeTrackedAnimation<String>(walkKeyframe, spriteAnimateable, true));
        tracks.add(new TimeTrackedAnimation<String>(idleKeyframe, spriteAnimateable, true));
            
        animationComponent.currentTrack = 1;
        animationComponent.tracks = tracks;
    }

    @Override
    public void start() {
        try{

            createWalkAnimation();
            gameObject.addComponent(comp);
            gameObject.addComponent(animationComponent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltatime) {
        boolean walking = false;
        Vector2 walk = new Vector2(0,0);
        Main.game.getSelectedScene().getCamera().setPosition(gameObject.transform.position.getX(),gameObject.transform.position.getY());

        if(Input.isKeyDown(Keys.D)) {
            comp.inverted = true;
            animationComponent.currentTrack = 0;
            walk = walk.add(new Vector2(speed,0));
            walking = true;
        }
        else if (Input.isKeyDown(Keys.A)) {
            comp.inverted = false;
            animationComponent.currentTrack = 0;
            walk = walk.add(new Vector2(-speed,0));
            walking = true;
        }

        if (Input.isKeyDown(Keys.W)) {
            animationComponent.currentTrack = 0;
            walk = walk.add(new Vector2(0, -speed));
            walking = true;

        } else if (Input.isKeyDown(Keys.S)) {
            animationComponent.currentTrack = 0;
            walk = walk.add(new Vector2(0, speed));
            walking = true;
        }

        transform.position = transform.position.add(walk.getNormalized());
        if (!walking){
            animationComponent.currentTrack = 1;
        }

    }

}
