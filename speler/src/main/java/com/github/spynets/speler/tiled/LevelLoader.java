package com.github.spynets.speler.tiled;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledLayer;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledObject;
import org.tiledreader.TiledObjectLayer;
import org.tiledreader.TiledTile;
import org.tiledreader.TiledTileLayer;
import org.tiledreader.TiledTileset;

import com.github.spynets.speler.Game;
import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.animations.Animatable;
import com.github.spynets.speler.animations.AnimationTrack.Keyframe;
import com.github.spynets.speler.animations.TimeTrackedAnimation;
import com.github.spynets.speler.ecs.components.AnimationComponent;
import com.github.spynets.speler.ecs.components.Renderable;
import com.github.spynets.speler.ecs.components.SpriteComponent;
import com.github.spynets.speler.resources.ResourceManager;
import com.github.spynets.speler.resources.ResourceManager.Sprite;
import com.github.spynets.speler.resources.SpriteSheet;
import com.github.spynets.speler.scripting.GameObject;


public class LevelLoader {
    Game game = null;

    private final Map<TiledTileset, SpriteSheet> sheets = new HashMap<>();
    private final Map<TiledTile, String> spriteKeys = new HashMap<>();

    private List<ObjectListener> objectListeners = new ArrayList<>();
    private ObjectListener objectHandler;

    public LevelLoader(Game game) {
        this.game = game;
    }

    public void setObjectHandler(ObjectListener listener) {
        this.objectHandler = listener;
    }


    public void load(String mapPath) throws Exception {

        FileSystemTiledReader reader = new FileSystemTiledReader();
        TiledMap map = reader.getMap(mapPath);

        for(TiledTileset tileset : map.getTilesets()){
            ResourceManager.loadImage(tileset.getImage().getSource());
        }
        
        int size = 50;
        for(TiledLayer layer: map.getTopLevelLayers()){

            if (layer instanceof TiledTileLayer) {
                TiledTileLayer tiled = (TiledTileLayer)layer;
                for(int y = 0; y < map.getHeight(); y ++ ){
                    for(int x = 0; x < map.getWidth(); x ++ ){
                        TiledTile tile = tiled.getTile(x, y);
                        if(tile == null) continue;

                        GameObject ob = new GameObject(game.getEcs());

                        SpriteComponent spriteComponent = new SpriteComponent();
                        spriteComponent.order = false;
                        ob.addComponent(spriteComponent);
                        //ob.addComponent(new Renderable());

                        if (tile.getNumAnimationFrames() > 0) {
                            ob.addComponent(animationFor(tile, spriteComponent));
                        } else {
                            spriteComponent.image = spriteFor(tile);
                        }
                        

                        ob.transform.worldScale = new Vector2(size,size);
                        //                        ob.transform.position = new Vector2((x-map.getWidth()/2)*size,(y-map.getHeight()/2)*size);
                        ob.transform.position = new Vector2((x)*size,(y)*size);
                    }
                }
            }
            else if (layer instanceof TiledObjectLayer) {
                TiledObjectLayer olayer = (TiledObjectLayer) layer;
                for( TiledObject obj : olayer.getObjects()) {
                    if (objectHandler == null){
                        throw new Exception("No object handler was here");
                    }
                    objectHandler.loadedObject(obj);
                }
            }
        }

    }

    
    

    /***
        Builds a looping track that swaps the sprite component's image on every
        frame boundary, using the per frame durations authored in Tiled.
     **/
    private AnimationComponent animationFor(TiledTile tile, SpriteComponent target) throws Exception {
        ArrayList<Keyframe<String>> keyframes = new ArrayList<>();

        float time = 0f;
        for (int i = 0; i < tile.getNumAnimationFrames(); i ++) {
            keyframes.add(new Keyframe<>(time, spriteFor(tile.getAnimationFrame(i))));
            // Tiled stores frame durations in milliseconds.
            time += tile.getAnimationFrameDuration(i) / 1000f;
        }
        // AnimationTrack reads the final keyframe's time as the point where the
        // cycle wraps and never displays its value, so close the list off with
        // the total length. Without this terminator the last frame is skipped
        // entirely, and a two frame animation never leaves frame 0.
        keyframes.add(new Keyframe<>(time, keyframes.get(0).value));

        AnimationComponent component = new AnimationComponent();
        component.tracks.add(new TimeTrackedAnimation<String>(keyframes, new Animatable<String>() {
                public void apply(String value) {
                    target.image = value;
                }
            }, true));
        component.currentTrack = 0;
        return component;
    }

    /***
        Slices a tile out of its tileset image and returns the resource key the
        renderer draws it by.
     **/
    private String spriteFor(TiledTile tile) throws Exception {
        String key = spriteKeys.get(tile);
        if (key == null) {
            // Registering a sprite re-encodes it to hash its pixels, so do it
            // once per distinct tile rather than once per tile placement.
            key = Sprite.getSprite(sheetFor(tile.getTileset()), tile.getTilesetY(), tile.getTilesetX());
            spriteKeys.put(tile, key);
        }
        return key;
    }

    /***
        Tilesets are sliced once and reused. Every tile of a map would otherwise
        rebuild the same sheet, and animated tiles would do it per frame.
     **/
    private SpriteSheet sheetFor(TiledTileset tileset) {
        return sheets.computeIfAbsent(tileset, ts -> {
            String path = ts.getImage().getSource();
            BufferedImage image = ResourceManager.loadImage(path);
            if (image == null) {
                throw new IllegalStateException("Could not load tileset image: " + path);
            }
            return new SpriteSheet(image, ts.getTileWidth(), ts.getTileHeight(),
                    ts.getSpacing(), ts.getMargin());
        });
    }

}
