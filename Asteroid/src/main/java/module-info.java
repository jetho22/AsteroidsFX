import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Asteroid {
    exports dk.sdu.mmmi.cbse.asteroid;
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidControlSystem;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.PrintFrom;
}