package utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Debug {
    private Debug() {
    }

    public static final boolean LOAD_WORLD = true;

    public static void log(Object... objects) {
        System.err.println(Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(" ")));
    }
}
