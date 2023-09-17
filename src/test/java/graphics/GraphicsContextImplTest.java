package graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drawable.Transform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import utils.AssetManager;

import static org.mockito.Mockito.*;

class GraphicsContextImplTest {
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    ResolutionProvider resolutionProvider;
    AssetManager assetManager;
    GraphicsContextImpl graphicsContext;

    @BeforeEach
    void setUp() {
        spriteBatch = mock(SpriteBatch.class);
        resolutionProvider = mock(ResolutionProvider.class);
        camera = mock(OrthographicCamera.class);
        assetManager = mock(AssetManager.class);
        graphicsContext = new GraphicsContextImpl(
            spriteBatch,
            camera,
            resolutionProvider,
            assetManager,
            10
        );
    }

    @Test
    void testSpriteBatchIsOperatedInCorrectOrder() {
        Sprite mySprite = mock(Sprite.class);
        when(assetManager.getSprite("mySprite")).thenReturn(mySprite);

        graphicsContext.begin();
        graphicsContext.drawSprite("mySprite", new Transform(), 90, 1, 3);
        graphicsContext.end();

        InOrder order = inOrder(spriteBatch, mySprite);
        order.verify(spriteBatch).begin();
        order.verify(mySprite).draw(spriteBatch);
        order.verify(spriteBatch).end();
    }

    @Test
    void testSpritesAreDrawnInLayerOrder() {
        Sprite upper = mock(Sprite.class);
        when(assetManager.getSprite("upper")).thenReturn(upper);
        Sprite lower = mock(Sprite.class);
        when(assetManager.getSprite("lower")).thenReturn(lower);

        graphicsContext.begin();
        graphicsContext.drawSprite("upper", new Transform(), 0, 1, 1);
        graphicsContext.drawSprite("lower", new Transform(), 0, 1, -1);
        graphicsContext.end();

        InOrder order = inOrder(upper, lower, spriteBatch);
        order.verify(lower).draw(spriteBatch);
        order.verify(upper).draw(spriteBatch);
    }
}