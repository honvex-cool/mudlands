package graphics;

import components.*;
import graphics.drawable.*;
import utils.Pair;

class DrawableBuilder implements ComponentVisitor {
    private final LocalizedSprite mainSprite;
    private LocalizedSprite itemSprite = null;
    private Drawable healthBar = null;
    private Drawable staminaBar = null;
    private Drawable hungerBar = null;
    private float actionProgress = 0;

    private final int basisLayer;
    private final float barHeight;
    private final boolean isMob;
    private final float itemSize;

    public DrawableBuilder(
        String mainSpriteName,
        int basisLayer,
        float barHeight,
        boolean isMob,
        float itemSize
    ) {
        this.basisLayer = basisLayer;
        mainSprite = new LocalizedSprite(mainSpriteName, new Transform(0, 0, 1, 1));
        mainSprite.setLayer(basisLayer);
        this.barHeight = barHeight;
        this.isMob = isMob;
        this.itemSize = itemSize;
    }

    public Drawable build() {
        Drawable drawable = mainSprite;
        if(itemSprite != null && !isMob)
            drawable = Stacked.withCommonCenter(drawable, itemSprite);
        if(staminaBar != null)
            drawable = Stacked.verticallyCentered(drawable, staminaBar);
        if(hungerBar != null)
            drawable = Stacked.verticallyCentered(drawable, hungerBar);
        if(healthBar != null)
            drawable = Stacked.verticallyCentered(drawable, healthBar);
        if(itemSprite != null && isMob) {
            directItemAlongMainDirection();
            drawable = Stacked.grouped(drawable, itemSprite);
        }
        return drawable;
    }

    @Override
    public void visit(PositionComponent positionComponent) {
        float x = positionComponent.getX();
        float y = positionComponent.getY();
        if(isMob) {
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
        var itemType = itemComponent.mainItem();
        if(itemType == null)
            return;
        String name = itemType.getSimpleName();
        String itemName = "inventory/" + name.substring(0, name.indexOf("Item"));
        itemSprite = new LocalizedSprite(itemName, new Transform(0, 0, itemSize, itemSize));
        itemSprite.setLayer(basisLayer + 2);
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

    public void visit(ActionComponent actionComponent) {
        actionProgress = actionComponent.getProgress();
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

    private void directItemAlongMainDirection() {
        Transform mainTransform = mainSprite.getTransform();
        Transform itemTransform = itemSprite.getTransform();
        float mainRadius = mainTransform.width() / 2;
        float itemRadius = itemTransform.width() * (float)Math.sqrt(2) / 2;
        float totalRadius = mainRadius + itemRadius;
        Pair<Float, Float> center = mainTransform.center();
        float rotation = mainSprite.getRotation() + 90;
        double inRadians = Math.toRadians(rotation);
        float x = (float)(center.getFirst() + totalRadius * Math.cos(inRadians)) - itemTransform.width() / 2;
        float y = (float)(center.getSecond() + totalRadius * Math.sin(inRadians)) - itemTransform.height() / 2;
        itemSprite.setTransform(itemTransform.withPosition(x, y));
        itemSprite.setRotation(rotation - 45 + (float)Math.sin(actionProgress * Math.PI) * 15);
    }
}
