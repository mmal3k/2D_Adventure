package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH ;
    public final int screenX , screenY ;
    int standCounter = 0;

    public Player(GamePanel gp , KeyHandler keyH) {
        super(gp);
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
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
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

//             Check npc collision
            int npcIdx = gp.cChecker.checkEntity(this , gp.npc);
            interactWithNPC(npcIdx);


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
        } else {
            standCounter ++ ;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

    }

    public void pickUpObject (int index) {
        if (index != 999) {


        }
    }

    public void interactWithNPC(int index) {
        if(index != 999) {
            if  (gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }
        gp.keyH.enterPressed = false ;
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

        g2.drawImage(image , screenX ,screenY , null);

    }
}
