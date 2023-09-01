package actions;

import components.VelocityComponent;

import java.io.Serializable;
import java.util.function.Consumer;

public class Movement implements Serializable {
    private final VelocityComponent velocity;
    private final int availableStamina;
    private final Consumer<Integer> onAccept;
    private final Runnable onReject;

    public Movement(VelocityComponent velocity, int availableStamina, Consumer<Integer> onAccept) {
        this(velocity, availableStamina, onAccept, null);
    }

    public Movement(VelocityComponent velocity, int availableStamina, Consumer<Integer> onAccept, Runnable onReject) {
        this.velocity = velocity;
        this.availableStamina = availableStamina;
        this.onAccept = onAccept;
        this.onReject = onReject;
    }

    public VelocityComponent getVelocity() {
        return new VelocityComponent(velocity.getX(), velocity.getY());
    }

    public int getAvailableStamina() {
        return availableStamina;
    }

    public void accept(int cost) {
        if(onAccept != null)
            onAccept.accept(cost);
    }

    public void reject() {
        if(onReject != null)
            onReject.run();
    }
}
