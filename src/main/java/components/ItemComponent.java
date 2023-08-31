package components;

import openable.items.Item;

public interface ItemComponent extends Component {
    Class<? extends Item> rightHandItem();

    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
