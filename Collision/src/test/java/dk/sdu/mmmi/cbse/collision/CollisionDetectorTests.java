package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// This is needed to initialize the mocks (annotated with @Mock)
@ExtendWith(MockitoExtension.class)
public class CollisionDetectorTests {

    private CollisionDetector collisionDetector;

    // Use @Mock and ensure it is initialized
    @Mock
    private Player player;
    @Mock
    private World world;
    @Mock
    private IAsteroidSplitter asteroidSplitter;

    @BeforeEach
    public void setUp() {
        collisionDetector = new CollisionDetector();
        collisionDetector.asteroidSplitter = asteroidSplitter;
    }

    // Test for large asteroid
    // Verifying that the createSplitAsteroid method is called when a large asteroid is hit by a bullet
    // and createSplitAsteroid should be called
    @Test
    public void testHandleAsteroidSplit_Large() {
        Bullet bullet = mock(Bullet.class);
        Asteroid asteroid = mock(Asteroid.class);
        when(asteroid.getType()).thenReturn(Asteroid.Type.LARGE);

        collisionDetector.handleAsteroidSplit(bullet, asteroid, world);

        verify(world).removeEntity(bullet);
        verify(world).removeEntity(asteroid);
        verify(asteroidSplitter).createSplitAsteroid(asteroid, world);
    }

    // Test for small asteroid
    // Verifying that the bullet and asteroid are removed when a small asteroid is hit by a bullet
    // and createSplitAsteroid method should not be called
    @Test
    public void testHandleAsteroidSplit_Small() {
        Bullet bullet = mock(Bullet.class);
        Asteroid asteroid = mock(Asteroid.class);
        when(asteroid.getType()).thenReturn(Asteroid.Type.SMALL);

        collisionDetector.handleAsteroidSplit(bullet, asteroid, world);

        verify(world).removeEntity(bullet);
        verify(world).removeEntity(asteroid);
        verify(asteroidSplitter, never()).createSplitAsteroid(asteroid, world);
    }

    // Test for player losing life
    // Verifying that the player loses a life and the other entity is removed
    // and removeEntity should not be called on the player
    @Test
    public void testPlayerLosesLifeAndSurvives() {
        Entity otherEntity = mock(Entity.class);
        when(player.getLives()).thenReturn(2); // Player starts with more than one life

        collisionDetector.handleEntityPlayerCollision(otherEntity, player, world);

        verify(player).reduceLives();
        verify(world, never()).removeEntity(player);
        verify(world).removeEntity(otherEntity);
    }
}
