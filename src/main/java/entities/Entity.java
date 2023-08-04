package entities;

import components.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity {
    private World world;
    private final int id;
    private String name;
    private final Map<Class<? extends Component>, Component> components;

    Entity(int id, String name) {
        this(null, id, name);
    }

    Entity(World world, int id, String name) {
        this.world = world;
        this.id = id;
        this.name = name;
        components = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Component component) {
        components.put(component.getClass(), component);
        world.upgrade(this);
    }

    public <T extends Component> T get(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public void remove(Class<? extends Component> componentClass) {
        components.remove(componentClass);
        world.downgrade(this);
    }

    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }

    public boolean discard() {
        if(world == null)
            return false;
        world.discard(this);
        world = null;
        return true;
    }

    @Override
    public String toString() {
        return getId() + " " + Objects.requireNonNullElse(name, "entity");
    }
}
