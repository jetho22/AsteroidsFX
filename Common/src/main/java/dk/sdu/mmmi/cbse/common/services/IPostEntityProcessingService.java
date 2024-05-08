package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The interface `IPostEntityProcessingService` is a service for processing entities
 * after all entities in the game world have been updated.
 * <p>
 * The `process` method is called after all `IEntityProcessingServices` have been processed.
 * Implementations of `IPostEntityProcessingService` need to implement this method.
 */
public interface IPostEntityProcessingService {

    /**
     * The `process` method is called after all `IEntityProcessingServices` have been processed,
     * processing world entities and game data.
     *
     * @param gameData
     *      gameData contains all information about the current game state.
     * @param world
     *      world contains all entities in the game.
     *      <p>
     *      Pre-condition: gameData and world are not null.
     *      <p>
     *      Post-condition: Entities have been updated
     *      (e.g. add entities, remove entities, handle movement etc.).
     */
    void process(GameData gameData, World world);
}
