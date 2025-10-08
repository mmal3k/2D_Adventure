package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {


    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        name = "Red Potion";
        type = type_consumable ;
        down1 = setup("/objects/potion_red" , gp.tileSize , gp.tileSize);
        itemValue = 5 ;
        description = "[Red Potion]\nHeals your life by "+itemValue;
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState ;
        gp.ui.currentDialog = "You dring the "+name+"!\nYour life has been recovered by "+itemValue+" .";
        entity.life += itemValue ;
        gp.playSE(2);
    }

}
