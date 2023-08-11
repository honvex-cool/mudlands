package actions;

public class Cooldown {
    private final float duration;

    private float remaining = 0.0f;

    public Cooldown(float duration) {
        this.duration = duration;
    }

    public void advance(float time) {
        remaining = Math.max(remaining - time, 0f);
    }

    public void reset() {
        remaining = duration;
    }

    public boolean isReady() {
        return remaining == 0f;
    }

    public boolean use() {
        if(!isReady())
            return false;
        reset();
        return true;
    }
}
