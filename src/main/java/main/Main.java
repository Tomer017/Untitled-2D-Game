package main;

import javax.swing.JFrame;

/**
 * Main entry point for the game.
 * Sets up the game window and initializes the game panel.
 */
public class Main {
    /**
     * The main method that starts the game.
     * Creates and configures the game window, adds the game panel,
     * and starts the game thread.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Untitled Project");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}