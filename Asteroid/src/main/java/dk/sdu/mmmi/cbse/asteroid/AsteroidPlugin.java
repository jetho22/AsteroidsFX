package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsteroidPlugin implements IGamePluginService, AsteroidSPI {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);;

    @Override
    public void start(GameData gameData, World world) {
        asteroidCreationTimer(gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
        scheduler.shutdown();
    }

    // This method is used to create an asteroid with a specific type and size
    // This method was used before the randomAsteroidPicker method was created
    /*public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        double size = rnd.nextDouble(21) + 10;
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

        // Set the polygon coordinates
        asteroid.setPolygonCoordinates(polygonCoordinates);
        // Set the initial position of the asteroid at random coordinates within the game area
        int initialX = rnd.nextInt(gameData.getDisplayWidth());
        int initialY = rnd.nextInt(gameData.getDisplayHeight());
        asteroid.setX(initialX);
        asteroid.setY(initialY);

        // Set a random rotation for the asteroid, up to 360 degrees
        asteroid.setRotation(rnd.nextInt(360));
        asteroid.setRadius((float) radius);
        return asteroid;
    }*/

    public Entity createAsteroid(GameData gameData, Asteroid.Type type, double size) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();
        asteroid.setType(type);
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
        int initialX = rnd.nextInt(gameData.getDisplayWidth());
        int initialY = rnd.nextInt(gameData.getDisplayHeight());
        asteroid.setX(initialX);
        asteroid.setY(initialY);
        asteroid.setPolygonCoordinates(polygonCoordinates);
        asteroid.setRotation(rnd.nextInt(360));
        asteroid.setRadius((float) radius);

        return asteroid;
    }

    public Entity smallAsteroid(GameData gameData) {
        return createAsteroid(gameData, Asteroid.Type.SMALL, 30);
    }
    public Entity mediumAsteroid(GameData gameData) {
        return createAsteroid(gameData, Asteroid.Type.MEDIUM, 40);
    }
    public Entity largeAsteroid(GameData gameData) {
        return createAsteroid(gameData, Asteroid.Type.LARGE, 50);
    }

    public Entity randomAsteroidPicker(GameData gameData) {
        Random rnd = new Random();
        int random = rnd.nextInt(3);
        switch (random) {
            case 0:
                return smallAsteroid(gameData);
            case 1:
                return mediumAsteroid(gameData);
            case 2:
                return largeAsteroid(gameData);
            default:
                return largeAsteroid(gameData);
        }
    }

    public void asteroidCreationTimer(GameData gameData, World world) {
        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = randomAsteroidPicker(gameData);
            world.addEntity(asteroid);
        }, 0, 3, TimeUnit.SECONDS);
    }
}
