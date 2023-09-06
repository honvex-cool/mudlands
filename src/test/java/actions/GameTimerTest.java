package actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {
    @Test
    void testCannotCreateATimerWithNegativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> GameTimer.started(-3));
        assertThrows(IllegalArgumentException.class, () -> GameTimer.finished(-3));
    }

    @Test
    void testCanCreateTimersWithZeroDuration() {
        assertDoesNotThrow(() -> GameTimer.started(0));
        assertDoesNotThrow(() -> GameTimer.finished(0));
    }

    @Test
    void testZeroDurationTimersAreImmediatelyFinished() {
        GameTimer first = GameTimer.started(0);
        GameTimer second = GameTimer.finished(0);
        assertTrue(first.isFinished());
        assertTrue(second.isFinished());
    }

    @Test
    void testStartedNonzeroTimerIsNotFinishedImmediatelyAfterCreation() {
        GameTimer timer = GameTimer.started(4);
        assertFalse(timer.isFinished());
    }

    @Test
    void testAdvancingATimerByLessThanEnoughDoesNotMakeItFinished() {
        GameTimer timer = GameTimer.started(4);
        timer.advance(2);
        assertFalse(timer.isFinished());
        timer.advance(1);
        assertFalse(timer.isFinished());
    }

    @Test
    void testAdvancingATimerByJustEnoughMakesItFinished() {
        GameTimer first = GameTimer.started(4);
        first.advance(4);
        assertTrue(first.isFinished());

        GameTimer second = GameTimer.finished(4);
        second.advance(2);
        second.advance(2);
        assertTrue(second.isFinished());
    }

    @Test
    void testAdvancingATimerByMoreThanEnoughMakesItFinished() {
        GameTimer first = GameTimer.started(4);
        first.advance(5);
        assertTrue(first.isFinished());

        GameTimer second = GameTimer.started(4);
        second.advance(3);
        second.advance(2);
        assertTrue(second.isFinished());
    }

    @Test
    void testATimerCanBeFinishedEarly() {
        GameTimer timer = GameTimer.started(4);
        timer.finish();
        assertTrue(timer.isFinished());
    }

    @Test
    void testATimerCreatedAsFinishedIsIndeedFinished() {
        GameTimer timer = GameTimer.finished(4);
        assertTrue(timer.isFinished());
    }

    @Test
    void testFinishingAFinishedTimerKeepsItFinished() {
        GameTimer timer = GameTimer.finished(4);
        timer.finish();
        assertTrue(timer.isFinished());
    }

    @Test
    void testRestartingATimerMakesItRunAgainForTheSameDuration() {
        GameTimer timer = GameTimer.finished(4);
        timer.restart();
        assertFalse(timer.isFinished());
        timer.advance(3.99f);
        assertFalse(timer.isFinished());
        timer.advance(0.01f);
        assertTrue(timer.isFinished());
    }

    @Test
    void testAdvancingATimerByJustEnoughOrLessReturnsZeroUnmeasuredTime() {
        GameTimer first = GameTimer.started(4);
        assertEquals(0, first.advance(3));
        assertEquals(0, first.advance(1));

        GameTimer second = GameTimer.started(4);
        assertEquals(0, second.advance(4));
    }

    @Test
    void testAdvancingATimerByMoreThanEnoughReturnsTheAmountOfUnmeasuredTime() {
        GameTimer timer = GameTimer.started(4);
        assertEquals(1, timer.advance(5));
        assertEquals(5, timer.advance(5));
    }

    @Test
    void testATimerCanBeInterpretedAsProgressFromZeroToOne() {
        GameTimer timer = GameTimer.started(4);
        timer.advance(3);
        assertEquals(0.75f, timer.getProgress());
    }

    @Test
    void testATimerThatIsNotFinishedCannotBeUsed() {
        GameTimer timer = GameTimer.started(4);
        timer.advance(2);
        assertFalse(timer.use());
        timer.advance(1);
        assertFalse(timer.use());
    }

    @Test
    void testUsingAFinishedTimerSucceedsAndRestarts() {
        GameTimer timer = GameTimer.started(4);
        timer.advance(5);
        assertTrue(timer.use());
        assertEquals(0, timer.getProgress());
    }

    @Test
    void testTimerCanBeConvenientlyAdvancedAndUsedWithASingleMethod() {
        GameTimer first = GameTimer.started(4);
        assertTrue(first.use(5));

        GameTimer second = GameTimer.started(4);
        assertFalse(second.use(1));
        assertEquals(0.25f, second.getProgress());
    }

    @Test
    void testTwoTimersAreEqualIfTheyHaveEqualDurationsAndEqualProgress() {
        GameTimer first = GameTimer.started(4);
        GameTimer second = GameTimer.started(4);
        assertEquals(first, second);
        first.advance(2);
        second.advance(2);
        assertEquals(first, second);
    }

    @Test
    void testTwoTimersAreDifferentIfTheyHaveDifferentDurations() {
        GameTimer first = GameTimer.started(4);
        GameTimer second = GameTimer.started(5);
        assertNotEquals(first, second);
        first.advance(1);
        second.advance(2);
        assertNotEquals(first, second);
    }

    @Test
    void testTwoTimersAreDifferentIfTheyHaveDifferentProgress() {
        GameTimer first = GameTimer.started(4);
        GameTimer second = GameTimer.started(4);
        first.advance(3);
        assertNotEquals(first, second);
        second.advance(2);
        assertNotEquals(first, second);
    }
}