package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double bulletSpeed = 4;
            bullet.setX(bullet.getX() + bulletSpeed * Math.cos(Math.toRadians(bullet.getRotation())));
            bullet.setY(bullet.getY() + bulletSpeed * Math.sin(Math.toRadians(bullet.getRotation())));

        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());
        System.out.println("Open fire!!");
        return bullet;
    }

    private void setShape(Entity entity) {
    }

}
