package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);

        name = "Mana Crystal";
        type = type_pickUpOnly ;
        itemValue = 1 ;
        down1 = setup("/objects/manacrystal_full" , gp.tileSize , gp.tileSize);
        image = setup("/objects/manacrystal_full" , gp.tileSize , gp.tileSize);
        image2 = setup("/objects/manacrystal_blank" , gp.tileSize , gp.tileSize);
    }

    public void use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Mana +"+itemValue);
        gp.player.mana += itemValue ;
    }



}
