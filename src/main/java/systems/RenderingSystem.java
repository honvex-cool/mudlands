package systems;

import components.MutablePositionComponent;
import entities.Entity;
import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import graphics.drawable.Drawable;
import graphics.GraphicsContext;
import graphics.Presenter;

import java.util.Collection;
import java.util.List;

public class RenderingSystem {

    private final Presenter<? extends Drawable> spritePresenter;
    private final GraphicsContext graphicsContext;
    private final Player player;
    private final Collection<Ground> grounds;
    private final Collection<Passive> passives;
    private final Collection<Mob> mobs;

    public RenderingSystem(
        GraphicsContext graphicsContext,
        Presenter<? extends Drawable> spritePresenter,
        Player player,
        Collection<Ground> grounds,
        Collection<Passive> passives,
        Collection<Mob> mobs
    ) {
        this.graphicsContext = graphicsContext;
        this.spritePresenter = spritePresenter;
        this.player = player;
        this.grounds = grounds;
        this.passives = passives;
        this.mobs = mobs;
    }

    public void update() {
        updatePlayer();
        graphicsContext.begin();
        renderAll(List.of(player));
        renderAll(grounds);
        renderAll(passives);
        renderAll(mobs);
        graphicsContext.end();
    }

    private void renderAll(Collection<? extends Entity> entities) {
        if(entities == null)
            return;
        for(Entity entity : entities)
            for(Drawable drawable : spritePresenter.present(entity))
                drawable.draw(graphicsContext);
    }

    private void updatePlayer() {
        MutablePositionComponent position = player.mutablePositionComponent;
        graphicsContext.placeCamera(position.getX(), position.getY());
    }
}
