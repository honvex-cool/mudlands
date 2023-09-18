package graphics;

import components.Component;
import entities.Entity;
import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import entities.passives.Tree;
import graphics.drawable.Drawable;

import java.util.List;

public class DrawablePresenter implements Presenter<Drawable> {
    @Override
    public List<Drawable> present(Entity entity) {
        String mainSpriteName = extractSpriteName(entity);
        int basisLayer = 3 * getLogicalLayer(entity);
        DrawableBuilder builder = new DrawableBuilder(
            mainSpriteName,
            basisLayer,
            0.1f,
            entity instanceof Mob,
            0.5f
        );
        for(Component component : entity.viewComponents())
            component.accept(builder);
        return List.of(builder.build());
    }

    private String extractSpriteName(Entity entity) {
        return "textures/" + entity.getLogicalName();
    }

    private static int getLogicalLayer(Object holder) {
        if(holder instanceof Ground)
            return 0;
        if(holder instanceof Tree)
            return 4;
        if(holder instanceof Passive)
            return 1;
        if(holder instanceof Player)
            return 2;
        if(holder instanceof Mob)
            return 3;
        return 5;
    }
}
