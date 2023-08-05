package generator;

import utils.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class Generator {
    private int seed;

    Generator(Integer seed) {
        this.seed = seed;
    }

    Generator() {
        this.seed = 123456789;
    }

    public int getSeed() {
        return seed;
    }

    public Map<Pair<Integer, Integer>, Object> generateChunk(int x, int y) { //object is temporary and will be replaced be appropriate class
        return null;
    }
}
