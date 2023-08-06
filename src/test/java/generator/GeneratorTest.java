package generator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    @Test
    void testConstructor() {
        Generator generator = new Generator();
        //assertEquals(123456789, generator.getSeed());
        //Generator generator1 = new Generator(321);
        //assertEquals(321, generator1.getSeed());

        var map = new HashMap<Pair<Integer, Integer>, Pair<GroundType, ObjectType>>();

        for(int x = 0; x < 8; x++)
            for(int y = 0; y < 8; y++)
                map.putAll(generator.generateChunk(x, y));
        try {
            generator.saveMapToFile(map, "test");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}