package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tile.TileManager;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = (originalTileSize * scale); //48x48 tile
    public final int maxScreenCol = 16; // 4:3 Ratio
    public final int maxScreenRow = 12;
    public final int screenWidth = (tileSize * maxScreenCol); //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    // WORLD SETTINGS
    // CHANGE THESE TO EXPAND MAX TILE SIZE FOR WORLD
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int fps = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = (double) 1000000000 / fps; // 0.0166666 Seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread !=null) {
            // Update information such as character position
            // draw: draw the screen with the updated information
            long currentTime = System.nanoTime(); // Use this variable to implement pets missing you

            update();

            repaint();

            try {

                double remainingTime = nextDrawTime - currentTime;
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Make sure to draw the tiles before the player, since it is layered and the tiles will cover the player otherwise

        player.draw(g2);

        g2.dispose();

    }
}