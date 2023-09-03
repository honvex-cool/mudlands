package utils;

import actions.Repeatable;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RepeatableTest {
    @Test
    void testUpdate() {
        Runnable runnable = mock(Runnable.class);
        Repeatable repeatable = Repeatable.notReady(5, runnable);
        verify(runnable, never()).run();
        repeatable.update(4);
        verify(runnable, never()).run();
        repeatable.update(4);
        verify(runnable, times(1)).run();
        repeatable.update(4);
        verify(runnable, times(1)).run();
    }
}