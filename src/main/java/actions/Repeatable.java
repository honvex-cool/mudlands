package actions;

import org.jetbrains.annotations.NotNull;

public class Repeatable {
    private final Cooldown cooldown;
    private final Runnable runnable;

    private Repeatable(Cooldown cooldown, @NotNull Runnable runnable) {
        this.cooldown = cooldown;
        this.runnable = runnable;
    }

    public void update(float time) {
        if(cooldown.use(time))
            runnable.run();
    }

    public static Repeatable notReady(float interval, Runnable runnable) {
        return new Repeatable(Cooldown.notReadyToUse(interval), runnable);
    }
}
