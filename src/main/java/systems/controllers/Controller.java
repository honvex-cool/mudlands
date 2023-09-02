package systems.controllers;

import entities.mobs.Mob;

public interface Controller {
    void control(Mob mob);
    boolean canControl(Class<? extends Mob> mobClass);
}
