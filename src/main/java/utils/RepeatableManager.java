package utils;

import java.util.ArrayList;
import java.util.List;

public class RepeatableManager {
    private final List<Repeatable> repeatables = new ArrayList<>();

    public void add(Repeatable repeatable) {
        repeatables.add(repeatable);
    }

    public void update(float time) {
        for(Repeatable repeatable : repeatables)
            repeatable.update(time);
    }
}
