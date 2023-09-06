package graphics;

import components.*;
import graphics.drawable.*;

class DrawableBuilder implements ComponentVisitor {
    private final LocalizedSprite mainSprite;
    private Drawable itemIcon = null;
    private Drawable healthBar = null;
    private Drawable staminaBar = null;
    private Drawable hungerBar = null;

    private final int basisLayer;
    private final float barHeight;
    private final boolean anchoredAtCenter;

    public DrawableBuilder(String mainSpriteName, int basisLayer, float barHeight, boolean anchoredAtCenter) {
        this.basisLayer = basisLayer;
        mainSprite = new LocalizedSprite(mainSpriteName, new Transform(0, 0, 1, 1));
        mainSprite.setLayer(basisLayer);
        this.barHeight = barHeight;
        this.anchoredAtCenter = anchoredAtCenter;
    }

    public Drawable build() {
        Drawable drawable = mainSprite;
        if(itemIcon != null)
            drawable = Stacked.withCommonCenter(drawable, itemIcon);
        if(staminaBar != null)
            drawable = Stacked.verticallyCentered(drawable, staminaBar);
        if(hungerBar != null)
            drawable = Stacked.verticallyCentered(drawable, hungerBar);
        if(healthBar != null)
            drawable = Stacked.verticallyCentered(drawable, healthBar);
        return drawable;
    }

    @Override
    public void visit(PositionComponent positionComponent) {
        float x = positionComponent.getX();
        float y = positionComponent.getY();
        if(anchoredAtCenter) {
            x -= 0.5f;
            y -= 0.5f;
        }
        mainSprite.setTransform(mainSprite.getTransform().withPosition(x, y));
    }

    @Override
    public void visit(RotationComponent rotationComponent) {
        mainSprite.setRotation(rotationComponent.getRotation() - 90);
    }

    @Override
    public void visit(HealthComponent healthComponent) {
        if(Vital.isSatisfied(healthComponent)) {
            healthBar = bar();
            return;
        }
        float fraction = Vital.asFraction(healthComponent);
        Drawable green = bar("textures/healthBarGreen", fraction);
        Drawable red = bar("textures/healthBarRed", 1 - fraction);
        healthBar = Stacked.horizontallyCentered(green, red);
    }

    @Override
    public void visit(ItemComponent itemComponent) {
        var rightHand = itemComponent.mainItem();
        if(rightHand == null)
            return;
        String name = rightHand.getSimpleName();
        String itemName = "inventory/" + name.substring(0, name.indexOf("Item"));
        LocalizedSprite itemSprite = new LocalizedSprite(itemName, new Transform(0, 0, 0.4f, 0.4f));
        itemSprite.setLayer(basisLayer + 1);
        itemIcon = itemSprite;
    }

    @Override
    public void visit(StaminaComponent staminaComponent) {
        staminaBar = bar("textures/staminaBar", Vital.asFraction(staminaComponent));
    }

    @Override
    public void visit(HungerComponent hungerComponent) {
        hungerBar = bar("textures/hungerBar", Vital.asFraction(hungerComponent));
    }

    @Override
    public void visit(VelocityComponent component) {
    }

    private Drawable bar() {
        return new PlaceholderDrawable(barTransform(1));
    }

    private Drawable bar(String spriteName, float width) {
        LocalizedSprite barSprite = new LocalizedSprite(spriteName, barTransform(width));
        barSprite.setLayer(basisLayer + 1);
        return barSprite;
    }

    private Transform barTransform(float width) {
        return new Transform(0, 0, width, barHeight);
    }
}
