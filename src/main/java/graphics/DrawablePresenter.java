package graphics;

import components.Component;
import components.ComponentHolder;
import components.HealthComponent;
import entities.mobs.Mob;
import entities.Player;
import entities.grounds.Ground;
import entities.passives.Passive;
import entities.passives.Tree;
import graphics.drawable.Drawable;
import graphics.drawable.LocalizedSprite;
import graphics.drawable.Stacked;
import graphics.drawable.Transform;

import java.util.List;

public class DrawablePresenter implements Presenter<Drawable> {
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
        LocalizedSprite holderSprite = new LocalizedSprite(extractSpriteName(holder), transform);
        holderSprite.setRotation(rotation);
        int layer = getLayer(holder);
        holderSprite.setLayer(layer);
        Drawable drawable = holderSprite;
        Float staminaFraction = builder.getStaminaFraction();
        if(staminaFraction != null) {
            Transform barTransform = new Transform(0, 0, staminaFraction, 0.2f);
            LocalizedSprite bar = new LocalizedSprite("textures/staminaBar", barTransform);
            bar.setLayer(layer);
            drawable = Stacked.verticallyCentered(drawable, bar);
        }
        String itemName = builder.getItemName();
        if(itemName != null) {
            LocalizedSprite itemSprite = new LocalizedSprite(itemName, transform.scaled(0.4f, 0.4f));
            itemSprite.setLayer(6);
            drawable = Stacked.horizontallyTopAligned(drawable, itemSprite);
        }
        HealthComponent health = builder.getHealthComponent();
        if(health != null && health.getCurrentPoints() != health.getMaxPoints()) {
            float greenFraction = (float)health.getCurrentPoints() / health.getMaxPoints();
            float redFraction = 1 - greenFraction;
            Transform greenTransform = new Transform(0, 0, greenFraction, 0.1f);
            Transform redTransform = new Transform(0, 0, redFraction, 0.1f);
            LocalizedSprite green = new LocalizedSprite("textures/healthBarGreen", greenTransform);
            green.setLayer(layer);
            LocalizedSprite red = new LocalizedSprite("textures/healthBarRed", redTransform);
            red.setLayer(layer);
            Drawable healthBar = Stacked.horizontallyCentered(green, red);
            drawable = Stacked.verticallyCentered(drawable, healthBar);
        }
        return List.of(drawable);
    }

    private String extractSpriteName(ComponentHolder holder) {
        return "textures/" + holder.getClass().getSimpleName().toLowerCase();
    }

    private static int getLayer(Object holder) {
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
