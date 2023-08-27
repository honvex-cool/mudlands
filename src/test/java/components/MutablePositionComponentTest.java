package components;

import org.junit.jupiter.api.Test;
import utils.Config;
import utils.Debug;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class MutablePositionComponentTest {

    @Test
    void testBasicGettersSetters(){
        MutablePositionComponent mutablePositionComponent = new MutablePositionComponent(1.2f,3.4f);
        assertEquals(1.2f, mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(3.4f, mutablePositionComponent.getY(), Debug.TEST_DELTA);

        mutablePositionComponent.setX(5.6f);
        assertEquals(5.6f, mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(3.4f, mutablePositionComponent.getY(), Debug.TEST_DELTA);

        mutablePositionComponent.setY(7.8f);
        assertEquals(5.6f, mutablePositionComponent.getX(), Debug.TEST_DELTA);
        assertEquals(7.8f, mutablePositionComponent.getY(), Debug.TEST_DELTA);

        assertEquals(new Pair<>((int)Math.floor(5.6f),(int)Math.floor(7.8f)), PositionComponent.getFieldAsPair(mutablePositionComponent));
    }
    @Test
    void testChunkGetters(){
        float eps = Config.CHUNK_SIZE/10;
        MutablePositionComponent mutablePositionComponent = new MutablePositionComponent(0,0);
        assertEquals(0, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(0, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,0), PositionComponent.getChunk(mutablePositionComponent));

        mutablePositionComponent.setX(eps);
        mutablePositionComponent.setY(eps);
        assertEquals(0, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(0, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,0), PositionComponent.getChunk(mutablePositionComponent));

        mutablePositionComponent.setX(-eps);
        mutablePositionComponent.setY(eps);
        assertEquals(-1, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(0, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(-1,0), PositionComponent.getChunk(mutablePositionComponent));

        mutablePositionComponent.setX(-eps);
        mutablePositionComponent.setY(-eps);
        assertEquals(-1, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(-1, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(-1,-1), PositionComponent.getChunk(mutablePositionComponent));

        mutablePositionComponent.setX(eps);
        mutablePositionComponent.setY(-eps);
        assertEquals(0, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(-1, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(0,-1), PositionComponent.getChunk(mutablePositionComponent));

        mutablePositionComponent.setX(Config.CHUNK_SIZE+eps);
        mutablePositionComponent.setY(Config.CHUNK_SIZE+eps);
        assertEquals(1, PositionComponent.getChunkX(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(1, PositionComponent.getChunkY(mutablePositionComponent), Debug.TEST_DELTA);
        assertEquals(new Pair<>(1,1), PositionComponent.getChunk(mutablePositionComponent));
    }

}