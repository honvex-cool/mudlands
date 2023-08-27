package systems;

import components.MutablePositionComponent;
import components.PositionComponent;
import entities.*;
import entities.grounds.Ground;
import entities.passives.Passive;
import generator.WorldLoader;
import utils.Config;
import utils.Pair;
import utils.SaveStruct;

import java.util.*;

public class ChunkManagerSystem{
    private WorldLoader worldLoader;
    private UniversalLoader entityLoader;
    private Pair<Integer, Integer> central_chunk_coordinates; //central chunk coordinates
    private Player player;
    private MutablePositionComponent player_position;
    private Set<Pair<Integer,Integer>> loaded;

    public ChunkManagerSystem(Player player,WorldLoader worldLoader, UniversalLoader entityLoader){
        this.player = player;
        this.player_position = player.mutablePositionComponent;
        //in unloaded world player chunk won't match central_chunk_coordinates, used instead of initial loading
        central_chunk_coordinates = new Pair<>(PositionComponent.getChunkX(player_position)+1, PositionComponent.getChunkY(player_position));
        this.worldLoader = worldLoader;
        this.entityLoader = entityLoader;
        loaded = new HashSet<>();
    }

    private void unload(Map<Pair<Integer,Integer>, Ground> grounds, Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs, Set<Pair<Integer,Integer>> unloading_chunks){
        Set<Mob> to_remove = new HashSet<>();

        for(var pair:unloading_chunks) {
            Map<Pair<Integer,Integer>,SaveStruct> passivesToSave = new HashMap<>();
            Set<SaveStruct> mobsToSave = new HashSet<>();
            for(int dx=0;dx<Config.CHUNK_SIZE;dx++) {
                for(int dy=0;dy<Config.CHUNK_SIZE;dy++){
                    Pair<Integer,Integer> curr = new Pair<>(pair.getFirst()*Config.CHUNK_SIZE+dx,pair.getSecond()*Config.CHUNK_SIZE+dy);
                    if(passives.containsKey(curr)){
                        Passive passive = passives.get(curr);
                        passivesToSave.put(curr, entityLoader.savePassive(passive));
                    }
                    grounds.remove(curr);
                    passives.remove(curr);
                }
            }

            for(Mob mob:mobs) {
                if(PositionComponent.getChunk(mob.mutablePositionComponent).equals(pair)){
                    to_remove.add(mob);
                    SaveStruct struct = entityLoader.saveMob(mob);
                    mobsToSave.add(struct);
                }
            }

            worldLoader.saveChunk(pair,passivesToSave,mobsToSave);
        }

        mobs.removeAll(to_remove);
    }

    public void load(Map<Pair<Integer,Integer>, Ground> grounds, Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs, Set<Pair<Integer,Integer>> loading_chunks){
        for(var pair:loading_chunks){
            var set = worldLoader.loadChunk(pair);
            for(SaveStruct struct:set){
                switch(struct.entityTag){
                    case GROUND -> grounds.put(new Pair<>((int)Math.floor(struct.x),(int)Math.floor(struct.y)),entityLoader.loadGround(struct));
                    case PASSIVE -> passives.put(new Pair<>((int)Math.floor(struct.x),(int)Math.floor(struct.y)),entityLoader.loadPassive(struct));
                    //case MOB -> passives.add(new Mob(struct));
                }
            }
        }
    }
    public void update(Map<Pair<Integer,Integer>, Ground> grounds, Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs) {

        handleDestroyed(passives,mobs);

        var curr_chunk = PositionComponent.getChunk(player_position);
        if(curr_chunk.equals(central_chunk_coordinates)) {
            return;
        }
        Set<Pair<Integer,Integer>> expected = getSurroundingChunks(curr_chunk);
        Set<Pair<Integer,Integer>> unloading_chunks,loading_chunks;

        unloading_chunks = new HashSet<>(loaded);
        unloading_chunks.removeAll(expected);

        unload(grounds,passives,mobs,unloading_chunks);

        loading_chunks = new HashSet<>(expected);
        loading_chunks.removeAll(loaded);

        load(grounds,passives,mobs,loading_chunks);

        central_chunk_coordinates = curr_chunk;
        loaded = expected;
    }

    public void unloadAll(Map<Pair<Integer,Integer>, Ground> grounds, Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        handleDestroyed(passives,mobs);
        unload(grounds,passives,mobs,loaded);
    }
    private Set<Pair<Integer,Integer>> getSurroundingChunks(Pair<Integer,Integer> center){
        Set<Pair<Integer,Integer>> set = new HashSet<>();
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                set.add(new Pair<>(center.getFirst()+i,center.getSecond()+j));
            }
        }
        return set;
    }

    private void handleDestroyed(Map<Pair<Integer,Integer>, Passive> passives, Collection<Mob> mobs){
        Set<Pair<Integer,Integer>> passivesToDelete = new HashSet<>();
        for(var key:passives.keySet()) {
            //passives.get(key).react(ActionType.HIT,player);
            if(passives.get(key).isDestroyed()){
                SaveStruct struct = passives.get(key).getSuccessor();
                switch(struct.entityTag){
                    case PASSIVE -> {
                        passives.put(key,entityLoader.loadPassive(struct));
                        continue;
                    }
                    case MOB -> mobs.add(entityLoader.loadMob(struct));
                }
                passivesToDelete.add(key);
            }
        }
        for(var key:passivesToDelete) {
            passives.remove(key);
        }
    }
}
