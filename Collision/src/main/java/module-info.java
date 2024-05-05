import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
    requires Common;
    requires CommonAsteroids;
    requires CommonBullet;
    requires Asteroid;
    requires CommonEnemy;
    requires Player;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collision.CollisionDetector;
}