package graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import components.Component;
import components.ComponentHolder;
import components.HealthComponent;
import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import graphics.drawable.Drawable;
import graphics.drawable.LocalizedSprite;
import graphics.drawable.Stacked;
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
        int layer = getLayer(holder);
        Drawable drawable = new LocalizedSprite(
            transform,
            assetManager.getSprite(extractSpriteName(holder)),
            layer,
            rotation
        );
        Float staminaFraction = builder.getStaminaFraction();
        if(staminaFraction != null) {
            Transform barTransform = new Transform(0, 0, staminaFraction, 0.2f);
            Drawable bar = new LocalizedSprite(
                barTransform,
                assetManager.getSprite("staminaBar"),
                4
            );
            drawable = Stacked.verticallyCentered(drawable, bar);
        }
        String itemName = builder.getItemName();
        if(itemName != null) {
            Drawable item = new LocalizedSprite(
                transform.scaled(0.4f, 0.4f),
                new Sprite(assetManager.getInventoryTexture(itemName)),
                5,
                0
            );
            drawable = Stacked.horizontallyTopAligned(drawable, item);
        }
        HealthComponent health = builder.getHealthComponent();
        if(health != null && health.getCurrentPoints() != health.getMaxPoints()) {
            float greenFraction = (float)health.getCurrentPoints() / health.getMaxPoints();
            float redFraction = 1 - greenFraction;
            Transform greenTransform = new Transform(0, 0, greenFraction, 0.1f);
            Transform redTransform = new Transform(0, 0, redFraction, 0.1f);
            Drawable green = new LocalizedSprite(greenTransform, assetManager.getSprite("healthBarGreen"), 4);
            Drawable red = new LocalizedSprite(redTransform, assetManager.getSprite("healthBarRed"), 4);
            Drawable healthBar = Stacked.horizontallyCentered(green, red);
            drawable = Stacked.verticallyCentered(drawable, healthBar);
        }
        return List.of(drawable);
    }

    private String extractSpriteName(ComponentHolder holder) {
        return holder.getClass().getSimpleName().toLowerCase();
    }

    private static int getLayer(Object holder) {
        if(holder instanceof Ground)
            return 0;
        if(holder instanceof Passive)
            return 1;
        if(holder instanceof Player)
            return 3;
        if(holder instanceof Mob)
            return 2;
        return 4;
    }
}
