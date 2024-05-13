package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterImpl;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
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
    public CollisionDetector() {}
    
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
                        handleEntityEnemyCollision(entity1, entity2, world);
                    }
                    if (entity2 instanceof Enemy) {
                        handleEntityEnemyCollision(entity1, entity2, world);
                    }
                    if (entity1 instanceof Player) {
                        handleEntityPlayerCollision(entity1, entity2, world);
                    }
                    if (entity2 instanceof Player) {
                        handleEntityPlayerCollision(entity1, entity2, world);
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

    public void handleEntityEnemyCollision(Entity entity, Entity enemy, World world) {
        if (enemy instanceof Enemy) {
            enemy.reduceLives();
            if (enemy.getLives() == 0) {
                world.removeEntity(enemy);
                world.removeEntity(entity);
                getEnemySPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createEnemy(new GameData()));
                        });
            } else {
                world.removeEntity(entity);
            }
        }
    }

    public void handleEntityPlayerCollision(Entity entity, Entity player, World world) {
        if (player instanceof Player) {
            player.reduceLives();
            if (player.getLives() == 0) {
                world.removeEntity(player);
                world.removeEntity(entity);
            } else {
                world.removeEntity(entity);
            }
        }
    }

    private Collection<? extends EnemySPI> getEnemySPIs() {
        return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

