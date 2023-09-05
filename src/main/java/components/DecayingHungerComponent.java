package components;

import actions.Cooldown;

public class DecayingHungerComponent implements HungerComponent {
    private final MutableVital hunger;
    private final Cooldown untilDecayCooldown;
    private final Cooldown decayCooldown;
    private final int decayAmount;
    private boolean decaying;

    public DecayingHungerComponent(int total, float untilDecayTime, float decayTime, int decayAmount) {
        this.hunger = new MutableVital(total);
        this.untilDecayCooldown = Cooldown.notReadyToUse(untilDecayTime);
        this.decayCooldown = Cooldown.notReadyToUse(decayTime);
        this.decayAmount = decayAmount;
        reset();
    }

    public void update(float time) {
        if(decaying && decayCooldown.use(time))
            hunger.damage(decayAmount);
        else if(untilDecayCooldown.use(time))
            decaying = true;
    }

    public void fix(int amount) {
        hunger.fix(amount);
        if(Vital.isSatisfied(hunger))
            reset();
    }

    private void reset() {
        decaying = false;
        untilDecayCooldown.reset();
        decayCooldown.reset();
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
