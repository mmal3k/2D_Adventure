package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp ;
    KeyHandler keyH ;
    public final int screenX , screenY ;
    public int hasKey = 0 ;

    public Player(GamePanel gp , KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        screenX = (gp.screenWidth - gp.tileSize) / 2  ;
        screenY = (gp.screenHeight - gp.tileSize)/ 2 ;

        solidArea = new Rectangle(8 , 16 , 32 , 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }

    public void getPlayerImage() {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }catch(IOException e ){
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if (keyH.upPressed) {
                direction = "up";

            }
            if (keyH.downPressed){
                direction = "down";

            }
            if (keyH.leftPressed){
                direction = "left";

            }
            if (keyH.rightPressed ){
                direction = "right";

            }

            collisionOn = false ;
            gp.cChecker.checkTile(this);

            // Check object
            int objIdx = gp.cChecker.checkObject(this, true);
            pickUpObject(objIdx);

            // IF collisionOn == false player can move

            if (!collisionOn) {
                if (keyH.upPressed) {
                    worldY -= this.speed;
                }
                if (keyH.downPressed) {
                    worldY += this.speed;
                }
                if (keyH.leftPressed) {
                    worldX -= this.speed;
                }
                if (keyH.rightPressed) {
                    worldX += this.speed;
                }
            }

            spriteCounter ++ ;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2 ;
                }else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void pickUpObject (int index) {
        if (index != 999) {


            switch (gp.obj[index].name) {
                case "key" :
                    hasKey ++ ;
                    gp.obj[index] = null;
                    break;
                case "door" :
                    if (hasKey > 0) {
                        gp.obj[index] = null;
                        hasKey -- ;
                    }
                    break;
            }
        }
    }


    public void draw(Graphics2D g2){

        BufferedImage image = null ;

        switch (direction) {
            case "up" :
                if (spriteNum == 1) {
                    image = up1 ;
                }
                if (spriteNum == 2) {
                    image = up2 ;
                }
                break ;
            case "down" :
                if (spriteNum == 1) {
                    image = down1 ;
                }
                if (spriteNum == 2) {
                    image = down2 ;
                }
                break ;
            case "left" :
                if (spriteNum == 1) {
                    image = left1 ;
                }
                if (spriteNum == 2) {
                    image = left2 ;
                }
                break ;
            case "right" :
                if (spriteNum == 1) {
                image = right1 ;
            }
            if (spriteNum == 2) {
                image = right2 ;
            }
                break ;
        }

        g2.drawImage(image , screenX ,screenY ,gp.tileSize , gp.tileSize , null);

    }
}
