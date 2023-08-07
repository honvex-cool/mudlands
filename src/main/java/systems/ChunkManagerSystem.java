package systems;

import components.Component;
import components.PlayerComponent;
import components.PositionComponent;
import entities.*;
import generator.FieldStruct;
import generator.WorldLoader;
import utils.Pair;
import utils.SaveStruct;
import world.EntityTag;

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
        central_chunk_coordinates = new Pair<>(player_position.getChunkX()+1,player_position.getChunkY());
        this.worldLoader = worldLoader;
        loaded = new HashSet<>();
    }
    public void update(Collection<Ground> grounds, Collection<Passive> passives, Collection<Mob> mobs) {
        var curr_chunk = player_position.getChunk();
        if(curr_chunk.equals(central_chunk_coordinates)) {
            return;
        }
        Set<Pair<Integer,Integer>> expected = getSurroundingChunks(curr_chunk);
        Set<Pair<Integer,Integer>> unloading_chunks,loading_chunks;

        unloading_chunks = new HashSet<>(loaded);
        unloading_chunks.removeAll(expected);

        Set<Entity> to_remove = new HashSet<>();
        for(var pair:unloading_chunks) {
            Set<SaveStruct> to_save = new HashSet<>();

            for(Ground ground:grounds) {
                if(ground.positionComponent.getChunk().equals(pair)){
                    to_remove.add(ground);
                }
            }

            for(Passive passive:passives) {
                if(passive.positionComponent.getChunk().equals(pair)){
                    to_remove.add(passive);
                    to_save.add(new SaveStruct(EntityTag.PASSIVE,passive.type,passive.positionComponent.getX(),passive.positionComponent.getY(),passive.getSaveMap()));
                }
            }

            for(Mob mob:mobs) {
                if(mob.positionComponent.getChunk().equals(pair)){
                    to_remove.add(mob);
                    to_save.add(new SaveStruct(EntityTag.MOB,mob.type,mob.positionComponent.getX(),mob.positionComponent.getY(),mob.getSaveMap()));
                }
            }

            worldLoader.saveChunk(pair,to_save);
        }

        grounds.removeAll(to_remove);
        passives.removeAll(to_remove);
        mobs.removeAll(to_remove);


        loading_chunks = new HashSet<>(expected);
        loading_chunks.removeAll(loaded);

        for(var pair:loading_chunks){
            var set = worldLoader.loadChunk(pair);
            for(SaveStruct struct:set){
                switch(struct.entityTag){
                    case GROUND -> grounds.add(new Ground(struct));
                    case PASSIVE -> passives.add(new Passive(struct));
                    //case MOB -> passives.add(new Mob(struct));
                }
            }
        }
        central_chunk_coordinates = curr_chunk;
        loaded = expected;
    }
    private Set<Pair<Integer,Integer>> getSurroundingChunks(Pair<Integer,Integer> center){
        Set<Pair<Integer,Integer>> set = new HashSet<>();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                set.add(new Pair<>(center.getFirst()+i,center.getSecond()+j));
            }
        }
        return set;
    }
}
