package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The interface `IEntityProcessingService` is a service for processing all types of individual entities in the game world.
 * <p>
 * The `process` method is called every game update cycle.
 * Implementations of `IEntityProcessingService` need to implement this method.
 */
public interface IEntityProcessingService {

    /**
     * The `process` method is called every game update cycle, processing world entities and game data.
     *
     * @param gameData
     *      gameData contains all information about the current game state.
     * @param world
     *      world contains all entities in the game.
     *      <p>
     *      Pre-condition: gameData and world are not null.
     *      <p>
     *      Post-condition: Entities have been updated
     *      (e.g. add entities, handle movement etc.).
     */
    void process(GameData gameData, World world);
}
