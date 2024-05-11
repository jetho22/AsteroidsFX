package dk.sdu.mmmi.cbse.playersystem;


import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerControlSystemTests {

    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Player player;

    @BeforeEach
    public void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = new GameData();
        world = new World();
        player = new Player();
        world.addEntity(player);
        gameData.setPlayer(player);
    }

    @Test
    public void testPlayerMovement() {
        player.setX(0);
        player.setY(0);
        player.setRotation(180);

        gameData.getKeys().setKey(GameKeys.UP, true);
        playerControlSystem.process(gameData, world);

        assertNotEquals(0, player.getX());
        assertNotEquals(0, player.getY());
    }
}
