package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    int healingValue= 5 ;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        name = "Red Potion";
        type = type_consumable ;
        down1 = setup("/objects/potion_red" , gp.tileSize , gp.tileSize);
        description = "[Red Potion]\nHeals your life by "+healingValue;
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState ;
        gp.ui.currentDialog = "You dring the "+name+"!\nYour life has been recovered by "+healingValue+" .";
        entity.life += healingValue ;
        if (entity.life > entity.maxLife) {
            entity.life = entity.maxLife;
        }
        gp.playSE(2);
    }

}
