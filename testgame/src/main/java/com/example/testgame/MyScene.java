package com.example.testgame;


import com.example.speler.JScene;
import com.example.speler.Vector2;
import com.example.speler.ecs.ECS;
import com.example.speler.ecs.components.ColliderComponent;
import com.example.speler.ecs.components.Rigidbody;
import com.example.speler.ecs.components.ScriptComponent;
import com.example.speler.ecs.components.SpriteComponent;
import com.example.speler.resources.ResourceManager;
import com.example.speler.resources.SpriteSheet;
import com.example.speler.resources.ResourceManager.Sprite;
import com.example.speler.scripting.GameObject;
import com.example.speler.scripting.Script;
import com.example.testgame.Player.Player;

public class MyScene extends JScene {

		public MyScene(ECS ecs) {
				super(ecs);

				int map[][] = {
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,2,0,0,0,0,0,0,0,0,0,0,0,0},
						
				};
				new Player(ecs);

				SpriteSheet spriteSheet = new SpriteSheet(ResourceManager.loadImage("/home/spy/dev/playengine/testgame/sprites/Tiles/Path_Tile.png"), 48/3, 96/6);
				
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[y].length; x++) {
						try{
								String image = "";
								System.out.println(map[y][x]);
										
								switch(map[y][x]){
								case 0:
										image = Sprite.getSprite("/home/spy/dev/playengine/testgame/sprites/Tiles/Grass_Middle.png");
										break;
								case 1:
										image = Sprite.getSprite(spriteSheet, 1, 0);
										break;
								case 2:
										image = Sprite.getSprite(spriteSheet, 1, 2);
										break;
								case 3:
										image = Sprite.getSprite("/home/spy/dev/playengine/testgame/sprites/Tiles/Path_Middle.png");
										break;	
								case 4:
										image = Sprite.getSprite(spriteSheet, 4, 1);
										break;
								case 5:
										image = Sprite.getSprite(spriteSheet, 0, 1);
										break;
								case 6:
										image = Sprite.getSprite(spriteSheet, 2, 1);
										break;
									
								}

								Tile ground = new Tile(ecs, image);
								ground.transform.position = new Vector2((x - map[y].length/2) * 50, (y-map.length/2)*50);
								ground.transform.worldScale = new Vector2(50, 50);
								ground.getComponent(SpriteComponent.class).order = false;
							
						}catch(Exception e){e.printStackTrace();}
				}
			}
				
				Ground floor = new Ground(ecs,0);
				floor.transform.position = new Vector2(0,100);
				floor.transform.worldScale = new Vector2(50, 50);
				Ground floor1 = new Ground(ecs,1);
				floor1.transform.position = new Vector2(50,100);
				floor1.transform.worldScale = new Vector2(50, 50);
				Ground floor2 = new Ground(ecs,2);
				floor2.transform.position = new Vector2(150,100);
				floor2.transform.worldScale = new Vector2(50, 50);

				try{
					Tile tree = new Tile(ecs,
															 Sprite.getSprite("/home/spy/dev/playengine/testgame/sprites/Outdoor/Oak_Tree.png"));
					var collider = new ColliderComponent();
					collider.height = -170;
					collider.width = -150;
					tree.addComponent(collider);

					var trigger = new ColliderComponent();
					trigger.height = -100;
					trigger.width = -150;
					trigger.layer = 1;
					tree.addComponent(trigger);

					
					//					tree.removeComponent(SpriteComponent.class);
					tree.addComponent(new ScriptComponent(new KillableEntity()));
					tree.transform.position = new Vector2(0,-200);
					tree.transform.worldScale = new Vector2(200,200);
				} catch(Exception e){}

		}
}
