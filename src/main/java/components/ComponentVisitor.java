package components;

public interface ComponentVisitor {
    void visit(PositionComponent component);
    void visit(RotationComponent component);
    void visit(VelocityComponent component);
    void visit(HealthComponent healthComponent);
}
