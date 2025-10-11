package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {
    public IT_DryTree(GamePanel gp, int row , int col) {
        super(gp);
        worldX = gp.tileSize * row ;
        worldY = gp.tileSize * col ;
        down1 = setup("/tiles_interactive/drytree" , gp.tileSize , gp.tileSize);
        destructible = true ;
        life = 3 ;
    }


    @Override
    public void playSE(int i) {
        gp.playSE(11);
    }

    @Override
    public InteractiveTile getDesroyedForm() {
        int row = worldX / gp.tileSize ;
        int col = worldY / gp.tileSize ;
        return new IT_Trunk(gp ,row , col );
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == type_axe;
    }


}
