package graphics.drawable;

import graphics.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceholderDrawableTest {
    @Test
    void testGetTransformReturnsTheTransformProvidedDuringConstruction() {
        Transform transform = new Transform(2, 3, 4, 5);
        PlaceholderDrawable drawable = new PlaceholderDrawable(transform);
        assertEquals(transform, drawable.getTransform());
    }

    @Test
    void testSetTransformSetsTheTransformToTheProvidedOne() {
        PlaceholderDrawable drawable = new PlaceholderDrawable(new Transform(2, 3, 4, 5));
        Transform next = new Transform(20, 30, 40, 50);
        drawable.setTransform(next);
        assertEquals(next, drawable.getTransform());
    }

    @Test
    void testDrawDoesNotInvokePotentiallyExpensiveMethods() {
        PlaceholderDrawable drawable = new PlaceholderDrawable(new Transform(2, 3, 4, 5));
        GraphicsContext context = mock(GraphicsContext.class);
        context.begin();
        drawable.draw(context);
        context.end();
        verify(context, times(1)).begin();
        verify(context, never()).drawSprite(anyString(), any(Transform.class), anyFloat(), anyFloat(), anyInt());
        verify(context, times(1)).end();
    }
}