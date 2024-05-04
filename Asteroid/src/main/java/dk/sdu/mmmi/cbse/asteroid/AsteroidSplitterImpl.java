package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (e instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) e;
            switch (asteroid.getType()) {
                case LARGE:
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.MEDIUM));
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.MEDIUM));
                    System.out.println("Large Asteroid split into two Medium Asteroids");
                    break;
                case MEDIUM:
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.SMALL));
                    world.addEntity(splitAsteroidSpawner(asteroid, Asteroid.Type.SMALL));
                    System.out.println("Medium Asteroid split into two Small Asteroids");
                    break;
            }
        }
    }

    public Entity splitAsteroidSpawner(Asteroid parent, Asteroid.Type smallerType) {
        Asteroid child = new Asteroid();
        Random rnd = new Random();
        double size = 0;
        child.setType(smallerType);
        if (smallerType == Asteroid.Type.SMALL) {
            size = 30;
        } else if (smallerType == Asteroid.Type.MEDIUM) {
            size = 40;
        }
        double radius = size / 2; // Calculate the radius based on the size
        // Define the polygon coordinates to create an irregular shape
        double[] polygonCoordinates = {
                -size/2, -size/4,    // Top-left
                -size/4, -size/2,    // Top-middle
                size/4, -size/2,     // Top-right
                size/2, -size/4,     // Right-middle
                size/2, size/4,      // Bottom-right
                size/4, size/2,      // Bottom-middle
                -size/4, size/2,     // Bottom-left
                -size/2, size/4      // Left-middle
        };
        /*double initialX = parent.getX() + rnd.nextInt(10) - 5;
        double initialY = parent.getX() + rnd.nextInt(10) - 5;
        child.setX(initialX);
        child.setY(initialY);
        child.setPolygonCoordinates(polygonCoordinates);
        child.setRotation(rnd.nextInt(360));*/
        child.setRadius((float) radius);
        child.setPolygonCoordinates(polygonCoordinates);
        child.setX(parent.getX() + (float)Math.random() * 10 - 5);
        child.setY(parent.getY() + (float)Math.random() * 10 - 5);
        double angle = Math.random() * 360;
        child.setRotation(angle);

        return child;
    }
}