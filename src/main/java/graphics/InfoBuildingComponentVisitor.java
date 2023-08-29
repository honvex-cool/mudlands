package graphics;

import components.*;
import graphics.drawable.Transform;

public class InfoBuildingComponentVisitor implements ComponentVisitor {
    private float x;
    private float y;
    private float rotation;
    private HealthComponent healthComponent = null;

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

    public Transform buildTransform() {
        return new Transform(x, y, 1f, 1f);
    }

    public float getRotation() {
        return rotation;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }
}
