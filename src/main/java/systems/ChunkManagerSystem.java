package systems;

import components.PositionComponent;
import entities.*;
import generator.WorldLoader;
import utils.Config;
import utils.Pair;
import utils.SaveStruct;
import entities.EntityTag;

import java.util.*;

public class ChunkManagerSystem{
    private WorldLoader worldLoader;
    private Pair<Integer, Integer> central_chunk_coordinates; //central chunk coordinates
    private Player player;
    private PositionComponent player_position;
    private Set<Pair<Integer,Integer>> loaded;

    public ChunkManagerSystem(Player player,WorldLoader worldLoader){
        this.player = player;
        this.player_position = player.positionComponent;
        //in unloaded world player chunk won't match central_chunk_coordinates, used instead of initial loading
        central_chunk_coordinates = new Pair<>(player_position.getChunkX()+1,player_position.getChunkY());
        this.worldLoader = worldLoader;
        loaded = new HashSet<>();
    }
    public void update(Map<Pair<Integer,Integer>,Ground> grounds, Map<Pair<Integer,Integer>,Passive> passives, Collection<Mob> mobs) {
        var curr_chunk = player_position.getChunk();
        if(curr_chunk.equals(central_chunk_coordinates)) {
            return;
        }
        Set<Pair<Integer,Integer>> expected = getSurroundingChunks(curr_chunk);
        Set<Pair<Integer,Integer>> unloading_chunks,loading_chunks;

        unloading_chunks = new HashSet<>(loaded);
        unloading_chunks.removeAll(expected);

        Set<Mob> to_remove = new HashSet<>();

        for(var pair:unloading_chunks) {
            Map<Pair<Integer,Integer>,SaveStruct> passivesToSave = new HashMap<>();
            Set<SaveStruct> mobsToSave = new HashSet<>();
            for(int dx=0;dx<Config.CHUNK_SIZE;dx++) {
                for(int dy=0;dy<Config.CHUNK_SIZE;dy++){
                    Pair<Integer,Integer> curr = new Pair<>(pair.getFirst()*Config.CHUNK_SIZE+dx,pair.getSecond()*Config.CHUNK_SIZE+dy);
                    if(passives.containsKey(curr)){
                        Passive passive = passives.get(curr);
                        passivesToSave.put(curr,new SaveStruct(EntityTag.PASSIVE,passive.type,(float)Math.floor(passive.positionComponent.getX()),(float)Math.floor(passive.positionComponent.getY()),passive.getSaveMap()));
                    }
                    grounds.remove(curr);
                    passives.remove(curr);
                }
            }

            for(Mob mob:mobs) {
                if(mob.positionComponent.getChunk().equals(pair)){
                    to_remove.add(mob);
                    mobsToSave.add(new SaveStruct(EntityTag.MOB,mob.type,mob.positionComponent.getX(),mob.positionComponent.getY(),mob.getSaveMap()));
                }
            }

            worldLoader.saveChunk(pair,passivesToSave,mobsToSave);
        }

        mobs.removeAll(to_remove);

        loading_chunks = new HashSet<>(expected);
        loading_chunks.removeAll(loaded);

        for(var pair:loading_chunks){
            var set = worldLoader.loadChunk(pair);
            for(SaveStruct struct:set){
                switch(struct.entityTag){
                    case GROUND -> grounds.put(new Pair<>((int)Math.floor(struct.x),(int)Math.floor(struct.y)),new Ground(struct));
                    case PASSIVE -> passives.put(new Pair<>((int)Math.floor(struct.x),(int)Math.floor(struct.y)),new Passive(struct));
                    //case MOB -> passives.add(new Mob(struct));
                }
            }
        }
        central_chunk_coordinates = curr_chunk;
        loaded = expected;
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
}
