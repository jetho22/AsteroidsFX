package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class EnemyControlSystem implements IEntityProcessingService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            //enemy.setX(enemy.getX() + changeX * 0.5);
            //enemy.setY(enemy.getY() + changeY * 0.5);

            checkEnemyOutOfBounds(enemy, gameData);
        }
    }

    public void checkEnemyOutOfBounds(Entity enemy, GameData gameData) {
        if (enemy.getX() < 0) {
            enemy.setX(enemy.getX() - gameData.getDisplayWidth());
        }

        if (enemy.getX() > gameData.getDisplayWidth()) {
            enemy.setX(enemy.getX() % gameData.getDisplayWidth());
        }

        if (enemy.getY() < 0) {
            enemy.setY(enemy.getY() - gameData.getDisplayHeight());
        }

        if (enemy.getY() > gameData.getDisplayHeight()) {
            enemy.setY(enemy.getY() % gameData.getDisplayHeight());
        }
    }
}
