package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    GamePanel gp ;
    public  OBJ_Key(GamePanel gp) {
        this.gp = gp;
        name = "key";
        try{
        image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        uTool.scaledImage(image , gp.tileSize , gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true ;
    }
}
