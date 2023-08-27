package components;

public interface RotationComponent extends Component {
    float getRotation();

    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
