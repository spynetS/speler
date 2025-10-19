 package com.example.ecs;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.example.scripting.*;
import com.google.gson.JsonObject;
import com.example.SerializableComponent;
import com.example.animations.AnimationTrack;
import com.example.ecs.UpdateSystem;


public class ECS implements SerializableComponent {
    public Map<UUID, Map<Class<?>, Component>> components = new HashMap<>();
    public List<UUID> entities = new ArrayList<>();
    public List<UpdateSystem> updateSystems = new LinkedList<>();

    // Create a new entity
    public UUID instantiate() {
        UUID uuid = UUID.randomUUID();
        entities.add(uuid);
        return uuid;
    }

    // Add a system
		public void addSystem(UpdateSystem system) {
				updateSystems.add(system);
		}



    // Add a component to an entity
    public <T extends Component> void addComponent(UUID entityId, T component) {
        components.computeIfAbsent(entityId, id -> new HashMap<>())
						.put(component.getClass(), component);
    }

    // Get a component from an entity
	public <T> T getComponent(UUID id, Class<T> componentClass) { //

		Map<Class<?>, Component> entityComponents = components.get(id);
		if (entityComponents == null)
			return null;
		Component comp = entityComponents.get(componentClass);
		if (componentClass.isInstance(comp))
			return componentClass.cast(comp);
		return null;
	}

	public List<Component> getComponents(UUID id) {
			return new LinkedList<Component>(components.get(id).values());
	}
		
    // Get all entities
    public List<UUID> getEntities() {
        return entities;
    }

    // Main update loop: call all systems
	public void update(float deltaTime) {
        for (UpdateSystem system : updateSystems) {
            system.update(this, deltaTime);
        }
    }


    @Override
    public JsonObject serialize() {
        JsonObject ecsJson = new JsonObject();
        JsonArray entitiesArray = new JsonArray();

        for (UUID id : entities) {
            JsonObject entityJson = new JsonObject();
            entityJson.addProperty("id", id.toString());

            JsonArray componentsArray = new JsonArray();

            Map<Class<?>, Component> entityComponents = components.get(id);
            if (entityComponents != null) {
                for (Component comp : entityComponents.values()) {
                    JsonObject compJson = new JsonObject();
                    compJson.addProperty("type", comp.getClass().getName());

                    // Each component must implement SerializableComponent
                    if (comp instanceof SerializableComponent serializable) {
                        compJson.add("data", serializable.serialize());
                    }

                    componentsArray.add(compJson);
                }
            }

            entityJson.add("components", componentsArray);
            entitiesArray.add(entityJson);
        }

        ecsJson.add("entities", entitiesArray);
        return ecsJson;
    }

    @Override
    public void deserialize(JsonObject data) {
        components.clear();
        entities.clear();

        JsonArray entitiesArray = data.getAsJsonArray("entities");
        for (var e : entitiesArray) {
            JsonObject entityJson = e.getAsJsonObject();
            UUID id = UUID.fromString(entityJson.get("id").getAsString());
            entities.add(id);

            Map<Class<?>, Component> comps = new HashMap<>();
            JsonArray componentsArray = entityJson.getAsJsonArray("components");

            for (var c : componentsArray) {
                JsonObject compJson = c.getAsJsonObject();
                String type = compJson.get("type").getAsString();

                try {
                    Class<?> clazz = Class.forName(type);
                    Object instance = clazz.getDeclaredConstructor().newInstance();

                    if (instance instanceof SerializableComponent serializable) {
                        serializable.deserialize(compJson.getAsJsonObject("data"));
                        comps.put(clazz, (Component) instance);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            components.put(id, comps);
        }
    }


    public static interface Component {}

}
