package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

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
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    KeyHandler keyH  = new KeyHandler();


    // Set player default position
    int playerX = 100 ;
    int playerY = 100 ;
    int playerSpeed = 4 ;


    Thread gameThread;

    public Player player = new Player(this , keyH);

    TileManager tileM  = new TileManager(this);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.BLACK);
        // make this true for better rendering performance
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
     }
}
