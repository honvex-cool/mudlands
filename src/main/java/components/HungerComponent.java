package components;

public interface HungerComponent extends Vital, Component {
    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
