package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        type = type_pickUpOnly ;
        name = "Bronze Coin";
        itemValue = 1;
        down1 = setup("/objects/coin_bronze" , gp.tileSize , gp.tileSize);

    }


    public void use(Entity entity) {
        gp.ui.addMessage("Coin +"+itemValue );
        gp.player.coin += itemValue ;
        gp.playSE(1);
    }
}
