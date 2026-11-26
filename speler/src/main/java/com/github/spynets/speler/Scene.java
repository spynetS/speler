package com.github.spynets.speler;

import com.github.spynets.speler.ecs.*;
import com.github.spynets.speler.ecs.systems.ColliderSystem;
import com.github.spynets.speler.ecs.systems.RenderSystem;
import com.github.spynets.speler.ecs.systems.SpriteRenderSystem;
import com.github.spynets.speler.ecs.systems.UiRenderSystem;
import com.github.spynets.speler.input.Input;
import com.github.spynets.speler.render.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;

public class Scene implements SerializableComponent {

		protected ECS ecs;
		protected RenderSystem renderSystem = new SpriteRenderSystem();
		protected RenderSystem uiRenderSystem = new UiRenderSystem();

		protected Renderer renderer;
		
		public Scene(ECS ecs) {
				this.ecs = ecs;
		}
		public Scene(Renderer renderer) {
				this.renderer = renderer;
		}
		public Scene() {
		}

		public void start(Game game) throws Exception {

		}
		
		public void render(int w, int h) throws Exception {
				if(renderer != null && renderSystem != null && uiRenderSystem != null){
						renderSystem.render(ecs, renderer, w,h);
						uiRenderSystem.render(ecs, renderer, w,h);
				}else if (renderer == null) {
						throw new Exception("No renderer has been set in the scene");
				}
				Input.resetMousePressed();
		}

		
		public ECS getEcs() {
				return ecs;
		}

		public void setEcs(ECS ecs) {
				this.ecs = ecs;
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

				return sceneJson;
		}

		@Override
		public void deserialize(JsonObject json) {
				// Deserialize ECS
				ecs.deserialize(json.getAsJsonObject("ecs"));

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
