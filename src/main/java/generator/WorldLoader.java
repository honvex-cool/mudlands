package generator;

import components.PositionComponent;
import entities.Entity;
import entities.Player;
import entities.UniversalFactory;
import entities.mobs.Mob;
import entities.passives.EmptyPassive;
import entities.passives.Passive;
import utils.Config;
import utils.Pair;

import java.io.*;
import java.util.*;

public class WorldLoader {

    private Generator generator;
    private Map<Pair<Integer, Integer>, Set<Entity>> changes;
    private String world_name;
    private Player player;
    private final UniversalFactory universalFactory;

    public WorldLoader(UniversalFactory universalFactory) {
        this.changes = null;
        this.generator = null;
        this.world_name = null;
        this.player = null;
        this.universalFactory = universalFactory;
    }

    public void createWorld(Long seed, String worldName) {
        if(worldName != null) {
            try {
                saveWorld();
            } catch(
                Exception e) { //since this whole thing is just an emergency save attempt, we do not care if it fails
            }
        }
        this.generator = new Generator(seed);
        this.world_name = worldName;
        this.changes = new HashMap<>();
        this.player = new Player();
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
        GameData data = (GameData) objectInputStream.readObject();
        objectInputStream.close();

        this.generator = new Generator(data.getSeed());
        this.world_name = world_name;
        this.changes = data.getChanges();
        this.player = data.getPlayer();
    }

    public void saveWorld() throws IOException {
        if(world_name == null)
            throw new RuntimeException("No world to save");
        File file = new File(Config.SAVE_PATH + world_name + Config.EXTENSION);
        if(file.exists())
            file.delete();

        file.createNewFile();

        GameData data = new GameData(generator.getSeed(), player, changes);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Set<Entity> loadChunk(Pair<Integer,Integer> chunk) {
        //generate chunk
        var map = generator.generateChunk(chunk);
        var diffs = changes.get(chunk);
        Set<Entity> answer = new HashSet<>();
        if(diffs != null) {
            //apply changes
            for(var entity : diffs) {
                if(entity instanceof Passive) {
                    Pair<Integer, Integer> coords = PositionComponent.getFieldAsPair(entity.mutablePositionComponent);
                    map.get(coords).objectType = ObjectType.NONE;
                    if(!(entity instanceof EmptyPassive))
                        answer.add(entity);
                }
                else{ //mobs
                    answer.add(entity);
                }
            }
        }
        answer.addAll(createDefaults(map));
        return answer;
    }

    public void saveChunk(Pair<Integer,Integer> chunk, Map<Pair<Integer,Integer>,Passive> passives, Set<Mob> mobs) {
        //generate chunk
        var generated = generator.generateChunk(chunk);

        //calculate differences between generation and state
        Set<Entity> diffs = new HashSet<>();
        for(var key : generated.keySet()) {
            FieldStruct fieldStruct = generated.get(key);
            if(passives.containsKey(key)) {
                Passive passive = passives.get(key);
                Passive defaultPassive = universalFactory.createPassive(fieldStruct,key);

                if(fieldStruct.objectType == ObjectType.NONE || !passive.equals(defaultPassive))
                    diffs.add(passive);
            }
            else{
                if(fieldStruct.objectType != ObjectType.NONE){
                    diffs.add(universalFactory.createEmptyPassive(key));
                }
            }
        }

        diffs.addAll(mobs);
        //save differences
        changes.put(chunk, diffs);
    }

    public Set<Entity> createDefaults(Map<Pair<Integer,Integer>,FieldStruct> map){
        Set<Entity> set = new HashSet<>();

        for(var key:map.keySet()) {
            FieldStruct fieldStruct = map.get(key);
            set.add(universalFactory.createGround(fieldStruct,key));
            if(fieldStruct.objectType != ObjectType.NONE)
                set.add(universalFactory.createPassive(fieldStruct,key));
        }
        set.remove(null);
        return set;
    }

    public Player loadPlayer() {
        return player;
    }
    public void savePlayer(Player player){
        this.player = player;
    }
}
