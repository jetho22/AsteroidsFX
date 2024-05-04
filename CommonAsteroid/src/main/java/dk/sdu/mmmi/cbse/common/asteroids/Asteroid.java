package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
/**
 *
 * @author corfixen
 */
public class Asteroid extends Entity {
    public enum Type {
        LARGE, MEDIUM, SMALL
    }

    private Type type;

    public Asteroid() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}