package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            bulletMovement(bullet);
            removeBulletIfOutOfBounds(bullet, gameData, world);
        }
    }

    public void bulletMovement(Entity bullet) {
        double bulletSpeed = 4;
        bullet.setX(bullet.getX() + bulletSpeed * Math.cos(Math.toRadians(bullet.getRotation())));
        bullet.setY(bullet.getY() + bulletSpeed * Math.sin(Math.toRadians(bullet.getRotation())));
    }

    public void removeBulletIfOutOfBounds(Entity bullet, GameData gameData, World world) {
        if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() ||
                bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
            world.removeEntity(bullet);
        }
    }


}
