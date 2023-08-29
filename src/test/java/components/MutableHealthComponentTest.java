package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableHealthComponentTest {
    @Test
    void testGetMaxPointsReturnsTheValueProvidedInConstructor() {
        MutableHealthComponent health = new MutableHealthComponent(42);
        assertEquals(42, health.getMaxPoints());
    }
}