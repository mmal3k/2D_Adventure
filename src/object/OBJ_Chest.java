package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject{
    GamePanel gp ;
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaledImage(image , gp.tileSize , gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
