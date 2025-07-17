package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp ;
    public Tile[] tiles ;
    public int mapTileNum[][];


    public TileManager(GamePanel gp ){
        this.gp = gp ;
        this.tiles = new Tile[10] ;
        getTileImage();
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        loadMap("world01");
    }


    public void getTileImage() {


            setup(0 , "grass" , false);
            setup(1 , "wall" , true);
            setup(2 , "water" , true);
            setup(3 , "earth" , false);
            setup(4 , "tree" , true);
            setup(5 , "sand" , false);


    }

    public void setup(int index , String    imagePath , boolean collision) {
        UtilityTool uTool = new UtilityTool() ;

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
            tiles[index].image =uTool.scaledImage(tiles[index].image , gp.tileSize , gp.tileSize);
            tiles[index].collision = collision ;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap (String map) {
        try{
            InputStream is = getClass().getResourceAsStream("/maps/"+map+".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int i =0 ; i < gp.maxWorldRow ; i ++) {
                String line = br.readLine();
                String number[] = line.split(" ");
                for (int j =0 ; j < gp.maxWorldCol ; j++){

                    mapTileNum[i][j] = Integer.parseInt(number[j]);
                }
            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2) {

        for (int i =0 ; i < gp.maxWorldRow ; i++) {
            for (int j = 0 ; j < gp.maxWorldCol ; j++){
                int tileNum = mapTileNum[i][j];
                int worldX = j * gp.tileSize;
                int worldY = i * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX ;
                int screenY = worldY - gp.player.worldY + gp.player.screenY ;
                if (
                        worldX + gp.tileSize > gp.player.worldX  - gp.player.screenX
                        && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                        && worldY + gp.tileSize > gp.player.worldY  - gp.player.screenY
                        && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    g2.drawImage(tiles[tileNum].image , screenX , screenY ,  null);
                }
            }
        }
    }
}
