package systems;

import components.MutablePositionComponent;
import entities.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import graphics.GraphicsContext;
import graphics.Presenter;
import graphics.drawable.Drawable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;

class RenderingSystemTest {
    GraphicsContext graphicsContext;
    Presenter<Drawable> presenter;
    RenderingSystem renderingSystem;

    Player player;
    Drawable playerDrawable;

    Ground ground;
    Drawable groundDrawable;

    Passive passive;
    Drawable passiveDrawable;

    Mob mob;
    Drawable mobDrawable;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        graphicsContext = mock(GraphicsContext.class);

        presenter = (Presenter<Drawable>)mock(Presenter.class);

        player = new Player();
        player.mutablePositionComponent = new MutablePositionComponent(7, 42);
        playerDrawable = mock(Drawable.class);

        ground = mock(Ground.class);
        groundDrawable = mock(Drawable.class);

        passive = mock(Passive.class);
        passiveDrawable = mock(Drawable.class);

        mob = mock(Mob.class);
        mobDrawable = mock(Drawable.class);

        when(presenter.present(player)).thenReturn(List.of(playerDrawable));
        when(presenter.present(ground)).thenReturn(List.of(groundDrawable));
        when(presenter.present(passive)).thenReturn(List.of(passiveDrawable));
        when(presenter.present(mob)).thenReturn(List.of(mobDrawable));

        renderingSystem = new RenderingSystem(
            graphicsContext,
            presenter,
            player,
            List.of(ground),
            List.of(passive),
            List.of(mob)
        );
    }

    @Test
    void testUpdatePresentsAndDraws() {
        renderingSystem.update();

        verify(presenter).present(player);
        verify(presenter).present(ground);
        verify(presenter).present(passive);
        verify(presenter).present(mob);

        verify(playerDrawable).draw(graphicsContext);
        verify(groundDrawable).draw(graphicsContext);
        verify(passiveDrawable).draw(graphicsContext);
        verify(mobDrawable).draw(graphicsContext);
    }

    @Test
    void testUpdateHandlesContext() {
        renderingSystem.update();

        verify(graphicsContext).placeCamera(7, 42);

        InOrder order = inOrder(graphicsContext);
        order.verify(graphicsContext).begin();
        order.verify(graphicsContext).end();
    }
}