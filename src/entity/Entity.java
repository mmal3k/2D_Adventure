package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    public int worldX,worldY ;

    public int speed ;

    public BufferedImage up1 , up2 , left1 , left2 , right1 , right2 , down1 , down2 ;

    public String direction ;

    public int spriteCounter = 0 ;
    public int spriteNum = 1 ;

    public Rectangle solidArea = new Rectangle(0 , 0 ,48 ,48);
    public int solidAreaDefaultX , solidAreaDefaultY;
    public boolean collisionOn = false ;
    public int actionLockCounter = 0 ;
    GamePanel gp ;

    //DIALOG
    String dialogues[] = new String[20];
    int dialogueIdx = 0;


    public Entity(GamePanel gp) {
        this.gp = gp ;
    }


    public BufferedImage setup (String imagePath) {
        UtilityTool uTool = new UtilityTool();

        BufferedImage image = null ;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png")) ;
            image = uTool.scaledImage(image , gp.tileSize , gp.tileSize) ;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction () {}

    public void speak() {
        if (dialogues[dialogueIdx] == null) {
            dialogueIdx = 0 ;
        }
        gp.ui.currentDialog = dialogues[dialogueIdx];
        dialogueIdx++;

        switch (gp.player.direction) {
            case "up" :
                this.direction = "down";
                break;
            case "down" :
                this.direction = "up";
                break;
            case "left" :
                this.direction = "right";
                break;
            case "right" :
                this.direction = "left";
                break;
        }
    }

    public void update() {
        setAction();

        collisionOn = false ;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkPlayer(this);

        // IF collisionOn == false player can move

        if (!collisionOn) {
            if (direction == "up") {
                worldY -= this.speed;
            }
            if (direction == "down") {
                worldY += this.speed;
            }
            if (direction == "left") {
                worldX -= this.speed;
            }
            if (direction == "right") {
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
    public void draw(Graphics2D g2){
        BufferedImage image = null ;
        int screenX = worldX - gp.player.worldX + gp.player.screenX ;
        int screenY = worldY - gp.player.worldY + gp.player.screenY ;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
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
            g2.drawImage(image , screenX , screenY , gp.tileSize , gp.tileSize , null);
        }
    }

}
