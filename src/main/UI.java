package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp ;
    Graphics2D g2 ;
    Font maruMonica ;
    public boolean messageOn = false;
    BufferedImage heart_full , heart_half , heart_blank ;

//    public String message = "" ;
//    int messageCounter = 0 ;

    ArrayList<String> message=  new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public String currentDialog = "";
    public int commandNum = 0 ;
//  State 0 is the first screen and State 1 is the second screen
    public int titleScreenState = 0 ;

    public UI (GamePanel gp){
        this.gp = gp ;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT , is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        create HUD object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image ;
        heart_half = heart.image2 ;
        heart_blank = heart.image3 ;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2 ;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen ();
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogScreen();
        }
        if(gp.gameState == gp.characterState) {
            drawCharaterScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2 ;
        int y = gp.tileSize / 2 ;

        int i = 0 ;
//
//        Draw blank hear
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank , x ,y , null);
            i ++ ;
            x += gp.tileSize ;
        }
        x = gp.tileSize / 2 ;
        y = gp.tileSize / 2 ;

        i = 0 ;
//        Draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half , x ,y , null);
            i++ ;
            if (i < gp.player.life) {
                g2.drawImage(heart_full , x ,y , null);
            }
            i++ ;
            x += gp.tileSize ;
        }
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 32F));

        for (int i= 0 ; i < message.size() ; i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i) , messageX+2 , messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i) , messageX , messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i , counter);

                messageY += 50 ;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }


    }

    public void addMessage(String text) {
        message.add(text) ;
        messageCounter.add(0);
        messageOn = true ;
    }

    public void drawPauseScreen () {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN , 80F));
        String text = "PAUSED";


        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text , x , y) ;
    }

    public void drawDialogScreen() {
        // dialog window
        int x ,y , width , height ;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN , 32F));
        x = gp.tileSize * 2 ;
        y = gp.tileSize / 2 ;
        width = gp.screenWidth  - (gp.tileSize*4) ;
        height = gp.tileSize * 4;
        drawSubWindow(x ,y ,width , height);
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialog.split("\n")) {
            g2.drawString(line , x ,y);
            y += 40 ;
        }
    }

    public void drawCharaterScreen() {
        // Create a frame
        final  int frameX = gp.tileSize;
        final  int frameY = gp.tileSize;
        final  int frameWidth = gp.tileSize *5  ;
        final  int frameHeight = gp.tileSize * 10 ;

        drawSubWindow(frameX , frameY , frameWidth , frameHeight);


//        Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX  = frameX + 20 ;
        int textY  = frameY + gp.tileSize;
        final int lineHeight = 36 ;

//        Names
        g2.drawString("Level" , textX , textY );
        textY += lineHeight;
        g2.drawString("Life", textX , textY);
        textY += lineHeight;
        g2.drawString("Strength", textX , textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX , textY);
        textY += lineHeight;
        g2.drawString("Attack", textX , textY);
        textY += lineHeight;
        g2.drawString("Defense", textX , textY);
        textY += lineHeight;
        g2.drawString("Exp", textX , textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX , textY);
        textY += lineHeight;
        g2.drawString("Coin", textX , textY);
        textY += lineHeight+20;
        g2.drawString("Weapon", textX , textY);
        textY += lineHeight+15;
        g2.drawString("Shield", textX , textY);
        textY += lineHeight;

//        Values
        int tailX = frameX + frameWidth - 30;
//        Reset text Y
        textY = frameY  + gp.tileSize ;
        String value = "";

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize , textY - 14 , null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize , textY - 14 , null);

    }



    public int getXForCenteredText (String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text , g2).getWidth();
        int x = (gp.screenWidth - length) / 2 ;
        return  x ;
    }

    public void drawSubWindow(int x  , int y , int width , int height) {
        Color c = new Color(0 , 0, 0 , 200);
        g2.setColor(c);

        g2.fillRoundRect(x , y ,width, height , 35 , 35);


        c = new Color(255, 255 ,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5 , y+5 ,width - 10 , height -10 , 25 , 25);
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            //        Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD , 96F));
            String text = "Adventure 2D" ;
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3 ;


//        text Shadow
            g2.setColor(Color.gray);
            g2.drawString(text , x+5, y+5);


            g2.setColor(Color.white);
            g2.drawString(text , x , y);

//        add spirit image
            x= (gp.screenWidth - gp.tileSize*2) / 2  ;
            y+= gp.tileSize * 2;
            g2.drawImage(gp.player.down1 , x , y ,gp.tileSize * 2 , gp.tileSize * 2 , null);


//        Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD , 48F));
            text = "NEW GAME";

            x = getXForCenteredText(text) ;
            y += gp.tileSize *3 ;
            g2.drawString(text , x, y);
            if (commandNum == 0) {
                g2.drawString(">" , x - gp.tileSize, y);
            }
            text = "LOAD GAME";

            x = getXForCenteredText(text) ;
            y += gp.tileSize ;
            g2.drawString(text , x, y);
            if (commandNum == 1) {
                g2.drawString(">" , x - gp.tileSize, y);
            }

            text = "QUIT";

            x = getXForCenteredText(text) ;
            y += gp.tileSize  ;
            g2.drawString(text , x, y);
            if (commandNum == 2) {
                g2.drawString(">" , x - gp.tileSize, y);
            }
        }else if (titleScreenState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "select your class";
            int x= getXForCenteredText(text);
            int y = gp.tileSize*3 ;
            g2.drawString(text ,x ,y);

            text = "Fighter";
            x= getXForCenteredText(text);
            y+= gp.tileSize*3;
            g2.drawString(text ,x ,y);
            if (commandNum == 0) {
                g2.drawString(">" , x - gp.tileSize, y);
            }

            text = "Thief";
            x= getXForCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text ,x ,y);
            if (commandNum == 1) {
                g2.drawString(">" , x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x= getXForCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text ,x ,y);
            if (commandNum == 2) {
                g2.drawString(">" , x - gp.tileSize, y);
            }

            text = "Back";
            x= getXForCenteredText(text);
            y+= gp.tileSize*2;
            g2.drawString(text ,x ,y);
            if (commandNum == 3) {
                g2.drawString(">" , x - gp.tileSize, y);
            }
        }

    }

    public int getXForAlignToRightText(String text , int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text , g2).getWidth();
        int x = (tailX - length) ;
        return  x ;
    }

}


