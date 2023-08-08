package generator;

import entities.Player;
import utils.Config;
import utils.Pair;
import utils.SaveStruct;
import entities.EntityTag;

import java.io.*;
import java.util.*;

public class WorldLoader {

    private Generator generator;
    private Map<Pair<Integer, Integer>, Set<SaveStruct>> changes;
    private String world_name;
    private SaveStruct player;

    public WorldLoader() {
        this.changes = null;
        this.generator = null;
        this.world_name = null;
        this.player = null;
    }

    public void createWorld(Integer seed, String world_name) {
        if(world_name != null) {
            try {
                saveWorld();
            } catch(
                Exception e) { //since this whole thing is just an emergency save attempt, we do not care if it fails
            }
        }
        this.generator = new Generator(seed);
        this.world_name = world_name;
        this.changes = new HashMap<>();
        this.player = new SaveStruct(EntityTag.PLAYER,0,0,0,new HashMap<>());
    }

    public void loadWorld(String world_name) throws IOException, ClassNotFoundException {
        if(world_name != null) {
            try {
                saveWorld();
            } catch(
                Exception e) { //since this whole thing is just an emergency save attempt, we do not care if it fails
            }
        }
        File file = new File(Config.SAVE_PATH + world_name + Config.EXTENSION);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        WorldData data = (WorldData) objectInputStream.readObject();
        objectInputStream.close();

        this.generator = new Generator(data.getSeed());
        this.world_name = world_name;
        this.changes = data.getChanges();
        this.player = data.getPlayerSavestruct();
    }

    public void saveWorld() throws IOException {
        if(world_name == null)
            throw new RuntimeException("No world to save");
        File file = new File(Config.SAVE_PATH + world_name + Config.EXTENSION);
        if(file.exists())
            file.delete();
        file.createNewFile();

        WorldData data = new WorldData(generator.getSeed(), player, changes);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Set<SaveStruct> loadChunk(Pair<Integer,Integer> chunk) {
        //generate chunk
        var map = generator.generateChunk(chunk);
        var diffs = changes.get(chunk);
        Set<SaveStruct> answer = new HashSet<>();
        if(diffs != null) {
            //apply changes
            for(var saveStruct : diffs) {
                if(saveStruct.entityTag == EntityTag.PASSIVE) {
                    Pair<Integer, Integer> coords = new Pair<>((int)Math.floor(saveStruct.x), (int) Math.floor(saveStruct.y));
                    map.get(coords).objectType = ObjectType.NONE;
                    if(saveStruct.type != -1){
                        answer.add(saveStruct);
                    }
                }
                else{
                    answer.add(saveStruct);
                }
            }
        }
        answer.addAll(getSaveStructs(map));
        return answer;
    }

    public void saveChunk(Pair<Integer,Integer> chunk, Map<Pair<Integer,Integer>,SaveStruct> passives, Set<SaveStruct> mobs) {
        //generate chunk
        var generated = generator.generateChunk(chunk);

        //calculate differences between generation and state
        Set<SaveStruct> diffs = new HashSet<>();
        for(var key : generated.keySet()) {
            if(passives.containsKey(key)) {
                if(passives.get(key).type >= 0){
                    diffs.add(passives.get(key));
                }
            }
            else {
                diffs.add(new SaveStruct(EntityTag.PASSIVE,-1, key.getFirst(),key.getSecond(),new HashMap<>()));
            }
        }

        diffs.addAll(mobs);

        //save differences
        changes.put(chunk, diffs);
    }

    public Set<SaveStruct> getSaveStructs(Map<Pair<Integer,Integer>,FieldStruct> map){
        Set<SaveStruct> set = new HashSet<>();

        for(var key:map.keySet()) {
            FieldStruct fieldStruct = map.get(key);
            int type = switch(fieldStruct.groundType){
                case WATER -> 1;
                case SAND -> 2;
                case GRASS -> 3;
                case MUD -> 4;
                case STONE -> 5;
                case DIRT -> 6;
            };
            set.add(new SaveStruct(EntityTag.GROUND,type, key.getFirst(), key.getSecond(), new HashMap<>()));

            type = switch(fieldStruct.objectType){
                case NONE -> -1;
                case TREE -> 1;
                case STONE -> 2;
                default -> 0;
            };
            if(type != -1){
                set.add(new SaveStruct(EntityTag.PASSIVE,-1*type, key.getFirst(), key.getSecond(), new HashMap<>()));
            }
        }

        return set;
    }

    public SaveStruct getPlayerSaveStruct() {
        return player;
    }
    public void setPlayerSaveStruct(SaveStruct playerSaveStruct){
        this.player = playerSaveStruct;
    }
}
