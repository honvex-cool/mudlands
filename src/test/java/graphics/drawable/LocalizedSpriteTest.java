package graphics.drawable;

import graphics.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LocalizedSpriteTest {
    @Test
    void testDefaultParametersAreNoRotationOnTheBaseLayerAndFullOpacity() {
        LocalizedSprite sprite = new LocalizedSprite("MySprite", new Transform(2, 3, 4, 5));
        assertEquals(0, sprite.getRotation());
        assertEquals(1, sprite.getAlpha());
        assertEquals(0, sprite.getLayer());
    }

    @Test
    void testParametersCanBeAdjustedAfterConstruction() {
        LocalizedSprite sprite = new LocalizedSprite("MySprite", new Transform(2, 3, 4, 5));
        sprite.setRotation(42);
        sprite.setAlpha(0.12f);
        sprite.setLayer(-3);
        assertEquals(42, sprite.getRotation());
        assertEquals(0.12f, sprite.getAlpha());
        assertEquals(-3, sprite.getLayer());
    }

    @Test
    void testAlphaBelowZeroOrAboveOneIsNotAllowed() {
        LocalizedSprite sprite = new LocalizedSprite("MySprite", new Transform(2, 3, 4, 5));
        sprite.setAlpha(0.12f);
        assertThrows(IllegalArgumentException.class, () -> sprite.setAlpha(-0.5f));
        assertEquals(0.12f, sprite.getAlpha());
        assertThrows(IllegalArgumentException.class, () -> sprite.setAlpha(1.5f));
        assertEquals(0.12f, sprite.getAlpha());
    }

    @Test
    void testSpriteForwardsItsParametersToTheGraphicsContextWhenDrawn() {
        Transform transform = new Transform(2, 3, 4, 5);
        LocalizedSprite sprite = new LocalizedSprite("MySprite", transform);
        GraphicsContext context = mock(GraphicsContext.class);

        sprite.setRotation(42);
        sprite.setAlpha(0.12f);
        sprite.setLayer(-3);

        context.begin();
        sprite.draw(context);
        context.end();

        verify(context).drawSprite("MySprite", transform, 42, 0.12f, -3);
    }
}