package com.example;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class ECS {
		public Map<Integer, Map<Class<?>, Component>> components = new HashMap<>();

		public List<Integer> entites = new ArrayList<>();

 		public int instanstiate( ){
				entites.add(0);
				return 0;
		}
			

public List<Integer> getEntities(){
		return entites;
}

public <T extends Component> void addComponent(int entityId, T component) {
	components.computeIfAbsent(entityId, id -> new HashMap<>())
			.put(component.getClass(), component);
}

		public <T> T getComponent(int id, Class<T> componentClass) {
				Map<Class<?>, Component> entityComponents = components.get(id);
				if (entityComponents == null)
						return null;

				Component comp = entityComponents.get(componentClass);
				if (componentClass.isInstance(comp))
						return componentClass.cast(comp);
				else
						return null;
		}
		
public static interface Component
{

}

public static class Transform implements Component {
		int x=0, y=0;
}

public static class Renderable implements Component {

		public Renderable() {  }
}



}
