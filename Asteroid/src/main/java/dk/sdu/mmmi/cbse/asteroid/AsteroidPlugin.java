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

    public Entity createAsteroid(GameData gameData, Asteroid.Type type, double size) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();
        asteroid.setType(type);
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
                return null;
        }
    }

    public void asteroidCreationTimer(GameData gameData, World world) {
        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = randomAsteroidPicker(gameData);
            world.addEntity(asteroid);
        }, 0, 3, TimeUnit.SECONDS);
    }
}
