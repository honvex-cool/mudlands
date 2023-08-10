package components;

import org.junit.jupiter.api.Test;
import utils.Config;
import utils.Debug;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PositionComponentTest {

    @Test
    void testBasicGettersSetters(){
        PositionComponent positionComponent = new PositionComponent(1.2f,3.4f);
        assertEquals(1.2f,positionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(3.4f,positionComponent.getY(), Debug.TEST_DELTA);

        positionComponent.setX(5.6f);
        assertEquals(5.6f,positionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(3.4f,positionComponent.getY(), Debug.TEST_DELTA);

        positionComponent.setY(7.8f);
        assertEquals(5.6f,positionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(7.8f,positionComponent.getY(), Debug.TEST_DELTA);

        assertEquals(new Pair<>((int)Math.floor(5.6f),(int)Math.floor(7.8f)),positionComponent.getFieldAsPair());
    }
    @Test
    void testChunkGetters(){
        float eps = Config.CHUNK_SIZE/10;
        PositionComponent positionComponent = new PositionComponent(0,0);
        assertEquals(0,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(0,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,0),positionComponent.getChunk());

        positionComponent.setX(eps);
        positionComponent.setY(eps);
        assertEquals(0,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(0,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,0),positionComponent.getChunk());

        positionComponent.setX(-eps);
        positionComponent.setY(eps);
        assertEquals(-1,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(0,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(-1,0),positionComponent.getChunk());

        positionComponent.setX(-eps);
        positionComponent.setY(-eps);
        assertEquals(-1,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(-1,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(-1,-1),positionComponent.getChunk());

        positionComponent.setX(eps);
        positionComponent.setY(-eps);
        assertEquals(0,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(-1,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,-1),positionComponent.getChunk());

        positionComponent.setX(Config.CHUNK_SIZE+eps);
        positionComponent.setY(Config.CHUNK_SIZE+eps);
        assertEquals(1,positionComponent.getChunkX(), Debug.TEST_DELTA);
        assertEquals(1,positionComponent.getChunkY(), Debug.TEST_DELTA);
        assertEquals(new Pair<>(1,1),positionComponent.getChunk());
    }

}