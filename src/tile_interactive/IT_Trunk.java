package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {
    public IT_Trunk(GamePanel gp, int row , int col) {
        super(gp);
        worldX = gp.tileSize * row ;
        worldY = gp.tileSize * col ;
        down1 = setup("/tiles_interactive/trunk" , gp.tileSize , gp.tileSize);
        solidArea.x = 0 ;
        solidArea.y=  0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }



}
