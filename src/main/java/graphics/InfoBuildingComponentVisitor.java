package graphics;

import components.ComponentVisitor;
import components.PositionComponent;
import components.RotationComponent;
import components.VelocityComponent;
import graphics.drawable.Transform;

public class InfoBuildingComponentVisitor implements ComponentVisitor {
    private float x;
    private float y;
    private float rotation;

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

    public Transform buildTransform() {
        return new Transform(x, y, 1f, 1f);
    }

    public float getRotation() {
        return rotation;
    }
}
