 package com.example.speler.ecs;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.example.speler.scripting.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.example.speler.SerializableComponent;
import com.example.speler.animations.AnimationTrack;
import com.example.speler.ecs.systems.UpdateSystem;
import com.example.speler.ecs.components.ParentComponent;
import com.example.speler.ecs.listeners.EntityListener;


public class ECS implements SerializableComponent {
    public Map<UUID, Map<Class<?>, Component>> components = new HashMap<>();
    public List<UUID> entities = new ArrayList<>();
    public List<UpdateSystem> updateSystems = new LinkedList<>();
    public List<EntityListener> listeners = new LinkedList<>();

    // Create a new entity
    public UUID instantiate() {
        UUID uuid = UUID.randomUUID();
        entities.add(uuid);
        return uuid;
    }

    // Add a system
	public void addSystem(UpdateSystem system) {
			for(EntityListener listener: listeners) listener.onSystemAdded(system);
			updateSystems.add(system);
		}

		public <T extends Component> void removeComponent(UUID id, Class<T> compClass) {
			components.get(id).remove(compClass);
		}


    // Add a component to an entity
	public <T extends Component> void addComponent(UUID entityId, T component) {

			for(EntityListener listener: listeners) listener.onComponentAdded(entityId, component);
			
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

		public void removeEntity(UUID id) {

				for (UUID uid : entities) {
						if (uid.equals(id))
								continue;
						ParentComponent p = getComponent(uid, ParentComponent.class);

						if(p != null && p.parentId.equals(id))
								removeEntity(uid);
				}
		
				entities.remove(id);

				List<Component> components = getComponents(id);
				for (Component comp : components) {
						removeComponent(id, comp.getClass());
				}
		}

		public void start() {
        for (UpdateSystem system : updateSystems) {
            system.start(this);
        }
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
