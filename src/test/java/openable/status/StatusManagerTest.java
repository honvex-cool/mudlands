package openable.status;

import entities.Player;
import openable.items.armor.MudBootsItem;
import openable.items.tools.MudSwordItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusManagerTest {

    @Test
    void checkUpdateStatus() {
        Player player = new Player();
        StatusManager statusManager = new StatusManager(player.getInventory());
        player.getInventory().get(3, 7).setItem(new MudBootsItem());
        player.getInventory().get(4, 7).setItem(new MudSwordItem());
        assertEquals("None", statusManager.getBoots().toString());
        assertEquals("None", statusManager.getRightHand().toString());
        statusManager.updateStatus();
        assertEquals("None", statusManager.getHead().toString());
        assertEquals("None", statusManager.getChest().toString());
        assertEquals("None", statusManager.getLegs().toString());
        assertEquals("MudBoots", statusManager.getBoots().toString());
        assertEquals("MudSword", statusManager.getRightHand().toString());
        assertEquals("None", statusManager.getLeftHand().toString());
    }
}