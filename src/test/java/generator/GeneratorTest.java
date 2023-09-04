package generator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    @Test
    void testSeedImplicatesOutput() {
        Generator generator1 = new Generator(7312L);
        var gen1 = generator1.generateChunk(new Pair<>(73,12));

        Generator generator2 = new Generator(7312L);
        var gen2 = generator2.generateChunk(new Pair<>(73,12));

        assertEquals(gen1,gen2);
    }

    @Test
    void testDifferentSeedsGiveDifferentOutputs() { //chance of this test not passing with valid code is close to 0
        Generator generator1 = new Generator(145L);
        var gen1 = generator1.generateChunk(new Pair<>(-20,45));
        var gen1_ = generator1.generateChunk(new Pair<>(23,-30));

        Generator generator2 = new Generator(541L);
        var gen2 = generator2.generateChunk(new Pair<>(-20,45));
        var gen2_ = generator2.generateChunk(new Pair<>(23,-30));

        assertNotEquals(gen1,gen2);
        assertNotEquals(gen1_,gen2_);
    }
}