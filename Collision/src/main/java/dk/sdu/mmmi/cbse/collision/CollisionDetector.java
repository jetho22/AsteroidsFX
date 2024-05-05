package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.asteroid.AsteroidSplitterImpl;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetector implements IPostEntityProcessingService {

    private EnemySPI enemySPI;
    public CollisionDetector() {
        this.enemySPI = getEnemySPIs();
    }
    IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                // or if the two entities are not colliding, skip the iteration
                // ultimately, with this we can choose how the collisions should be handled below
                if (entity1.getID().equals(entity2.getID()) || !collides(entity1, entity2)) {
                    continue;
                }
                // if the two entities are a bullet and an asteroid, the asteroid should split
                if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                    handleAsteroidSplit((Bullet) entity1, (Asteroid) entity2, world);
                } else if (entity1 instanceof Asteroid && entity2 instanceof Bullet) {
                    handleAsteroidSplit((Bullet) entity2, (Asteroid) entity1, world);
                }

                // CollisionDetection
                // Here we can determine what entities should be removed from the world upon collision
                // The player can destroy and be destroyed by every entity - same goes for the enemies
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Enemy) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                        world.addEntity(getEnemySPIs().createEnemy(gameData));
                        System.out.println("Added new enemy");
                    }
                    if (entity2 instanceof Enemy) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                        world.addEntity(getEnemySPIs().createEnemy(gameData));
                        System.out.println("Added new enemy");
                    }
                    if (entity1 instanceof Player) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                    if (entity2 instanceof Player) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) { // using corfixens collision detection example
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    // a method for handling this collision is necessary because the asteroid should split
    // upon collision with bullets depending on its size
    public void handleAsteroidSplit(Bullet bullet, Asteroid asteroid, World world) {
        world.removeEntity(bullet);
        switch (asteroid.getType()) {
            case LARGE:
                asteroidSplitter.createSplitAsteroid(asteroid, world);
                world.removeEntity(asteroid);
                break;
            case MEDIUM:
                asteroidSplitter.createSplitAsteroid(asteroid, world);
                world.removeEntity(asteroid);
                break;
            case SMALL:
                world.removeEntity(asteroid);
                break;
        }
    }
    private EnemySPI getEnemySPIs() {
        return ServiceLoader.load(EnemySPI.class).findFirst().orElse(null);
    }
}

