package actions;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RepeatableManagerTest {
    @Test
    void testRepeatablesAreUpdatedIndependently() {
        Runnable first = mock(Runnable.class);
        Runnable second = mock(Runnable.class);

        RepeatableManager manager = new RepeatableManager();
        manager.add(new Repeatable(1.5f, first));
        manager.add(new Repeatable(3, second));
        manager.update(2); // 2 seconds pass

        verify(first, times(1)).run();
        verify(second, never()).run();
    }

    @Test
    void testProceduresAreInvokedCorrectlyWhenTimeIsUpdatedInASingleBurst() {
        Runnable first = mock(Runnable.class);
        Runnable second = mock(Runnable.class);

        RepeatableManager manager = new RepeatableManager();
        manager.add(new Repeatable(2, first));
        manager.add(new Repeatable(3, second));
        manager.update(6); // 6 seconds pass in one burst

        verify(first, times(3)).run();
        verify(second, times(2)).run();
    }

    @Test
    void testProceduresAreInvokedCorrectlyWhenTimeIsUpdatedGradually() {
        Runnable first = mock(Runnable.class);
        Runnable second = mock(Runnable.class);

        RepeatableManager manager = new RepeatableManager();
        manager.add(new Repeatable(2, first));
        manager.add(new Repeatable(3, second));
        manager.update(2.5f); // 6 seconds pass with gradual updates
        manager.update(2.5f);
        manager.update(1f);

        verify(first, times(3)).run();
        verify(second, times(2)).run();
    }
}