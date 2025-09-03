package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp ;
    EventRect eventRect[][] ;

    int previousEventX , previousEventY ;
    boolean canTouchEvent = true ;

    public EventHandler(GamePanel gp) {
        this.gp = gp ;
        eventRect = new EventRect[gp.maxWorldRow][gp.maxWorldCol];

        for (int i = 0 ; i < gp.maxWorldRow ; i++) {
            for (int j = 0 ; j < gp.maxWorldCol ; j++) {
                eventRect[i][j] = new EventRect();
                eventRect[i][j].x = 23 ;
                eventRect[i][j].y = 23 ;
                eventRect[i][j].width = 2 ;
                eventRect[i][j].height = 2 ;
                eventRect[i][j].eventRectDefaultX = eventRect[i][j].x ;
                eventRect[i][j].eventRectDefaultY = eventRect[i][j].y ;
            }
        }

    }

    public void checkEvent () {


//        Check if the player character is more than 1 tile away from the last event

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);

        int distance = Math.max(xDistance , yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true ;
        }

        if (canTouchEvent) {
            if (hit(16,27 , "right")) {
//            event happens
                damagePit(16,27,gp.dialogueState);

            }

            if (hit(19,23 , "any")) {
//            event happens
                damagePit(19,23,gp.dialogueState);

            }
//        if (hit(27,16 , "right")) {
////            event happens
//            teleport(gp.dialogueState);
//
//        }

            if (hit(12,23 , "up")) {
                healingPool(12,23,gp.dialogueState);
            }
        }


    }

    public boolean hit(int row , int col , String reqDirection ) {
        boolean hit = false ;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x ;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y ;
        eventRect[row][col].x = col * gp.tileSize + eventRect[row][col].x ;
        eventRect[row][col].y = row * gp.tileSize + eventRect[row][col].y ;

        if (gp.player.solidArea.intersects(eventRect[row][col]) && !eventRect[row][col].eventDone) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true ;
                previousEventX = gp.player.worldX ;
                previousEventY = gp.player.worldY ;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX ;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY ;

        eventRect[row][col].x = eventRect[row][col].eventRectDefaultX ;
        eventRect[row][col].y = eventRect[row][col].eventRectDefaultY ;

        return hit ;
    }

    public void damagePit (int row , int col ,int gameState) {
        gp.gameState = gameState ;
        gp.ui.currentDialog = "you fall into a pit";
        gp.player.life -= 1;
//        eventRect[row][col].eventDone = true ;
        gp.playSE(6);
        canTouchEvent = false ;
    }

    public void healingPool (int row , int col , int gameState) {
        if (gp.keyH.enterPressed) {
                gp.gameState = gameState ;
                gp.playSE(2);
                gp.player.attackCanceled = true ;
                gp.ui.currentDialog = "You drink the water\nYour life has been recovered";
                gp.player.life = gp.player.maxLife ;
        }

        gp.keyH.enterPressed = false ;
    }

    public void teleport(int row, int col ,int gameState) {
        gp.gameState = gameState ;
        gp.ui.currentDialog = "Teleporting!";
        gp.player.worldX = gp.tileSize * 37 ;
        gp.player.worldY = gp.tileSize * 10 ;
    }
}
