package components;

import actions.ActionType;

public interface ActionComponent extends Component {
    ActionType getActionType();
    float getProgress();

    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
