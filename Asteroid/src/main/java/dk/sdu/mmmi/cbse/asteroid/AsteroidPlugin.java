package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
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

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        double size = rnd.nextDouble(21) + 10;
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
        return asteroid;
    }

    public void asteroidCreationTimer(GameData gameData, World world) {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Asteroid spawned!");
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
            // Add code to handle the created asteroid, such as adding it to the game world
        }, 0, 3, TimeUnit.SECONDS);
    }
}
