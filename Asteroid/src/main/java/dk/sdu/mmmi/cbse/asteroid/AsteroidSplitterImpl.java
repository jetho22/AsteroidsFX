package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public class AsteroidSplitterImpl implements AsteroidSplitterSPI {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (e instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) e;
            switch (asteroid.getType()) {
                case LARGE:
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.MEDIUM));
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.MEDIUM));
                    break;
                case MEDIUM:
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.SMALL));
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.SMALL));
                    break;
            }
        }
    }

    public Entity splitAsteroidSpawner(Asteroid parent, Asteroid.Type smallerType) {
        Asteroid child = new Asteroid();
        double size = 0;
        child.setType(smallerType);
        if (smallerType == Asteroid.Type.SMALL) {
            size = 30;
        } else if (smallerType == Asteroid.Type.MEDIUM) {
            size = 40;
        }
        double radius = size / 2;
        double[] polygonCoordinates = {
                -size/2, -size/4,
                -size/4, -size/2,
                size/4, -size/2,
                size/2, -size/4,
                size/2, size/4,
                size/4, size/2,
                -size/4, size/2,
                -size/2, size/4
        };
        child.setRadius((float) radius);
        child.setPolygonCoordinates(polygonCoordinates);
        child.setX(parent.getX() + (float)Math.random() * 10 - 5);
        child.setY(parent.getY() + (float)Math.random() * 10 - 5);
        double angle = Math.random() * 360;
        child.setRotation(angle);

        return child;
    }
}