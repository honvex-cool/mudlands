package components;

public interface HealthComponent extends Vital, Component {
    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
