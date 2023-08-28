package systems;

import components.MutablePositionComponent;
import entities.Entity;
import entities.Player;
import graphics.drawable.Drawable;
import graphics.GraphicsContext;
import graphics.Presenter;

import java.util.Collection;

public class RenderingSystem {

    private final Presenter<? extends Drawable> spritePresenter;
    private final GraphicsContext graphicsContext;

    public RenderingSystem(
        GraphicsContext graphicsContext,
        Presenter<? extends Drawable> spritePresenter
    ) {
        this.graphicsContext = graphicsContext;
        this.spritePresenter = spritePresenter;
    }

    public void update(Collection<? extends Entity> entities, float deltaTime) {
        if(entities == null)
            return;
        graphicsContext.begin();
        for(Entity entity : entities)
            for(Drawable drawable : spritePresenter.present(entity))
                drawable.draw(graphicsContext);
        graphicsContext.end();
    }

    public void updatePlayer(Player player, float deltaTime) {
        MutablePositionComponent position = player.mutablePositionComponent;
        graphicsContext.placeCamera(position.getX(), position.getY());
    }

}
