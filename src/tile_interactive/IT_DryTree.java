package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

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

    public Color getParticleColor () {
        Color color = new Color(65 , 50 , 30);
        return color ;
    }

    public int getParticleSize() {
        int size = 6 ;
        return size ;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20 ;
        return maxLife ;
    }


}
