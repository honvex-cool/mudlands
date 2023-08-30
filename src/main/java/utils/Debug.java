package utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Debug {
    private Debug() {
    }

    public static final boolean LOAD_WORLD = false;
    public static final float TEST_DELTA = 1E-7f;

    public static void log(Object... objects) {
       // System.err.println(Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(" ")));
    }
}
