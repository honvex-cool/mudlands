package components;

import java.io.Serializable;

public interface Component extends Serializable {
    void accept(ComponentVisitor visitor);
}