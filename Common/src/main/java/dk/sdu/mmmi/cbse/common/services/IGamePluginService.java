package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The interface `IGamePluginService` is a service for starting and stopping game plugins.
 * <p>
 * The `start` or `stop` methods are called when the game is started or stopped.
 * Implementations of `IGamePluginService` need to implement these methods.
 */
public interface IGamePluginService {

    /**
     * This method is called when the game is started, and the game plugin will start doing any needed setup.
     *
     * @param gameData
     *      gameData contains all information about the current game state.
     * @param world
     *      world contains all entities in the game.
     *      <p>
     *      Pre-condition: gameData and world are not null.
     *      <p>
     *      Post-condition: The game plugin is started and entities have been updated
     *      (e.g. add entities).
     */
    void start(GameData gameData, World world);

    /**
     * This method is called when the game is stopped, and the game plugin will stop doing any needed cleanup.
     *
     * @param gameData
     *      gameData contains all information about the current game state.
     * @param world
     *      world contains all entities in the game.
     *      <p>
     *      Pre-condition: gameData and world are not null.
     *      <p>
     *      Post-condition: The game plugin is stopped and entities have been updated
     *      (e.g. remove entities).
     */
    void stop(GameData gameData, World world);
}
