package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// This is needed to initialize the mocks (annotated with @Mock)
@ExtendWith(MockitoExtension.class)
public class PlayerPluginTests {

    private PlayerPlugin playerPlugin;
    @Mock
    private GameData gameData;
    @Mock
    private World world;

    @BeforeEach
    public void setUp() {
        playerPlugin = new PlayerPlugin();
    }

    @Test
    public void testStartAddsPlayerToTheWorld() {
        // start the plugin
        playerPlugin.start(gameData, world);

        // verify that a player entity was added to the world
        verify(world, times(1)).addEntity(any(Player.class));
    }

    @Test
    public void testStopRemovesPlayerFromTheWorld() {
        // start the plugin
        playerPlugin.start(gameData, world);

        // stop the plugin
        playerPlugin.stop(gameData, world);

        // verify that a player entity was removed from the world
        verify(world, times(1)).removeEntity(any(Player.class));
    }
}