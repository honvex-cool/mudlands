package actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CooldownTest {
    @Test
    void testCooldownIsReady() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        assertTrue(cooldown.isReady());
    }

    @Test
    void testReset() {
        Cooldown cooldown = Cooldown.readyToUse(3f);
        cooldown.reset();
        assertFalse(cooldown.isReady());
    }

    @Test
    void testAdvance() {
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
}