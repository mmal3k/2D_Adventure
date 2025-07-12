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
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];

        loadMap("map01");
    }


    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap (String map) {
        try{
            InputStream is = getClass().getResourceAsStream("/maps/"+map+".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int i =0 ; i < gp.maxScreenRow ; i ++) {
                String line = br.readLine();
                String number[] = line.split(" ");
                for (int j =0 ; j < gp.maxScreenCol ; j++){

                    mapTileNum[i][j] = Integer.parseInt(number[j]);
                }
            }
            br.close();
        }catch(Exception e){

        }

        for (int i =0 ; i < gp.maxScreenRow ;i++){
            for (int j =0 ; j < gp.maxScreenCol ; j++){
                System.out.print(mapTileNum[i][j]);
            }
            System.out.println();
        }
    }

    public void draw(Graphics2D g2) {

        for (int i =0 ; i < gp.maxScreenRow ; i++) {
            for (int j = 0 ; j < gp.maxScreenCol ; j++){
                int tileNum = mapTileNum[i][j];
                g2.drawImage(tiles[tileNum].image , j*gp.tileSize , i*gp.tileSize    , gp.tileSize , gp.tileSize , null);
            }
        }
    }
}
