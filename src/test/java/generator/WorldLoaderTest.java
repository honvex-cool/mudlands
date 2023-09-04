package generator;

import entities.UniversalFactory;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WorldLoaderTest {

    @Test
    public void testCreateSaveLoadWorld(){
        UniversalFactory universalFactoryMock = mock(UniversalFactory.class);
        WorldLoader worldLoader = new WorldLoader(universalFactoryMock);

        worldLoader.createWorld(123L,"testCreateWorld");

        try {
            worldLoader.saveWorld();
        } catch(IOException e) {
            fail();
        }

        UniversalFactory universalFactoryMock2 = mock(UniversalFactory.class);
        WorldLoader worldLoader2 = new WorldLoader(universalFactoryMock2);

        try {
            worldLoader2.loadWorld("testCreateWorld");
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    void testCannotSaveNullWorld() {
        UniversalFactory universalFactory = mock(UniversalFactory.class);
        WorldLoader worldLoader = new WorldLoader(universalFactory);
        try {
            worldLoader.saveWorld();
        } catch(Exception e) {
            if(e.getMessage().equals("No world to save"))
                return;
        }
        fail();
    }
}