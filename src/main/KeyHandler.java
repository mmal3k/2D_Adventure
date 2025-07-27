package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed , downPressed , leftPressed , rightPressed , enterPressed ;
    boolean checkDrawTime = false ;
    GamePanel gp ;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_W ) {
                if (gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                }else {
                    gp.ui.commandNum ++;
                }
            }
            if(code ==  KeyEvent.VK_S) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.commandNum = 2;
                }else {
                    gp.ui.commandNum --;
                }
            }
            if(code ==  KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum){
                    case 0 :
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        break;
                    case 1 :
//                    add later
                        break;
                    case 2 :
                        System.exit(0);
                        break;
                }
            }

        }

        if (gp.gameState == gp.playState) {
            if(code == KeyEvent.VK_W) {
                upPressed  = true ;
            }
            if(code ==  KeyEvent.VK_A) {
                leftPressed = true ;
            }
            if(code ==  KeyEvent.VK_S) {
                downPressed = true ;
            }
            if(code ==  KeyEvent.VK_D) {
                rightPressed = true ;
            }
            if(code ==  KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if(code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime ;
            }

        } else if (gp.gameState == gp.pauseState) {
            if(code ==  KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.dialogueState) {
            if(code ==  KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed  = false ;
        }
        if(code ==  KeyEvent.VK_A) {
            leftPressed = false ;
        }
        if(code ==  KeyEvent.VK_S) {
            downPressed = false ;
        }
        if(code ==  KeyEvent.VK_D) {
            rightPressed = false ;
        }
    }
}
