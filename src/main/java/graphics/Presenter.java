package graphics;

import components.ComponentHolder;

import java.util.List;

public interface Presenter<T> {
    List<T> present(ComponentHolder componentHolder);
}
