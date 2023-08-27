package components;

public interface Component {
    void accept(ComponentVisitor visitor);
}