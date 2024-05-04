package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.asteroid.AsteroidSplitterImpl;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;



public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }
    IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID()) || !collides(entity1, entity2)) {
                    continue;
                }

                if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                    handleAsteroidSplit((Bullet) entity1, (Asteroid) entity2, world);
                } else if (entity1 instanceof Asteroid && entity2 instanceof Bullet) {
                    handleAsteroidSplit((Bullet) entity2, (Asteroid) entity1, world);
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Enemy) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                    if (entity2 instanceof Enemy) {
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
}

