import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;

module Collision {
    uses EnemySPI;
    uses AsteroidSplitterSPI;
    requires Common;
    requires CommonAsteroids;
    requires CommonEnemy;
    requires CommonBullet;
    requires Player;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collision.CollisionDetector;
}