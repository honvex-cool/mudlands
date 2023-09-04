package actions;

import java.io.Serializable;
import java.util.Objects;

public class Cooldown implements Serializable {
    private final float duration;

    private float remaining = 0.0f;

    private Cooldown(float duration) {
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

    public boolean use(float time) {
        advance(time);
        return use();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Cooldown other)
            return Objects.equals(duration, other.duration) && Objects.equals(remaining, other.remaining);
        return false;
    }

    public static Cooldown readyToUse(float duration) {
        return new Cooldown(duration);
    }

    public static Cooldown notReadyToUse(float duration) {
        Cooldown cooldown = readyToUse(duration);
        cooldown.reset();
        return cooldown;
    }
}
