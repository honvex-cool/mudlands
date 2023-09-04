import utils.Config;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

class GdxProgram {
    public void run(MudlandsGame mudlandsGame) {
        new Lwjgl3Application(new GdxGame(mudlandsGame), createConfiguration());
        System.out.println("aa");
    }

    private static Lwjgl3ApplicationConfiguration createConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(Config.FPS);
        configuration.useVsync(Config.VSYNC_ENABLED);
        configuration.setWindowedMode(Config.NATIVE_WIDTH, Config.NATIVE_HEIGHT);
        configuration.setResizable(false);
        configuration.setTitle(Config.NAME);
        return configuration;
    }
}