package graphics;

import components.Component;
import components.ComponentHolder;
import entities.Mob;
import graphics.drawable.Drawable;
import graphics.drawable.LocalizedSprite;
import graphics.drawable.Transform;
import utils.AssetManager;

import java.util.List;

public class DrawablePresenter implements Presenter<Drawable> {
    private final AssetManager assetManager;

    public DrawablePresenter(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public List<Drawable> present(ComponentHolder holder) {
        InfoBuildingComponentVisitor builder = new InfoBuildingComponentVisitor();
        for(Component component : holder.viewComponents())
            component.accept(builder);
        Transform transform = builder.buildTransform();
        float rotation = builder.getRotation();
        if(holder instanceof Mob) {
            transform = transform.shifted(-0.5f, -0.5f);
            rotation -= 90f;
        }
        Drawable drawable = new LocalizedSprite(assetManager.getSprite(extractSpriteName(holder)), transform, rotation);
        return List.of(drawable);
    }

    private String extractSpriteName(ComponentHolder holder) {
        return holder.getClass().getSimpleName().toLowerCase();
    }
}
