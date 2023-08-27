package graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import components.Component;
import components.ComponentHolder;
import components.ComponentVisitor;
import entities.Mob;
import utils.AssetManager;

import java.util.List;

public class SpritePresenter implements Presenter<Sprite> {
    private final AssetManager assetManager;

    public SpritePresenter(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public List<Sprite> present(ComponentHolder holder) {
        Sprite sprite = assetManager.getSprite(extractSpriteName(holder));
        ComponentVisitor visitor = new InfoBuildingComponentVisitor(sprite);
        for(Component component : holder.viewComponents())
            component.accept(visitor);
        if(holder instanceof Mob)
            sprite.setPosition(sprite.getX() - 0.5f, sprite.getY() - 0.5f);
        return List.of(sprite);
    }

    private String extractSpriteName(ComponentHolder holder) {
        return holder.getClass().getSimpleName().toLowerCase();
    }
}
