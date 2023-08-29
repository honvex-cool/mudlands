package components;

public class MutableHealthComponent extends MutableVital implements HealthComponent {
    public MutableHealthComponent(int maxHp) {
        super(maxHp);
    }
}
