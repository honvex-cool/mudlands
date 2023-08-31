package entities.materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MixTest {
    @Test
    void testConstructor() {
        // no negative materials allowed
        assertThrows(IllegalArgumentException.class, () -> new Mix(-1, 2, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Mix(1, -2, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Mix(1, 2, -3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Mix(1, 2, 3, -4));

        // zero is allowed
        assertDoesNotThrow(() -> new Mix(0, 2, 3, 4));
        assertDoesNotThrow(() -> new Mix(1, 0, 3, 4));
        assertDoesNotThrow(() -> new Mix(1, 2, 0, 4));
        assertDoesNotThrow(() -> new Mix(1, 2, 3, 0));

        // but not all zeros
        assertThrows(IllegalArgumentException.class, () -> new Mix(0, 0, 0, 0));
    }

    @Test
    void testValues() {
        Mix mix = new Mix(1, 2, 3, 4);
        assertEquals(1, mix.wood());
        assertEquals(2, mix.mineral());
        assertEquals(3, mix.mud());
        assertEquals(4, mix.flesh());
        assertEquals(10, mix.total());
    }
}