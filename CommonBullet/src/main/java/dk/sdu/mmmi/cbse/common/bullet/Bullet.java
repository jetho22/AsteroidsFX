package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {

    public Bullet() {
        super(1);
    }
        private Entity owner;

        public Entity getOwner() {
            return owner;
        }

        public void setOwner(Entity owner) {
            this.owner = owner;
        }
}
