package com.example.speler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.LinkedList;

import com.example.speler.input.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.swing.JPanel;
import com.example.speler.ecs.*;
import com.example.speler.editor.EditorSystem;
import com.example.speler.Vector2;
import com.example.speler.ecs.systems.*;




public class Scene extends JPanel implements SerializableComponent
{
		protected ECS ecs;
		protected Camera camera;
		
		public Scene(ECS ecs) {
			this.ecs = ecs;
			setBackground(new Color(40, 125, 255));
			this.camera = new Camera(0, 0, 2);
			setDoubleBuffered(true); // helps prevent flickering
			initInput();

		}

		public void removeInput() {
				removeMouseListener(getMouseListeners()[0]);
		}
		
    @Override
    protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit.getDefaultToolkit().sync();

				int x = (int)Input.getMousePositionOnCanvas().x;
				int y = (int)Input.getMousePositionOnCanvas().y;

				Input.setMousePosition(new Vector2(
																					 camera.screenToWorldX(x, getWidth()),
																					 camera.screenToWorldY(y, getHeight())
																					 ));
				Graphics2D graphics2D =  (Graphics2D) g;
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				
				RenderSystem renderer = new SpriteRenderSystem();
				renderer.render(ecs, graphics2D, camera, this.getWidth(), this.getHeight());
    }


		public ECS getEcs() {
				return ecs;
		}

		public void setEcs(ECS ecs) {
				this.ecs = ecs;
		}

		public Camera getCamera() {
				return camera;
		}

		public void setCamera(Camera camera) {
				this.camera = camera;
		}


		private void initInput() {
				//setLayout(new GridLayout(0, 1));

				setFocusable(true);

				addKeyListener(new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
										super.keyPressed(e);
										Input.addKey(e);
								}

								@Override
								public void keyReleased(KeyEvent e) {
										super.keyReleased(e);
										Input.removeKey(e);
								}
						});

				MouseAdapter mouseAdapter = new MouseAdapter() {
								@Override
								public void mousePressed(MouseEvent e) {
										super.mousePressed(e);
										Input.addMouseButton(e);
										Input.setMouseEvent(e);
										Input.setMousePressed(e.getButton());
										//										requestFocus();
								}

								@Override
								public void mouseReleased(MouseEvent e) {
										super.mouseReleased(e);
										Input.removeMouseButton(e);
										Input.setMouseEvent(e);

								}

								@Override
								public void mouseDragged(MouseEvent e) {
										super.mouseDragged(e);
										Input.setMousePositionOnCanvas(
																									 new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
										Input.setMouseEvent(e);

								}

								@Override
								public void mouseMoved(MouseEvent e) {
										super.mouseMoved(e);
										Input.setMousePositionOnCanvas(
																									 new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
										Input.setMouseEvent(e);
										//requestFocus();
								}

								@Override
								public void mouseWheelMoved(MouseWheelEvent e) {
										Input.setScrollValue((float) e.getPreciseWheelRotation());
										Input.setMouseEvent(e);
								}

						};
				addMouseListener(mouseAdapter);
				addMouseMotionListener(mouseAdapter);
				addMouseWheelListener(mouseAdapter);
		}
		@Override
		public JsonObject serialize() {
				JsonObject sceneJson = new JsonObject();

				// Serialize ECS
				sceneJson.add("ecs", ecs.serialize());

				// Serialize camera
				JsonObject cameraJson = new JsonObject();
				cameraJson.addProperty("x", camera.x);
				cameraJson.addProperty("y", camera.y);
				cameraJson.addProperty("zoom", camera.zoom);
				sceneJson.add("camera", cameraJson);

				return sceneJson;
		}

		@Override
		public void deserialize(JsonObject json) {
				// Deserialize ECS
				ecs.deserialize(json.getAsJsonObject("ecs"));

				// Deserialize camera
				JsonObject cameraJson = json.getAsJsonObject("camera");
				camera.x = cameraJson.get("x").getAsFloat();
				camera.y = cameraJson.get("y").getAsFloat();
				camera.zoom = cameraJson.get("zoom").getAsFloat();
		}
		public void saveScene(String path) throws Exception {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(serialize());
				Files.writeString(Path.of(path), json);
		}

		public void loadScene(String path) throws Exception {
				Gson gson = new Gson();
				String jsonStr = Files.readString(Path.of(path));
				JsonObject obj = gson.fromJson(jsonStr, JsonObject.class);
				deserialize(obj);
				repaint(); // Update view
		}

}
