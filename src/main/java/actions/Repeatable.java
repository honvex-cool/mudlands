package actions;

import org.jetbrains.annotations.NotNull;

public class Repeatable {
    private final GameTimer delayTimer;
    private final GameTimer timer;
    private final Runnable runnable;

    private Repeatable(GameTimer delayTimer, GameTimer timer, @NotNull Runnable runnable) {
        this.delayTimer = delayTimer;
        this.timer = timer;
        this.runnable = runnable;
    }

    public Repeatable(float interval, float delay, Runnable runnable) {
        this(GameTimer.started(delay), GameTimer.started(interval), runnable);
    }

    public Repeatable(float interval, Runnable runnable) {
        this(interval, 0, runnable);
    }

    public void update(float time) {
        time = delayTimer.advance(time);
        while(time > 0) {
            time = timer.advance(time);
            if(timer.isFinished()) {
                runnable.run();
                timer.restart();
            }
        }
    }

    public static void repeatWithTimer(Runnable runnable, GameTimer timer, float time) {
        new Repeatable(GameTimer.started(0), timer, runnable).update(time);
    }
}
