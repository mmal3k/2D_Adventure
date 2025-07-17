package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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
    public final int playState  = 1;
    public final int pauseState  = 2;


    public CollisionChecker cChecker = new CollisionChecker(this);

    // SYSTEM
    Thread gameThread;
    KeyHandler keyH  = new KeyHandler(this);
    TileManager tileM  = new TileManager(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // Entity and objects
    public Player player = new Player(this , keyH);
    public SuperObject obj[] = new SuperObject[10];


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
        playMusic(0);
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
        gameState = 1;
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

        tileM.draw(g2);



        player.draw(g2);


        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }


        ui.draw(g2);

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
