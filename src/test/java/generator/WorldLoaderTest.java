package generator;

import org.junit.jupiter.api.Test;
import utils.Pair;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WorldLoaderTest {
    /*@Test
    void testSaveLoad() {
        WorldLoader worldLoader = new WorldLoader();
        worldLoader.createWorld(123, "testWorld");
        var map = worldLoader.loadChunk(0, 0);
        assertNotNull(map);

        map.put(new Pair(1, 1), new FieldStruct(GroundType.WATER, ObjectType.IRONSTONE));
        worldLoader.saveChunk(0, 0, map);

        try {
            worldLoader.saveWorld();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        WorldLoader worldLoader1 = new WorldLoader();
        try {
            worldLoader1.loadWorld("testWorld");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        var map1 = worldLoader1.loadChunk(0, 0);
        assertEquals(map, map1);
    }*/

    @Test
    void testCannotSaveNullWorld() {
        WorldLoader worldLoader = new WorldLoader();
        try {
            worldLoader.saveWorld();
        } catch(Exception e) {
            if(e.getMessage().equals("No world to save"))
                return;
        }
        fail();
    }
}