package systems;

import entities.Player;
import entities.grounds.Ground;
import entities.grounds.Water;
import entities.mobs.Mob;
import entities.mobs.Zombie;
import entities.passives.Passive;
import entities.passives.Tree;
import generator.WorldLoader;
import org.junit.jupiter.api.Test;
import utils.Config;
import utils.Pair;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChunkManagerSystemTest {

    @Test
    void testLoadChunk(){
        Player player = new Player();
        WorldLoader worldLoaderMock = mock(WorldLoader.class);
        Tree tree = new Tree();
        Zombie zombie = new Zombie();
        Water water = new Water();
        when(worldLoaderMock.loadChunk(new Pair<>(2,3))).thenReturn(Set.of(tree,zombie,water));
        Map<Pair<Integer,Integer>, Ground> grounds = new HashMap<>();
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Collection<Mob> mobs = new ArrayList<>();

        ChunkManagerSystem chunkManagerSystem = new ChunkManagerSystem(player,worldLoaderMock,grounds,passives,mobs);

        chunkManagerSystem.load(Set.of(new Pair<>(2,3)));

        assertTrue(grounds.containsValue(water));
        assertTrue(passives.containsValue(tree));
        assertTrue(mobs.contains(zombie));
    }

    @Test
    void testUnloadAll(){
        Player player = new Player();
        WorldLoader worldLoaderMock = mock(WorldLoader.class);
        Tree tree = new Tree();
        tree.mutablePositionComponent.setX(2* Config.CHUNK_SIZE);
        tree.mutablePositionComponent.setY(3*Config.CHUNK_SIZE);
        Zombie zombie = new Zombie();
        zombie.mutablePositionComponent.setX(2* Config.CHUNK_SIZE);
        zombie.mutablePositionComponent.setY(3*Config.CHUNK_SIZE);
        Water water = new Water();
        water.mutablePositionComponent.setX(2* Config.CHUNK_SIZE);
        water.mutablePositionComponent.setY(3*Config.CHUNK_SIZE);

        when(worldLoaderMock.loadChunk(new Pair<>(2,3))).thenReturn(Set.of(tree,zombie,water));
        Map<Pair<Integer,Integer>, Ground> grounds = new HashMap<>();
        Map<Pair<Integer,Integer>, Passive> passives = new HashMap<>();
        Collection<Mob> mobs = new ArrayList<>();

        ChunkManagerSystem chunkManagerSystem = new ChunkManagerSystem(player,worldLoaderMock,grounds,passives,mobs);

        chunkManagerSystem.load(Set.of(new Pair<>(2,3)));

        chunkManagerSystem.unloadAll();

        assertTrue(grounds.isEmpty());
        assertTrue(passives.isEmpty());
        assertTrue(mobs.isEmpty());
    }
}