package actions;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RepeatableTest {
    @Test
    void testRunnableIsNotRunBeforeTheDesiredTimePasses() {
        Runnable runnable = mock(Runnable.class);
        Repeatable repeatable = new Repeatable(5, runnable);
        verify(runnable, never()).run();
        repeatable.update(4);
        verify(runnable, never()).run();
    }

    @Test
    void testRunnableIsRunAfterTheDesiredTimePasses() {
        Runnable runnable = mock(Runnable.class);
        Repeatable repeatable = new Repeatable(5, runnable);
        repeatable.update(4);
        repeatable.update(4);
        verify(runnable).run();
    }

    @Test
    void testRunnableIsRepeatedlyRunAsMoreTimePasses() {
        Runnable runnable = mock(Runnable.class);
        Repeatable repeatable = new Repeatable(5, runnable);
        repeatable.update(10);
        repeatable.update(20);
        verify(runnable, times(6)).run();
    }

    @Test
    void testRepetitionCanBeDelayed() {
        Runnable runnable = mock(Runnable.class);
        Repeatable repeatable = new Repeatable(5, 5, runnable);
        repeatable.update(5);
        verify(runnable, never()).run();
        repeatable.update(4.9f);
        verify(runnable, never()).run();
        repeatable.update(0.1f);
        verify(runnable, times(1)).run();
    }
}