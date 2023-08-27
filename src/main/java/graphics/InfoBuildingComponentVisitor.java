package graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import components.ComponentVisitor;
import components.PositionComponent;
import components.RotationComponent;
import components.VelocityComponent;

public class InfoBuildingComponentVisitor implements ComponentVisitor {
    private final Sprite sprite;

    public InfoBuildingComponentVisitor(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void visit(PositionComponent positionComponent) {
        sprite.setPosition(positionComponent.getX(), positionComponent.getY());
    }

    @Override
    public void visit(RotationComponent rotationComponent) {
        sprite.setRotation(-90f + rotationComponent.getRotation());
    }

    @Override
    public void visit(VelocityComponent component) {
    }
}
