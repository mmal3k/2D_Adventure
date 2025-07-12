package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp ;
    Tile[] tiles ;
    int mapTileNum[][];


    public TileManager(GamePanel gp ){
        this.gp = gp ;
        this.tiles = new Tile[10] ;
        getTileImage();
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        loadMap("world01");
    }


    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
        }catch(IOException e){
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

                    g2.drawImage(tiles[tileNum].image , screenX , screenY    , gp.tileSize , gp.tileSize , null);
                }
            }
        }
    }
}
