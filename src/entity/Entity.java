package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    public GamePanel gp ;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1 , attackRight2 ;
    public BufferedImage up1 , up2 , left1 , left2 , right1 , right2 , down1 , down2 ;
    public Rectangle solidArea = new Rectangle(0 , 0 ,48 ,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX , solidAreaDefaultY;
    public boolean collisionOn = false ;

    public boolean invincible  = false ;
    public BufferedImage image , image2 , image3 ;

    // Character attributes
    public String name ;
    public int speed ;
    public int level ;
    public int strength ;
    public int dexterity ;
    public int attack ;
    public int defense ;
    public int exp ;
    public int nextLevelExp ;
    public int coin ;
    public Entity currentWeapon ;
    public Entity currentShield ;
    public int mana ;
    public int maxMana ;
    public int ammo ;
    public Projectile projectile ;



//    STATE
    public int worldX,worldY ;
    public String direction = "down" ;
    public int spriteNum = 1 ;
    int dialogueIdx = 0;
    public boolean collision = false ;
    public boolean attacking = false;
    public boolean alive = true ;
    public boolean dying = false ;
    public boolean hpBarOn = false ;


//    COUNTER
    public int spriteCounter = 0 ;
    public int invincibleCounter = 0 ;
    public int actionLockCounter = 0 ;
    public int dyingCounter = 0 ;
    public int hpBarCounter =  0 ;
    public int shotAvailableCounter = 0 ;

    //DIALOG
    String dialogues[] = new String[20];



    //Character status
    public int maxLife , life ;

//    ITEM ATTRIBUTES
    public int attackValue ;
    public int defenseValue ;
    public String description = "" ;
    public int itemValue ;

//    TYPE
    public int type ;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickUpOnly = 7;



    public Entity(GamePanel gp) {
        this.gp = gp ;
    }


    public BufferedImage setup (String imagePath , int width , int height) {
        UtilityTool uTool = new UtilityTool();

        BufferedImage image = null ;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png")) ;
            image = uTool.scaledImage(image , width , height) ;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction () {}

    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for (int i = 0  ; i < gp.obj.length ; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem ;
                gp.obj[i].worldX = worldX ;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }
    public void damageReaction () {}

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

    public void use (Entity entity) {}

    public void update() {
        setAction();

        collisionOn = false ;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this , gp.npc);
        gp.cChecker.checkEntity(this , gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);

        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }

        // IF collisionOn == false player can move

        if (!collisionOn) {
            if (Objects.equals(direction, "up")) {worldY -= this.speed;}
            if (Objects.equals(direction, "down")) {worldY += this.speed;}
            if (Objects.equals(direction, "left")) {worldX -= this.speed;}
            if (Objects.equals(direction, "right")) {worldX += this.speed;}
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

        if (invincible) {
            invincibleCounter++ ;
            if (invincibleCounter > 40) {
                invincible = false ;
                invincibleCounter = 0 ;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter ++ ;
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
                    if (spriteNum == 1) {image = up1 ;}
                    if (spriteNum == 2) {image = up2 ;}
                    break ;
                case "down" :
                    if (spriteNum == 1) {image = down1 ;}
                    if (spriteNum == 2) {image = down2 ;}
                    break ;
                case "left" :
                    if (spriteNum == 1) {image = left1 ;}
                    if (spriteNum == 2) {image = left2 ;}
                    break ;
                case "right" :
                    if (spriteNum == 1) {image = right1 ;}
                    if (spriteNum == 2) {image = right2 ;}
                    break ;
            }
//          Monster HP bar
            if(type == type_monster && hpBarOn) {
                double oneScale = (double) gp.tileSize / maxLife ;
                double hpBarValue = oneScale * life ;

                g2.setColor(new Color(35 , 35 ,35));
                g2.fillRect(screenX - 1, screenY - 16 , gp.tileSize + 2 , 12);

                g2.setColor(new Color(255 , 0 ,30));
                g2.fillRect(screenX , screenY - 15, (int) hpBarValue , 10);

                hpBarCounter ++ ;
                if (hpBarCounter > 600) {
                    hpBarOn = false ;
                    hpBarCounter = 0 ;
                }

            }


            if (invincible) {
                hpBarOn = true ;
                hpBarCounter = 0 ;
                changeAlpha(g2 , 0.4f);
            }


            if (dying) {
                dyingAnimation(g2) ;
            }

            g2.drawImage(image , screenX , screenY , null);

            changeAlpha(g2 , 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter ++ ;
        int i = 5 ;

        if (dyingCounter <= i) {changeAlpha(g2 , 0f);}
        if (dyingCounter > i && dyingCounter <= i*2 ) {changeAlpha(g2 , 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2 , 0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2 , 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2 , 0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2 , 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2 , 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2 , 1f);}

        if (dyingCounter > i*8 ) {
            alive = false;
        }

    }


    public void damagePlayer(int attack) {
            if (!gp.player.invincible) {
//                we can give damage
                gp.playSE(6);
                int damage = attack - gp.player.defense ;
                if (damage < 0) {
                    damage = 0 ;
                }
                gp.player.life -= damage ;
                gp.player.invincible = true ;
            }

    }
    public void changeAlpha (Graphics2D g2 , float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alphaValue));
    }

    public Color getParticleColor () {
        Color color = null;
        return color ;
    }

    public int getParticleSize() {
        int size = 0 ;
        return size ;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0 ;
        return maxLife ;
    }

    public void generateParticle(Entity generator , Entity target) {
//        later
        Color color = generator.getParticleColor();
        int maxLife = generator.getParticleMaxLife();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();

        Particle p1 = new Particle(gp ,target ,color , size , speed , maxLife , -2 ,-1 );
        Particle p2 = new Particle(gp ,target ,color , size , speed , maxLife , -2 ,1 );
        Particle p3 = new Particle(gp ,target ,color , size , speed , maxLife , 2 ,-1 );
        Particle p4 = new Particle(gp ,target ,color , size , speed , maxLife , 2 ,1 );
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }

}
