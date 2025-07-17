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
        this.tiles = new Tile[50] ;
        getTileImage();
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        loadMap("worldV2");
    }


    public void getTileImage() {


            setup(0 , "grass00" , false);
            setup(1 , "grass00" , false);
            setup(2 , "grass00" , false);
            setup(3 , "grass00" , false);
            setup(4 , "grass00" , false);
            setup(5 , "grass00" , false);
            setup(6 , "grass00" , false);
            setup(7 , "grass00" , false);
            setup(8 , "grass00" , false);
            setup(9 , "grass00" , false);

            // start from 10 to use two digits number
            setup(10 , "grass00" , false);
            setup(11 , "grass01" , false);
            setup(12 , "water00" , true);
            setup(13 , "water01" , true);
            setup(14 , "water02" , true);
            setup(15 , "water03" , true);
            setup(16 , "water04" , true);
            setup(17 , "water05" , true);
            setup(18 , "water06" , true);
            setup(19 , "water07" , true);
            setup(20 , "water08" , true);
            setup(21 , "water09" , true);
            setup(22 , "water10" , true);
            setup(23 , "water11" , true);
            setup(24 , "water12" , true);
            setup(25 , "water13" , true);
            setup(26 , "road00" , false);
            setup(27 , "road01" , false);
            setup(28 , "road02" , false);
            setup(29 , "road03" , false);
            setup(30 , "road04" , false);
            setup(31 , "road05" , false);
            setup(32 , "road06" , false);
            setup(33 , "road07" , false);
            setup(34 , "road08" , false);
            setup(35 , "road09" , false);
            setup(36 , "road10" , false);
            setup(37 , "road11" , false);
            setup(38 , "road12" , false);
            setup(39 , "earth" , false);
            setup(40 , "wall" , true);
            setup(41 , "tree" , true);








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
