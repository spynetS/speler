package com.example.testgame;

import java.awt.image.BufferedImage;
import java.io.File;

import org.tiledreader.*;

import com.example.speler.Game;
import com.example.speler.Scene;
import com.example.speler.Vector2;
import com.example.speler.ecs.Camera;
import com.example.speler.ecs.components.Renderable;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.opengl.GLGameWindow;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.example.speler.swing.JGameWindow;
import com.example.speler.swing.SwingRenderer;


public class Main {

		public static Game game;// = new Game(new JGameWindow(), new MyScene());
		public static void main(String[] args) {


				game = new Game(new JGameWindow(), new MyScene());

        FileSystemTiledReader reader = new FileSystemTiledReader();
        TiledMap map = reader.getMap("/home/spy/dev/speler/testgame/src/main/resources/tiled/map.tmx");

        for(TiledTileset tileset : map.getTilesets()){
            System.out.println("Loading: "+tileset.getImage().getSource());
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
                        System.out.println(tile.getTileset().getImage().getSource());
                
                        GameObject ob = new GameObject(game.getEcs());

                        String path = tile.getTileset().getImage().getSource();
                        BufferedImage image = ResourceManager.getImage(path);
                        SpriteSheet sheet = new SpriteSheet(image, 16,16);
                
                        try{
                            System.out.println(sheet);
                            System.out.println(tile.getTilesetX()+" "+tile.getTilesetY());
                            String sprite = Sprite.getSprite(sheet, tile.getTilesetY(), tile.getTilesetX());
                            System.out.println("Sprite: "+ sprite);
                            SpriteComponent component = new SpriteComponent(sprite);
                            component.order = false;
                            ob.addComponent(component);

                            ob.transform.worldScale = new Vector2(size,size);
                            ob.transform.position = new Vector2(x*size-100,y*size-100);

                
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }
        }


				game.getWindow().setBlankCursor();
				game.run();
    }

}
