package systems;

import entities.Player;
import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.passives.Passive;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

class UpdateSystemTest {
    @Test
    public void testUpdateSystem(){
        Player playerMock = mock(Player.class);
        Ground groundMock = mock(Ground.class);
        Passive passiveMock = mock(Passive.class);
        Mob mobMock = mock(Mob.class);
        Collection<Ground> grounds = List.of(groundMock);
        Collection<Passive> passives = List.of(passiveMock);
        Collection<Mob> mobs = List.of(mobMock);
        UpdateSystem updateSystem = new UpdateSystem(playerMock,grounds,passives,mobs);

        updateSystem.update(2f);

        verify(playerMock,times(1)).update(2f);
        verify(groundMock).update(2f);
        verify(passiveMock).update(2f);
        verify(mobMock).update(2f);
    }
}