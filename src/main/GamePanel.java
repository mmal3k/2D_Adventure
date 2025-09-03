package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 * 16 tile
    final int scale = 3 ;

    public final int tileSize = originalTileSize * scale ; // 48 * 48 tile

    public final int maxScreenCol = 16 ;
    public final int maxScreenRow = 12 ;

    public final int screenWidth = tileSize * maxScreenCol ; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow ; // 576 pixels


    public final int maxWorldCol = 50 ;
    public final int maxWorldRow = 50 ;


    // FPS
    int FPS = 60;


    //Game State
    public int gameState ;
    public final int titleState  = 0;
    public final int playState  = 1;
    public final int pauseState  = 2;
    public final int dialogueState = 3 ;
    public final int characterState = 4 ;


    public CollisionChecker cChecker = new CollisionChecker(this);

    // SYSTEM
    Thread gameThread;
    public KeyHandler keyH  = new KeyHandler(this);
    TileManager tileM  = new TileManager(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    public EventHandler eHandler = new EventHandler(this);

    // Entity and objects
    public Player player = new Player(this , keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.BLACK);
        // make this true for better rendering performance
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame () {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // 1 second = 1000000000 nanosecond
        //  `drawInterval` is how many nanoseconds should pass between each frame.
       double drawInterval = 1000000000/FPS ;
       double delta = 0 ;
       long lastTime = System.nanoTime();
       long currentTime;

       long timer = 0;
       int drawCount = 0;

       // the goal of this loop is to call update and repaint FPS Time per second
       while (gameThread != null){
           currentTime = System.nanoTime();

//           we are calculating how much time has passed by this : (currentTime - lastTime)
//           then we are dividing it by the drawInteval to see if we need to update our game
           delta += (currentTime - lastTime) / drawInterval ;
           timer += (currentTime - lastTime);
           lastTime = currentTime ;
            if (delta >= 1 ){
                // update game information like player position etc ...
                update();
                // draw screen with updated information
                repaint();
                delta-- ;
                drawCount ++;

            }

            if (timer >= 1000000000) {
//                System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }

       }
    }

    public void update() {
        if (gameState == playState) {
            player.update();

            for (int i = 0 ; i < npc.length ; i++) {
                if (npc[i] != null) {

                npc[i].update();
                }
            }


            for (int i = 0 ; i < monster.length ; i++) {
                if (monster[i] != null) {
                    if(monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    else if(!monster[i].alive) {
                        monster[i] = null;
                    }
                }
            }
        }

        if (gameState == pauseState) {
            // to implement
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        long drawStart = 0 ;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // title state
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
//          TILE
            tileM.draw(g2);

//            ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0 ; i < npc.length ; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0 ;  i < obj.length ; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0 ;  i < monster.length ; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

//            SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    int result = Integer.compare(o1.worldY , o2.worldY);
                    return result;
                }
            });

            for (Entity entity : entityList) {
                entity.draw(g2);
            }

//            Empty Entity List
            entityList.clear();


//          UI
            ui.draw(g2);
        }





        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart ;
            g2.setColor(Color.white);
            g2.drawString("Draw Time "+ passed , 10 , 400);
            System.out.println("Draw Time : " + passed);
        }



        g2.dispose();
     }


     public void playMusic (int i) {
        music.setFile(i);
        music.play();
        music.loop();
     }

     public void stopMusic () {
        music.stop();
     }


     //sound effects
     public void playSE(int i){
        se.setFile(i);
        se.play();
     }
}
