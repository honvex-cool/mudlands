package graphics;

import components.*;
import graphics.drawable.Transform;

public class InfoBuildingComponentVisitor implements ComponentVisitor {
    private float x;
    private float y;
    private float rotation;
    private HealthComponent healthComponent = null;
    private Float staminaFraction = null;
    private String itemName = null;

    @Override
    public void visit(PositionComponent positionComponent) {
        x = positionComponent.getX();
        y = positionComponent.getY();
    }

    @Override
    public void visit(RotationComponent rotationComponent) {
        rotation = rotationComponent.getRotation();
    }

    @Override
    public void visit(VelocityComponent component) {
    }

    @Override
    public void visit(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }

    @Override
    public void visit(ItemComponent itemComponent) {
        var rightHand = itemComponent.rightHandItem();
        if(rightHand == null)
            return;
        String name = rightHand.getSimpleName();
        itemName = name.substring(0, name.indexOf("Item"));
    }

    @Override
    public void visit(StaminaComponent staminaComponent) {
        staminaFraction = Vital.asFraction(staminaComponent);
    }

    public Transform buildTransform() {
        return new Transform(x, y, 1f, 1f);
    }

    public float getRotation() {
        return rotation;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public String getItemName() {
        return itemName;
    }

    public Float getStaminaFraction() {
        return staminaFraction;
    }
}
