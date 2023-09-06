package actions;

import openable.items.Item;

import java.io.Serializable;
import java.util.Objects;

public final class GameTimer implements Serializable {
    private final float duration;
    private float remaining;

    private GameTimer(float duration) {
        if(duration < 0)
            throw new IllegalArgumentException("`duration` must not be negative");
        remaining = this.duration = duration;
    }

    public float advance(float time) {
        float result = remaining >= time ? 0 : Math.max(time - remaining, 0);
        remaining = Math.max(remaining - time, 0);
        return result;
    }

    public void restart() {
        remaining = duration;
    }

    public boolean use() {
        if(!isFinished())
            return false;
        restart();
        return true;
    }

    public boolean use(float time) {
        advance(time);
        return use();
    }

    public void finish() {
        remaining = 0;
    }

    public boolean isFinished() {
        return remaining == 0;
    }

    public float getProgress() {
        return 1 - remaining / duration;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof GameTimer other)
            return Objects.equals(duration, other.duration) && Objects.equals(remaining, other.remaining);
        return false;
    }

    public static GameTimer started(float duration) {
        return new GameTimer(duration);
    }

    public static GameTimer finished(float duration) {
        GameTimer timer = started(duration);
        timer.finish();
        return timer;
    }

    public void useItem(Item item) {
        if(isFinished())
            item.damageItem();
    }
}
