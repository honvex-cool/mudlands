package graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drawable.Transform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class GraphicsContextImplTest {
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    ResolutionProvider resolutionProvider;
    GraphicsContextImpl graphicsContext;

    @BeforeEach
    void setUp() {
        spriteBatch = mock(SpriteBatch.class);
        resolutionProvider = mock(ResolutionProvider.class);
        camera = mock(OrthographicCamera.class);
        graphicsContext = new GraphicsContextImpl(
            spriteBatch,
            camera,
            resolutionProvider,
            10
        );
    }

    @Test
    void testSpriteBatchIsOperatedInCorrectOrder() {
        Sprite sprite = mock(Sprite.class);
        graphicsContext.begin();
        graphicsContext.drawSprite(sprite, new Transform(), 90, 3);
        graphicsContext.end();

        InOrder order = inOrder(spriteBatch, sprite);
        order.verify(spriteBatch).begin();
        order.verify(sprite).draw(spriteBatch);
        order.verify(spriteBatch).end();
    }

    @Test
    void testSpritesAreDrawnInLayerOrder() {
        Sprite upper = mock(Sprite.class);
        Sprite lower = mock(Sprite.class);
        graphicsContext.begin();
        graphicsContext.drawSprite(upper, new Transform(), 0, 1);
        graphicsContext.drawSprite(lower, new Transform(), 0, 0);
        graphicsContext.end();

        InOrder order = inOrder(upper, lower, spriteBatch);
        order.verify(lower).draw(spriteBatch);
        order.verify(upper).draw(spriteBatch);
    }
}