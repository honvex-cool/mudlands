package components;

import actions.GameTimer;
import actions.Repeatable;

public class DecreasingHungerComponent implements HungerComponent {
    private final MutableVital hunger;
    private final GameTimer fullnessTimer;
    private final GameTimer decreaseTimer;
    private final int decreaseAmount;

    public DecreasingHungerComponent(int total, float fullnessTime, float decreaseTime, int decreaseAmount) {
        hunger = new MutableVital(total);
        fullnessTimer = GameTimer.started(fullnessTime);
        decreaseTimer = GameTimer.started(decreaseTime);
        this.decreaseAmount = decreaseAmount;
    }

    public void update(float time) {
        float remaining = fullnessTimer.advance(time);
        Repeatable.repeatWithTimer(() -> hunger.damage(decreaseAmount), decreaseTimer, remaining);
    }

    public void fix(int amount) {
        hunger.fix(amount);
        if(Vital.isSatisfied(hunger))
            reset();
    }

    private void reset() {
        fullnessTimer.restart();
        decreaseTimer.restart();
    }

    @Override
    public int getCurrentPoints() {
        return hunger.getCurrentPoints();
    }

    @Override
    public int getMaxPoints() {
        return hunger.getMaxPoints();
    }
}
