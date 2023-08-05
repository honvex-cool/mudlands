package generator;

import org.junit.jupiter.api.Test;
import utils.Config;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PerlinTest {
    @Test
    void testOutput() {
        int testx = 0;
        int testy = 0;
        Perlin perlin = new Perlin(123456789);
        Map<Pair<Integer, Integer>, Float> map = new HashMap<>();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                map.putAll(perlin.getChunkNoiseMap(testx + i, testy + j));

        perlin.printNoiseMap(map, 0, 8);
        for(var v : map.keySet()) {
            System.err.print(v.getFirst());
            System.err.print(' ');
            System.err.print(v.getSecond());
            System.err.print(' ');
            System.err.print(' ');
            System.err.println(map.get(v));
        }
    }
}