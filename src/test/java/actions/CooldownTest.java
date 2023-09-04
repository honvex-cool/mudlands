package actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CooldownTest {
    @Test
    void testReadyToUse() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        assertTrue(cooldown.isReady());
    }

    @Test
    void testCooldownIsNotReadyAfterReset() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        cooldown.reset();
        assertFalse(cooldown.isReady());
    }

    @Test
    void testAdvanceSimulatesTheFlowOfTime() {
        Cooldown cooldown = Cooldown.readyToUse(3f); // will need 3 seconds to cool down
        cooldown.reset();

        cooldown.advance(2f); // 2 seconds pass
        assertFalse(cooldown.isReady()); // not ready yet

        cooldown.advance(2f); // 2 more seconds pass
        assertTrue(cooldown.isReady()); // now it is ready
    }

    @Test
    void testUseReturnsFalseIfNotReady() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        cooldown.reset();
        assertFalse(cooldown.use());
    }

    @Test
    void testUseReturnsTrueAndResetsIfReady() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        assertTrue(cooldown.use());
        assertFalse(cooldown.isReady());
    }

    @Test
    void testNotReadyToUse() {
        Cooldown cooldown = Cooldown.notReadyToUse(3f);
        assertFalse(cooldown.isReady());
    }

    @Test
    void testConvenientUseIfReady() {
        Cooldown cooldown = Cooldown.notReadyToUse(3f);
        assertTrue(cooldown.use(3f));
    }

    @Test
    void testTwoCooldownsAreEqualIfTheyHaveTheSameState() {
        Cooldown first = Cooldown.notReadyToUse(5);
        Cooldown second = Cooldown.notReadyToUse(5);
        assertEquals(first, second);

        first.advance(4);
        second.advance(4);
        assertEquals(first, second);
    }

    @Test
    void testTwoCooldownsAreDifferentIfTheyHaveDifferentDuration() {
        Cooldown first = Cooldown.notReadyToUse(5);
        Cooldown second = Cooldown.notReadyToUse(6);
        assertNotEquals(first, second);

        first.advance(1);
        second.advance(2);
        assertNotEquals(first, second);
    }

    @Test
    void testTwoCooldownsAreDifferentIfTheyAreDifferentlyAdvanced() {
        Cooldown first = Cooldown.notReadyToUse(5);
        Cooldown second = Cooldown.notReadyToUse(5);
        first.advance(4);
        second.advance(3);
        assertNotEquals(first, second);
    }
}