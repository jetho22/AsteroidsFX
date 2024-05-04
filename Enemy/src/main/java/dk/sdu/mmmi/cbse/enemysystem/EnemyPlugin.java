package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class EnemyPlugin implements IGamePluginService, EnemySPI {
    // this component will be created similar to the Asteroid component
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(GameData gameData, World world) {
        enemyCreationTimer(gameData, world);
        enemyStartShooting(gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

    public Entity createEnemy(GameData gameData) {
        Entity enemy = new Enemy();
        Random rnd = new Random();
        double size = rnd.nextDouble() * 40 + 30;
        double radius = (size / 2) - 5;
        double[] polygonCoordinates = {
                12, -1, 8, -1, 8, -3, 6, -3, 6, -5, -2, -5, -2, -7, 0, -7, 0, -9, -10, -9, -10, -5, -8, -5, -8, -3, -6, -3, -6, -1, -10, -1, -10, 1, -6, 1, -6, 3, -8, 3, -8, 5, -10, 5, -10, 9, 0, 9, 0, 7, -2, 7, -2, 5, 2, 5, 2, 1, 4, 1, 4, -1, 2, -1, 2, -3, 4, -3, 4, -1, 6, -1, 6, 1, 4, 1, 4, 3, 2, 3, 2, 5, 6, 5, 6, 3, 8, 3, 8, 1, 12, 1
        };
        enemy.setPolygonCoordinates(polygonCoordinates);
        int initialX = rnd.nextInt(gameData.getDisplayWidth());
        int initialY = rnd.nextInt(gameData.getDisplayHeight());
        enemy.setX(initialX);
        enemy.setY(initialY);
        enemy.setRotation(rnd.nextInt(360));
        enemy.setRadius((float) radius);
        return enemy;
    }

    public void enemyCreationTimer(GameData gameData, World world) {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Enemy spawned!");
            Entity enemy = createEnemy(gameData);
            world.addEntity(enemy);
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void enemyStartShooting(GameData gameData, World world) {
        scheduler.scheduleAtFixedRate(() -> {
            for (Entity enemy : world.getEntities(Enemy.class)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(enemy, gameData));
                        }
                );
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}