package components;

public interface ComponentVisitor {
    void visit(PositionComponent component);
    void visit(RotationComponent component);
    void visit(VelocityComponent component);
    void visit(HealthComponent healthComponent);
    void visit(ItemComponent itemComponent);
    void visit(StaminaComponent staminaComponent);
    void visit(HungerComponent hungerComponent);
    void visit(ActionComponent actionComponent);
}
