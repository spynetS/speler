package com.example.speler;

import com.example.speler.ecs.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;



public abstract class Scene implements SerializableComponent {

		protected ECS ecs;
		protected Camera camera;


		public Scene(ECS ecs) {
				this.ecs = ecs;
				this.camera = new Camera(0, 0, 2);
		}

		public void init(Game game) throws Exception{
				throw new Exception("NOT IMPLEMENTED");
		}
		
		public void render() throws Exception {
				throw new Exception("RENDER NOT IMPLEMENTED");
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
				render(); // Update view
		}
		
}
