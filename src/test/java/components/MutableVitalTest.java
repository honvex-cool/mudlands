package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableVitalTest {
    @Test
    void testCannotConstructWithNegativeMaxPoints() {
        assertThrows(IllegalArgumentException.class, () -> new MutableVital(-3));
    }

    @Test
    void testCannotConstructWithZeroMaxPoints() {
        assertThrows(IllegalArgumentException.class, () -> new MutableVital(0));
    }

    @Test
    void testGetMaxPointsReturnsTheValueProvidedInConstructor() {
        MutableVital vital = new MutableVital(42);
        assertEquals(42, vital.getMaxPoints());
    }

    @Test
    void testCurrentPointsIsEqualToMaxPointsImmediatelyAfterConstruction() {
        MutableVital vital = new MutableVital(42);
        assertEquals(vital.getMaxPoints(), vital.getCurrentPoints());
    }

    @Test
    void testDamage() {
        MutableVital vital = new MutableVital(100);
        vital.damage(42);
        assertEquals(58, vital.getCurrentPoints());
    }

    @Test
    void testCannotDamageBelowZero() {
        MutableVital vital = new MutableVital(100);
        vital.damage(142);
        assertEquals(0, vital.getCurrentPoints());
    }

    @Test
    void testFix() {
        MutableVital vital = new MutableVital(100);
        vital.damage(20);
        assertEquals(80, vital.getCurrentPoints());
        vital.fix(10);
        assertEquals(90, vital.getCurrentPoints());
    }

    @Test
    void testCannotFixBeyondMaxPoints() {
        MutableVital vital = new MutableVital(100);
        vital.damage(20);
        assertEquals(80, vital.getCurrentPoints());
        vital.fix(42);
        assertEquals(100, vital.getCurrentPoints());
    }

    @Test
    void testVitalIsNotInitiallyDrained() {
        MutableVital vital = new MutableVital(100);
        assertFalse(Vital.isDrained(vital));
    }

    @Test
    void testVitalIsDrainedOnlyWhenCurrentPointsIsZero() {
        MutableVital vital = new MutableVital(100);
        assertFalse(Vital.isDrained(vital));
        vital.damage(50);
        assertFalse(Vital.isDrained(vital));
        vital.damage(50);
        assertTrue(Vital.isDrained(vital));
        assertEquals(0, vital.getCurrentPoints());
    }

    @Test
    void testVitalIsInitiallySatisfied() {
        MutableVital vital = new MutableVital(100);
        assertTrue(Vital.isSatisfied(vital));
    }

    @Test
    void testVitalIsSatisfiedOnlyWhenItIsFullyFixed() {
        MutableVital vital = new MutableVital(100);
        vital.damage(30);
        assertFalse(Vital.isSatisfied(vital));
        vital.fix(40);
        assertTrue(Vital.isSatisfied(vital));
        assertEquals(100, vital.getCurrentPoints());
    }

    @Test
    void testVitalMayBeNeitherDrainedNorSatisfied() {
        MutableVital vital = new MutableVital(100);
        vital.damage(50);
        assertFalse(Vital.isDrained(vital));
        assertFalse(Vital.isSatisfied(vital));
    }

    @Test
    void testVitalMayBeAccordinglyRepresentedByAFraction() {
        MutableVital vital = new MutableVital(200);
        assertEquals(1, Vital.asFraction(vital));
        vital.damage(50); // 150 / 200
        assertEquals(0.75f, Vital.asFraction(vital));
        vital.damage(50); // 100 / 200
        assertEquals(0.5f, Vital.asFraction(vital));
        vital.damage(70); // 30 / 200
        assertEquals(0.15f, Vital.asFraction(vital));
        vital.damage(30); // 0 / 200
        assertEquals(0, Vital.asFraction(vital));
    }
}