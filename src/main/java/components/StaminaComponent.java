package components;

public interface StaminaComponent extends Vital, Component {
    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
