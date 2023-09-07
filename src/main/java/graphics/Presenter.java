package graphics;

import components.ComponentHolder;
import entities.Entity;

import java.util.List;

public interface Presenter<T> {
    List<T> present(Entity entity);
}
