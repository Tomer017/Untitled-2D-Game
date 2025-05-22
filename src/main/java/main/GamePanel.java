package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tile.TileManager;
import entity.Player;

/**
 * Main game panel that handles the game loop, rendering, and core game components.
 * This class extends JPanel and implements Runnable to manage the game thread.
 */
public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    /** Original size of a tile in pixels */
    private final int originalTileSize = 16; // 16x16 tile
    /** Scale factor for rendering */
    private final int scale = 3;

    /** Actual tile size after scaling */
    public final int tileSize = (originalTileSize * scale); //48x48 tile
    /** Maximum number of columns on screen */
    public final int maxScreenCol = 16; // 4:3 Ratio
    /** Maximum number of rows on screen */
    public final int maxScreenRow = 12;
    /** Screen width in pixels */
    public final int screenWidth = (tileSize * maxScreenCol); //768 pixels
    /** Screen height in pixels */
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    // WORLD SETTINGS
    /** Maximum number of columns in the world */
    public final int maxWorldCol = 50;
    /** Maximum number of rows in the world */
    public final int maxWorldRow = 50;
    /** Total world width in pixels */
    public final int worldWidth = tileSize * maxWorldCol;
    /** Total world height in pixels */
    public final int worldHeight = tileSize * maxWorldRow;

    /** Target frames per second */
    private final int fps = 60;

    /** Manages the game's tile system */
    public final TileManager tileM = new TileManager(this);
    /** Handles keyboard input */
    private final KeyHandler keyH = new KeyHandler();
    /** Main game thread */
    private Thread gameThread;
    /** Handles collision detection */
    public final CollisionChecker cChecker = new CollisionChecker(this);
    /** The player character */
    public final Player player = new Player(this, keyH);
    
    
    /**
     * Constructs a new GamePanel and initializes the game settings.
     * Sets up the panel's size, background, and input handling.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Starts the game thread and begins the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Main game loop that handles timing and updates.
     * Implements a fixed time step game loop to maintain consistent game speed.
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
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

    /**
     * Updates the game state.
     * Currently only updates the player, but can be expanded for other game elements.
     */
    public void update() {
        player.update();
    }

    /**
     * Renders the game state.
     * Draws the tiles first, then the player on top.
     *
     * @param g The Graphics object to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}