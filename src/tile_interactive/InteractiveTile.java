package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

    public boolean destructible = false ;
    public InteractiveTile(GamePanel gp) {
        super(gp);
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSE(int i) {}

    public InteractiveTile getDesroyedForm() {
        InteractiveTile tile = null;
        return  tile;
    }

    @Override
    public void update() {
        if (invincible) {
            invincibleCounter ++ ;
            if (invincibleCounter > 20) {
                invincible =false ;
                invincibleCounter = 0 ;
            }
        }
    }
}
