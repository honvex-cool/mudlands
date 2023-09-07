package actions;

import org.jetbrains.annotations.NotNull;

public class Repeatable {
    private final GameTimer timer;
    private final Runnable runnable;

    private Repeatable(GameTimer timer, @NotNull Runnable runnable) {
        this.timer = timer;
        this.runnable = runnable;
    }

    public Repeatable(float interval, Runnable runnable) {
        this(GameTimer.started(interval), runnable);
    }

    public void update(float time) {
        while(time > 0) {
            time = timer.advance(time);
            if(timer.isFinished()) {
                runnable.run();
                timer.restart();
            }
        }
    }

    public static void repeatWithTimer(Runnable runnable, GameTimer timer, float time) {
        new Repeatable(timer, runnable).update(time);
    }
}
