package com.example.speler;

import com.example.speler.ecs.*;
import com.example.speler.ecs.systems.RenderSystem;
import com.example.speler.ecs.systems.SpriteRenderSystem;
import com.example.speler.input.Input;
import com.example.speler.render.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;

public class Scene implements SerializableComponent {

		protected ECS ecs;
		protected Camera camera;
		protected RenderSystem renderSystem = new SpriteRenderSystem();

		protected Renderer renderer;
		
		public Scene(ECS ecs) {
				this.ecs = ecs;
				this.camera = new Camera(0, 0, 2);
		}
		public Scene(Renderer renderer) {
				this.renderer = renderer;
				this.camera = new Camera(0, 0, 2);
		}
		public Scene() {
				this.camera = new Camera(0, 0, 2);
		}

		public void start(Game game) throws Exception{

		}
		
		public void render(int w, int h) throws Exception {
				if(renderer != null && renderSystem != null){
						renderSystem.render(ecs, renderer, camera, w,h);
				}
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

		public Renderer getRenderer() {
				return renderer;
		}
		public void setRenderer(Renderer renderer) {
				this.renderer = renderer;
		}
		public RenderSystem getRenderSystem() {
				return renderSystem;
		}

		public void setRenderSystem(RenderSystem renderSystem) {
				this.renderSystem = renderSystem;
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
				//				render(); // Update view
		}
}
