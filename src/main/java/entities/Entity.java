package entities;

import components.Component;
import inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity {
    private EntityManager entityManager;
    private final int id;
    private String name;
    private final Map<Class<? extends Component>, Component> components;


    Entity(int id, String name) {
        this(null, id, name);
    }

    Entity(EntityManager entityManager, int id, String name) {
        this.entityManager = entityManager;
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
        entityManager.upgrade(this);
    }

    public <T extends Component> T get(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public void remove(Class<? extends Component> componentClass) {
        components.remove(componentClass);
        entityManager.downgrade(this);
    }

    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }

    public boolean discard() {
        if(entityManager == null)
            return false;
        entityManager.discard(this);
        entityManager = null;
        return true;
    }

    @Override
    public String toString() {
        return getId() + " " + Objects.requireNonNullElse(name, "entity");
    }
}
