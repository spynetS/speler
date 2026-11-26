package com.github.spynets.speler.tiled;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapeditor.core.Animation;
import org.mapeditor.core.Frame;
import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.MapObject;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import com.github.spynets.speler.Game;
import com.github.spynets.speler.Vector2;
import com.github.spynets.speler.animations.Animatable;
import com.github.spynets.speler.animations.AnimationTrack.Keyframe;
import com.github.spynets.speler.animations.TimeTrackedAnimation;
import com.github.spynets.speler.ecs.components.AnimationComponent;
import com.github.spynets.speler.ecs.components.SpriteComponent;
import com.github.spynets.speler.ecs.components.Transform;
import com.github.spynets.speler.resources.ResourceManager;
import com.github.spynets.speler.scripting.GameObject;


public class LevelLoader {
    Game game = null;

    // org.mapeditor.core.Map is the Tiled map, so the lookup table has to name
    // java.util.Map explicitly.
    private final java.util.Map<Tile, String> spriteKeys = new HashMap<>();

    private ObjectHandler objectHandler;
    private Map map;

    public LevelLoader(Game game) {
        this.game = game;
    }

    public void setObjectHandler(ObjectHandler listener) {
        this.objectHandler = listener;
    }


    public void load(String mapPath) throws Exception {

        TMXMapReader reader = new TMXMapReader();
        // libtiled reads the tileset images itself and cuts them into per tile
        // bitmaps, resolving the paths relative to the .tmx/.tsx, so there is
        // nothing to preload here.
        this.map = reader.readMap(mapPath);

        int size = 50;
        for(MapLayer layer: map.getLayers()){

            if (layer instanceof TileLayer) {
                TileLayer tiled = (TileLayer)layer;
                for(int y = 0; y < map.getHeight(); y ++ ){
                    for(int x = 0; x < map.getWidth(); x ++ ){
                        Tile tile = tiled.getTileAt(x, y);
                        if(tile == null) continue;

                        GameObject ob = new GameObject(game.getEcs());

                        SpriteComponent spriteComponent = new SpriteComponent();
                        spriteComponent.order = false;
                        ob.addComponent(spriteComponent);

                        Animation animation = animationOf(tile);
                        if (animation != null) {
                            ob.addComponent(animationFor(tile, animation, spriteComponent));
                        } else {
                            spriteComponent.image = spriteFor(tile);
                        }


                        ob.transform.worldScale = new Vector2(size,size);
                        ob.transform.position = new Vector2((x-map.getWidth()/2)*size,(y-map.getHeight()/2)*size);
                        //ob.transform.position = new Vector2((x)*(size),(y)*size);
                    }
                }
            }
            else if (layer instanceof ObjectGroup) {
                ObjectGroup olayer = (ObjectGroup) layer;
                for( MapObject obj : olayer.getObjects()) {
                    if (objectHandler == null){
                        throw new Exception("No object handler was here");
                    }
                    // Point and text objects carry no size, so libtiled hands
                    // back null rather than 0 for those.
                    double width = obj.getWidth() == null ? 0d : obj.getWidth();
                    double height = obj.getHeight() == null ? 0d : obj.getHeight();

                    Vector2 position = tiledToWorld((float)(obj.getX() + width / 2d),
                                                    (float)(obj.getY() + height / 2d),
                                                    map);

                    Transform trans = new Transform();
                    trans.position = position.multiply(new Vector2(1,-1));

                    if(obj.getWidth() == null) {
                        trans.worldScale = new Vector2(100,100);
                    } else {
                        trans.worldScale = tiledSizeToWorld((float) width,
                                                            (float) height,
                                                            map);
                    }

                    LevelObject lobject = new LevelObject(trans, obj);

                    objectHandler.instaniateObject(lobject);
                }
            }
        }

    }


    /***
        Returns the tile's animation, or null when it is a still tile. An empty
        frame list counts as still: libtiled will hand back an Animation for a
        tile that was animated in the editor and then had its frames removed.
     **/
    private static Animation animationOf(Tile tile) {
        Animation animation = tile.getAnimation();
        if (animation == null) return null;
        List<Frame> frames = animation.getFrame();
        if (frames == null || frames.isEmpty()) return null;
        return animation;
    }


    /***
        Builds a looping track that swaps the sprite component's image on every
        frame boundary, using the per frame durations authored in Tiled.
     **/
    private AnimationComponent animationFor(Tile tile, Animation animation, SpriteComponent target) throws Exception {
        ArrayList<Keyframe<String>> keyframes = new ArrayList<>();

        float time = 0f;
        for (Frame frame : animation.getFrame()) {
            // Frames reference tiles by their local id within the same tileset.
            Tile frameTile = tile.getTileSet().getTile(frame.getTileid());
            if (frameTile == null) {
                throw new IllegalStateException(
                        "Animation frame references missing tile id " + frame.getTileid());
            }
            keyframes.add(new Keyframe<>(time, spriteFor(frameTile)));
            // Tiled stores frame durations in milliseconds, and leaves the
            // attribute off for the editor's default of 100ms.
            time += (frame.getDuration() == null ? 100 : frame.getDuration()) / 1000f;
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

    public Vector2 tiledSizeToWorld(float width, float height, Map map) {
        float scaleX = 50f / map.getTileWidth();
        float scaleY = 50f / map.getTileHeight();

        return new Vector2(
                           width * scaleX,
                           height * scaleY
                          );
    }

    public Vector2 tiledToWorld(float x, float y, Map map) {
        float scale = 50f / map.getTileWidth();

        float mapWidth = map.getWidth() * 50f;
        float mapHeight = map.getHeight() * map.getTileHeight() * scale;

        return new Vector2(
                           x * scale - mapWidth / 2f,
                           mapHeight / 2f - y * scale
                          );
    }


    /***
        Returns the resource key the renderer draws this tile by. libtiled has
        already sliced the tile out of its tileset image, so the bitmap only
        needs registering.
     **/
    private String spriteFor(Tile tile) throws Exception {
        String key = spriteKeys.get(tile);
        if (key == null) {
            // Registering a sprite re-encodes it to hash its pixels, so do it
            // once per distinct tile rather than once per tile placement.
            //
            // Never route an animated tile through here: AnimatedTile overrides
            // getImage() to return whichever frame is current by wall clock, so
            // its key would depend on when it was loaded. Animated tiles are
            // resolved frame by frame in animationFor instead.
            BufferedImage image = tile.getImage();
            if (image == null) {
                throw new IllegalStateException("Tile " + tile.getId() + " has no image");
            }
            key = ResourceManager.registerImage(image);
            spriteKeys.put(tile, key);
        }
        return key;
    }

}
