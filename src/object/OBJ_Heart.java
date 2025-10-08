package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "heart";
        type = type_pickUpOnly;
        itemValue = 2 ;
        down1 = setup("/objects/heart_full",gp.tileSize , gp.tileSize);
        image = setup("/objects/heart_full",gp.tileSize , gp.tileSize);
        image2 = setup("/objects/heart_half",gp.tileSize , gp.tileSize);
        image3 = setup("/objects/heart_blank",gp.tileSize , gp.tileSize);
    }

    public void use(Entity entity) {
        gp.ui.addMessage("Life +"+itemValue);
        gp.player.life += itemValue;
        gp.playSE(2);
    }

}
