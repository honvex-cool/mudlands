package generator;

import utils.Config;
import utils.Pair;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldLoader {

    private Generator generator;
    private Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, FieldStruct>> changes;
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
        this.world_name = new String(world_name);
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

    public Map<Pair<Integer, Integer>, FieldStruct> loadChunk(int chunk_x, int chunk_y) {
        //generate chunk
        var map = generator.generateChunk(chunk_x, chunk_y);
        var diff_map = changes.get(new Pair(chunk_x, chunk_y));
        if(diff_map == null)
            return map;

        //apply changes
        for(var key : diff_map.keySet()) {
            map.get(key).applyDiffs(diff_map.get(key));
        }
        return map;
    }

    public void saveChunk(int chunk_x, int chunk_y, Map<Pair<Integer, Integer>, FieldStruct> state) {
        //generate chunk
        var generated_map = generator.generateChunk(chunk_x, chunk_y);

        //calculate differences between generation and state
        Map<Pair<Integer, Integer>, FieldStruct> diffs = new HashMap<>();
        for(var key : generated_map.keySet()) {
            if(!generated_map.get(key).equals(state.get(key))) {
                diffs.put(new Pair(key), new FieldStruct(state.get(key)));
            }
        }

        //save differences
        changes.put(new Pair(chunk_x, chunk_y), diffs);
    }
}
