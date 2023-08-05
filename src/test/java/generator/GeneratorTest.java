package generator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    @Test
    void testConstructor() {
        Generator generator = new Generator();
        assertEquals(123456789, generator.getSeed());
        Generator generator1 = new Generator(321);
        assertEquals(321, generator1.getSeed());
    }
}