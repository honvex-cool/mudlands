package generator;

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

    public WorldLoader() {
        this.generator = null;
        this.changes = null;
        this.generator = null;
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
    }

    public void saveWorld() throws IOException {
        if(world_name == null)
            throw new RuntimeException("No world to save");
        File file = new File(Config.SAVE_PATH + world_name + Config.EXTENSION);
        if(file.exists())
            file.delete();
        file.createNewFile();

        WorldData data = new WorldData(generator.getSeed(), changes);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();

        /*FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(String.valueOf(generator.getSeed()));
        fileWriter.write("\n");

        for(var chunk:changes.keySet()){
            fileWriter.write("C\n"+String.valueOf(chunk.getFirst())+" "+String.valueOf(chunk.getSecond())+"\n");
        }
        fileWriter.close();*/
    }

    public Set<SaveStruct> loadChunk(Pair<Integer,Integer> chunk) {
        return loadChunk(chunk.getFirst(),chunk.getSecond());
    }

    public Set<SaveStruct> loadChunk(int chunk_x, int chunk_y) {
        //generate chunk
        var map = generator.generateChunk(chunk_x, chunk_y);
        var diffs = changes.get(new Pair(chunk_x, chunk_y));
        Set<SaveStruct> answer = new HashSet<>();
        if(diffs != null) {
            //apply changes
            for(var saveStruct : diffs) {
                if(saveStruct.entityTag == EntityTag.PASSIVE) {
                    Pair<Integer, Integer> coords = new Pair<>((int)Math.floor(saveStruct.x), (int) Math.floor(saveStruct.y));
                    map.get(coords).objectType = ObjectType.NONE;
                    answer.add(saveStruct);
                }
                else{
                    answer.add(saveStruct);
                }
            }
        }
        answer.addAll(getSaveStructs(map));
        return answer;
    }

    public void saveChunk(Pair<Integer,Integer> chunk, Set<SaveStruct> passive) {
        saveChunk(chunk.getFirst(),chunk.getSecond(),passive);
    }
    public void saveChunk(int chunk_x, int chunk_y, Set<SaveStruct> passive) {
        //generate chunk
        //var generated = getSaveStructs(generator.generateChunk(chunk_x, chunk_y));

        //calculate differences between generation and state
        /*Set<SaveStruct> diffs = new HashSet<>();
        for(var entity : passive) {
            if(entity) {
                diffs.put(new Pair(key), new FieldStruct(state.get(key)));
            }
        }*/

        //save differences
        changes.put(new Pair(chunk_x, chunk_y), passive);
    }

    public Set<SaveStruct> getSaveStructs(Map<Pair<Integer,Integer>,FieldStruct> map){
        Set<SaveStruct> set = new HashSet<>();

        for(var key:map.keySet()) {
            FieldStruct fieldStruct = map.get(key);
            int type = switch(fieldStruct.groundType){
                case WATER -> 0;
                case SAND -> 1;
                case GRASS -> 2;
                case MUD -> 3;
                case STONE -> 4;
                case DIRT -> 5;
            };
            set.add(new SaveStruct(EntityTag.GROUND,type, key.getFirst(), key.getSecond(), new HashMap<>()));

            type = switch(fieldStruct.objectType){
                case NONE -> -1;
                case TREE -> 0;
                case STONE -> 1;
                default -> -1;
            };
            if(type != -1){
                set.add(new SaveStruct(EntityTag.PASSIVE,type, key.getFirst(), key.getSecond(), new HashMap<>()));
            }
        }

        return set;
    }
}
