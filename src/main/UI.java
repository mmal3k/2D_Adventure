package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp ;
    Graphics2D g2 ;
    Font maruMonica ;
    public boolean messageOn = false;
    public String message = "" ;
    int messageCounter = 0 ;
    public boolean gameFinished = false;
    public String currentDialog = "";

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


    }

    public void draw(Graphics2D g2) {
        this.g2 = g2 ;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {

        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen ();
        }

        if (gp.gameState == gp.dialogueState) {
            drawDialogScreen();
        }


    }

    public void showMessage (String text) {
        message = text ;
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


}
