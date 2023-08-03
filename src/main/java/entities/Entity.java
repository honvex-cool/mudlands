package entities;

import components.Component;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private int id;
    private Map<Class<? extends Component>, Component> components;

    public Entity() {
        components = new HashMap<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void add(Component component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T get(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public <T extends Component> void remove(Class<T> componentClass) {
        components.remove(componentClass);
    }

    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
}
