import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;

module Enemy {
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    provides EnemySPI with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
}