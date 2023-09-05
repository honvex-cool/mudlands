package graphics;

import components.Component;
import components.ComponentHolder;
import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import entities.passives.Tree;
import graphics.drawable.Drawable;

import java.util.List;

public class DrawablePresenter implements Presenter<Drawable> {
    @Override
    public List<Drawable> present(ComponentHolder holder) {
        String mainSpriteName = extractSpriteName(holder);
        int basisLayer = 2 * getLogicalLayer(holder);
        DrawableBuilder builder = new DrawableBuilder(
            mainSpriteName,
            basisLayer,
            0.1f,
            holder instanceof Mob
        );
        for(Component component : holder.viewComponents())
            component.accept(builder);
        return List.of(builder.build());
    }

    private String extractSpriteName(ComponentHolder holder) {
        return "textures/" + holder.getClass().getSimpleName().toLowerCase();
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
