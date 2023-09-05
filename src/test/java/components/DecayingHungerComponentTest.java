package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecayingHungerComponentTest {
    @Test
    void testHungerDoesNotStartDecayingBeforeTheProvidedTime() {
        DecayingHungerComponent hunger = new DecayingHungerComponent(100, 42, 4, 5);
        hunger.update(41);
        assertTrue(Vital.isSatisfied(hunger));
        hunger.update(42);
        assertTrue(Vital.isSatisfied(hunger));
    }

    @Test
    void testHungerDecaysAtTheRateProvidedDuringConstruction() {
        DecayingHungerComponent hunger = new DecayingHungerComponent(100, 42, 4, 2);
        hunger.update(42);
        hunger.update(4);
        assertEquals(98, hunger.getCurrentPoints());
        hunger.update(2);
        assertEquals(98, hunger.getCurrentPoints());
        hunger.update(2);
        assertEquals(96, hunger.getCurrentPoints());
    }
}