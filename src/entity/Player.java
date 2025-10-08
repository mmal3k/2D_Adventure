package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH ;
    public final int screenX , screenY ;
    int standCounter = 0;
    public boolean attackCanceled = false ;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int MaxInventorySize = 20 ;

    public Player(GamePanel gp , KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        screenX = (gp.screenWidth - gp.tileSize) / 2  ;
        screenY = (gp.screenHeight - gp.tileSize)/ 2 ;
//        Attack Area
//        attackArea.width = 36;
//        attackArea.height = 36;

        solidArea = new Rectangle(8 , 16 , 32 , 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

//        Player status
        level = 1 ;
        strength = 1 ;
        maxLife = 6 ;
        life= maxLife;
        dexterity = 1;
        exp = 0 ;
        nextLevelExp = 5 ;
        coin = 0 ;

//        mana

        maxMana =  4 ;
        mana = maxMana ;
        ammo = 10 ;

        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);

        attack = getAttack();
        defense = getDefense();

        projectile = new OBJ_Fireball(gp);
//        projectile = new OBJ_Rock(gp);
        setItems();
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea ;
        return strength * currentWeapon.attackValue ;
    }

    public int getDefense() {
        return dexterity * currentShield.defenseValue ;
    }

    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1" , gp.tileSize , gp.tileSize);
        up2 = setup("/player/boy_up_2",gp.tileSize , gp.tileSize);
        down1 = setup("/player/boy_down_1",gp.tileSize , gp.tileSize);
        down2 = setup("/player/boy_down_2",gp.tileSize , gp.tileSize);
        left1 = setup("/player/boy_left_1",gp.tileSize , gp.tileSize);
        left2 = setup("/player/boy_left_2",gp.tileSize , gp.tileSize);
        right1 = setup("/player/boy_right_1",gp.tileSize , gp.tileSize);
        right2 = setup("/player/boy_right_2",gp.tileSize , gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/boy_axe_up_1",gp.tileSize , gp.tileSize*2);
            attackUp2 = setup("/player/boy_axe_up_2",gp.tileSize , gp.tileSize*2);
            attackDown1 = setup("/player/boy_axe_down_1",gp.tileSize , gp.tileSize*2);
            attackDown2 = setup("/player/boy_axe_down_2",gp.tileSize , gp.tileSize*2);
            attackLeft1 = setup("/player/boy_axe_left_1",gp.tileSize*2 , gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2",gp.tileSize*2 , gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1",gp.tileSize*2 , gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2",gp.tileSize*2 , gp.tileSize);
        }else if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize , gp.tileSize*2);
            attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize , gp.tileSize*2);
            attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize , gp.tileSize*2);
            attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize , gp.tileSize*2);
            attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2 , gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2 , gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2 , gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2 , gp.tileSize);
        }

    }



    public void update() {
        if (attacking) {
            attack();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed || keyH.enterPressed) {
            if (keyH.upPressed) {direction = "up";}
            if (keyH.downPressed){direction = "down";}
            if (keyH.leftPressed){direction = "left";}
            if (keyH.rightPressed ){direction = "right";}
            collisionOn = false ;
            gp.cChecker.checkTile(this);

            // Check object
            int objIdx = gp.cChecker.checkObject(this, true);

            pickUpObject(objIdx);

//             Check npc collision
            int npcIdx = gp.cChecker.checkEntity(this , gp.npc);
            interactWithNPC(npcIdx);


//            Check monster collision
            int monsterIdx = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIdx);
//            Check event
            gp.eHandler.checkEvent();


            // IF collisionOn == false player can move
            if (!collisionOn && !keyH.enterPressed) {
                if (keyH.upPressed) {worldY -= this.speed;}
                if (keyH.downPressed) {worldY += this.speed;}
                if (keyH.leftPressed) {worldX -= this.speed;}
                if (keyH.rightPressed) {worldX += this.speed;}
            }

            if (keyH.enterPressed && !attackCanceled) {
                gp.playSE(7);
                attacking = true ;
                spriteCounter = 0 ;
            }
            attackCanceled = false ;
            gp.keyH.enterPressed = false ;

            spriteCounter ++ ;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {spriteNum = 2 ;}
                else if (spriteNum == 2) {spriteNum = 1;}
                spriteCounter = 0;
            }
        }
        else {
            standCounter ++ ;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (gp.keyH.shootKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {
//            SET DEFAULT COORDIANTES , DIRECTION AND USER
            projectile.set(worldX , worldY , direction , true , this);

//          SUBSTRACT THE COST
            projectile.substractResource(this);

//            ADD IT TO THE LIST
            gp.projectileList.add(projectile);


            shotAvailableCounter = 0 ;
            gp.playSE(10);

        }
        if (invincible) {
            invincibleCounter++ ;
            if (invincibleCounter > 60) {
                invincible = false ;
                invincibleCounter = 0 ;
            }
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana ;
        }

        if (shotAvailableCounter < 30){
            shotAvailableCounter ++;
        }

    }

    public void attack() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter >5 && spriteCounter <= 25) {
            spriteNum = 2;
//            Save the current X and Y , solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;


//            adjust the player x,y for the attack area

            switch (direction) {
                case "up": worldY -= attackArea.height;break;
                case "down": worldY += attackArea.height;break;
                case "left": worldX -= attackArea.width;break;
                case "right": worldX += attackArea.width;break;
            }
//            attack area becomes solid area
            solidArea.width = attackArea.width ;
            solidArea.height = attackArea.height;
//          check monster collision with the updated worldX , worldY and solidArea
            int monsterIdx = gp.cChecker.checkEntity(this , gp.monster);
            damageMonster(monsterIdx , attack) ;
//            after checking collision , restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth ;
            solidArea.height = solidAreaHeight ;

        }

        if (spriteCounter >25) {
            spriteNum = 1 ;
            spriteCounter = 0 ;
            attacking = false ;
        }
    }

    public void pickUpObject (int i) {
        if (i != 999) {
//            PICKUP ONLY ITEMS
            if (gp.obj[i].type == type_pickUpOnly ) {
                gp.obj[i].use(this);
                gp.obj[i] = null ;
            }
//            INVENTORY ITEMS
            else {
                String text = "" ;
                if (inventory.size() != MaxInventorySize) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(1);
                    text = "Got a " + gp.obj[i].name + "!" ;
                }else {
                    text = "Inventory is full !";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null ;
            }




        }
    }

    public void interactWithNPC(int index) {
        if (gp.keyH.enterPressed) {
            if(index != 999) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[index].speak();
                    attackCanceled = true ;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null ;

        int tempScreenX = screenX ;
        int tempScreenY = screenY ;

        switch (direction) {
            case "up" :
                if (!attacking) {
                    if (spriteNum == 1) {image = up1 ;}
                    if (spriteNum == 2) {image = up2 ;}
                }else {
                    tempScreenY = screenY - gp.tileSize ;
                    if (spriteNum == 1) {image = attackUp1 ;}
                    if (spriteNum == 2) {image = attackUp2 ;}
                }

                break ;
            case "down" :
                if (!attacking) {
                    if (spriteNum == 1) {image = down1 ;}
                    if (spriteNum == 2) {image = down2 ;}
                }else {
                    if (spriteNum == 1) {image = attackDown1 ;}
                    if (spriteNum == 2) {image = attackDown2 ;}
                }
                break ;
            case "left" :
                if (!attacking) {
                    if (spriteNum == 1) {image = left1 ;}
                    if (spriteNum == 2) {image = left2 ;}
                }else {
                    tempScreenX = screenX - gp.tileSize ;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break ;
            case "right" :
                if (!attacking) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }else {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break ;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.3f));
        }

        g2.drawImage(image , tempScreenX ,tempScreenY , null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));

    }

    public void contactMonster(int idx) {
        if (idx != 999) {
            if (!invincible && !gp.monster[idx].dying) {
                gp.playSE(6);
                int damage = gp.monster[idx].attack - defense ;
                if (damage < 0) {
                    damage = 0 ;
                }

                life -= damage ;
                if (life < 0) {
                    life = 0;
                }
                invincible = true ;
            }


        }
    }

    public void damageMonster(int idx , int attack) {
        if (idx != 999) {
            if (!gp.monster[idx].invincible) {
                gp.playSE(5);

                int damage = attack - gp.monster[idx].defense ;
                if (damage < 0) {
                    damage = 0 ;
                }


                gp.monster[idx].life -= damage ;

                gp.ui.addMessage(damage + " damage!");
                gp.monster[idx].invincible = true ;
                gp.monster[idx].damageReaction();
                if (gp.monster[idx].life <= 0) {
                    gp.monster[idx].dying = true ;
                    gp.ui.addMessage("Killed the "+gp.monster[idx].name +"!");
                    exp += gp.monster[idx].exp;
                    gp.ui.addMessage("Exp + "+gp.monster[idx].exp +"!");

                    checkLevelUp();
                }
            }

        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {

            level += 1;
            nextLevelExp = nextLevelExp*2 ;
            maxLife += 2;
            strength++;
            dexterity++;

            attack = getAttack();
            defense = getDefense();


            gp.playSE(8);
            gp.gameState = gp.dialogueState ;
            gp.ui.currentDialog = "you level " + level + " now\n" + "You are Stronger now !";

        }
    }

    public void selectItem () {
        int itemIdx  = gp.ui.getItemIndexOnSlot() ;
        if (itemIdx < inventory.size()) {
            Entity selectedItem = inventory.get(itemIdx);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem ;
                attack = getAttack() ;
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem ;
                defense = getDefense() ;
            }

            if (selectedItem.type == type_consumable) {
                // later
                selectedItem.use(this);
                inventory.remove(itemIdx);
            }
        }
    }

}
