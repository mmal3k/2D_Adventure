package main;

import object.OBJ_Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp ;
    Font arial_40 ,arial_30 , arial_80B ;
    BufferedImage keyImage ;
    public boolean messageOn = false;
    public String message = "" ;
    int messageCounter = 0 ;
    public boolean gameFinished = false;

    double playtime =0 ;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI (GamePanel gp){
        this.gp = gp ;
        arial_40 = new Font("Arial" , Font.PLAIN , 40);
        arial_30 = new Font("Arial" , Font.PLAIN , 30);
        arial_80B = new Font("Arial" , Font.BOLD , 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            String text ;
            int textLength ;
            int x ;
            int y ;
            g2.setFont(arial_40);
            g2.setColor(Color.white);


            text = "you found the treasure !";
            textLength = (int) g2.getFontMetrics().getStringBounds(text , g2).getWidth();
            x = (gp.screenWidth-textLength) / 2 ;
            y = gp.screenHeight / 2 - (gp.tileSize*3) ;

            g2.drawString(text , x ,y);

            text = "your time is "+dFormat.format(playtime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text , g2).getWidth();
            x = (gp.screenWidth-textLength) / 2 ;
            y = gp.screenHeight / 2 + (gp.tileSize*4) ;

            g2.drawString(text , x ,y);


            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);

            text = "Congratulations !";
            textLength = (int) g2.getFontMetrics().getStringBounds(text , g2).getWidth();
            x = (gp.screenWidth-textLength) / 2 ;
            y = gp.screenHeight / 2 + (gp.tileSize*2) ;
            g2.drawString(text , x ,y);

            gp.gameThread = null;
        }else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage , gp.tileSize/2 , gp.tileSize /2 , gp.tileSize , gp.tileSize , null);
            g2.drawString("x " + gp.player.hasKey , 74 , 65);

            //Time
            playtime += (double) 1/60;
            g2.drawString("TIME: "+dFormat.format(playtime) , gp.tileSize*11 , 65);

            if (messageOn) {
                g2.setFont(arial_30);
                g2.drawString(message , gp.tileSize/2 , gp.tileSize * 5);
                messageCounter ++ ;
                if (messageCounter > 120) {
                    messageCounter = 0 ;
                    messageOn = false;
                }
            }
        }

    }

    public void showMessage (String text) {
        message = text ;
        messageOn = true ;
    }
}
